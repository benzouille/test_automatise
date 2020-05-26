package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }

    /**
     * Permet de passer les stubs lors des test
     * @param pBusinessProxy -
     * @param pDaoProxy -
     */
    public void configureBusinessManager(BusinessProxy pBusinessProxy, DaoProxy pDaoProxy) {
        configure(pBusinessProxy, pDaoProxy);
    }

    /**
     * Permet de passer les stubs lors des test
     * @param pBusinessProxy -
     * @param pDaoProxy -
     * @param pTransactionManager -
     */
    public void configureBusinessManager(BusinessProxy pBusinessProxy, DaoProxy pDaoProxy, TransactionManager pTransactionManager) {
        configure(pBusinessProxy, pDaoProxy, pTransactionManager);
    }

    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */
    public SequenceEcritureComptable getSequenceEcritureComptable(String journal_code, int year) throws NotFoundException {
        return getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(journal_code, year);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) {
        // Bien se réferer à la JavaDoc de cette méthode !
        /* Le principe :
                1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
                    (table sequence_ecriture_comptable)
                2.  * S'il n'y a aucun enregistrement pour le journal pour l'année concernée :
                        1. Utiliser le numéro 1.
                    * Sinon :
                        1. Utiliser la dernière valeur + 1
                3.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
                La référence d'une écriture comptable est composée du code du journal dans lequel figure l'écriture suivi de l'année et d'un numéro de séquence
                (propre à chaque journal) sur 5 chiffres incrémenté automatiquement à chaque écriture. Le formatage de la référence est : XX-AAAA/#####.
                Ex : Journal de banque (BQ), écriture au 31/12/2016
                --> BQ-2016/00001
                4.  Enregistrer (insert/update) la valeur de la séquence en persitance
                    (table sequence_ecriture_comptable)
         */


        int vAnnee = getYear(pEcritureComptable.getDate());
        SequenceEcritureComptable sequence = null;
        try {
            sequence = getSequenceEcritureComptable(pEcritureComptable.getJournal().getCode(), vAnnee);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        SequenceEcritureComptable newSequence = new SequenceEcritureComptable();
        newSequence.setAnnee(vAnnee);

        if (sequence == null) {
            newSequence.setDerniereValeur(1);
        } else {
            newSequence.setDerniereValeur(sequence.getDerniereValeur().intValue() + 1);
        }

        String numeroSequence = "";
        if (newSequence.getDerniereValeur() < 10000) { numeroSequence += "0"; }
        if (newSequence.getDerniereValeur() < 1000) { numeroSequence += "0"; }
        if (newSequence.getDerniereValeur() < 100) { numeroSequence += "0"; }
        if (newSequence.getDerniereValeur() < 10) { numeroSequence += "0"; }
        numeroSequence += newSequence.getDerniereValeur();

        pEcritureComptable.setReference(pEcritureComptable.getJournal().getCode() + "-" + newSequence.getAnnee() + "/" + numeroSequence);

        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        if (newSequence.getDerniereValeur() == 1) {
            try {
                newSequence.setJournal_code(pEcritureComptable.getJournal().getCode());
                getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(newSequence);
                getTransactionManager().commitMyERP(vTS);
                vTS = null;
            } finally {
                getTransactionManager().rollbackMyERP(vTS);
            }

        } else {
            try {
                newSequence.setJournal_code(pEcritureComptable.getJournal().getCode());
                getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(newSequence);
                getTransactionManager().commitMyERP(vTS);
                vTS = null;
            } finally {
                getTransactionManager().rollbackMyERP(vTS);
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }

    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        constraintValidator(pEcritureComptable);

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        rg2(pEcritureComptable);

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        rg3(pEcritureComptable);

        // ===== RG_Compta_5 : Format et contenu de la référence
        rg5(pEcritureComptable);

    }

    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        rg6(pEcritureComptable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        // TODO : ne pas oublier de tester l'écriture compatble
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    private void constraintValidator(EcritureComptable pEcritureComptable) throws FunctionalException {
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }
    }

    private void rg2(EcritureComptable pEcritureComptable) throws FunctionalException {
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }
    }

    private void rg3(EcritureComptable pEcritureComptable) throws FunctionalException {
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
                || vNbrCredit < 1
                || vNbrDebit < 1) {
            throw new FunctionalException(
                    "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }
    }

    private void rg5(EcritureComptable pEcritureComptable) throws FunctionalException {
        //Code journal
        String code_ref = pEcritureComptable.getJournal().getCode();
        String code = pEcritureComptable.getReference().substring(0, 2);

        //Année écriture comptable
        Integer year_ref = getYear(pEcritureComptable.getDate());
        Integer year = Integer.parseInt(pEcritureComptable.getReference().substring(3, 7));

        if (!code_ref.equals(code) || !year_ref.equals(year)){
            throw new FunctionalException("L'écriture comptable doit avoir une référence valide");
        }
    }

    private void rg6(EcritureComptable pEcritureComptable) throws FunctionalException {
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                        pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                        || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * Récupère une année(String) d'un Objet Date.
     * @param date Date
     * @return int l'année de la date
     */
    public int getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
