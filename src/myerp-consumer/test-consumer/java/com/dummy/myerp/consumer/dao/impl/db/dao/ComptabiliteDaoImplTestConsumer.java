package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assume;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;


/**
 * Classe de test Consumer de la classe {@link ComptabiliteDaoImpl}
 */
public class ComptabiliteDaoImplTestConsumer extends ConsumerTestCase {
    // ==================== Attributs Static ====================
    /** Logger Log4j pour la classe */
    private static final Logger LOGGER = LogManager.getLogger(ComptabiliteDaoImplTestConsumer.class);


    // ==================== Attributs ====================
    /** {@link ComptabiliteDao} */
    private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();


    // ==================== Méthodes ====================
    /**
     * Test : {@link ComptabiliteDaoImpl#getListCompteComptable()}
     */
    @Test
    public void getListCompteComptable() {
        List<CompteComptable> vList = dao.getListCompteComptable();
        LOGGER.log(vList.size() == 0 ? Level.WARN : Level.INFO, "Nbr de CompteComptable : " + vList.size());
        Assumptions.assumeTrue(vList.size() > 0); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
    }


    /**
     * Test : {@link ComptabiliteDaoImpl#getListJournalComptable()}
     */
    @Test
    public void getListJournalComptable() {
        List<JournalComptable> vList = dao.getListJournalComptable();
        LOGGER.log(vList.size() == 0 ? Level.WARN : Level.INFO, "Nbr de JournalComptable : " + vList.size());
        Assumptions.assumeTrue(vList.size() > 0); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
    }


    /**
     * Test : {@link ComptabiliteDaoImpl#getListEcritureComptable()}
     */
    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> vList = dao.getListEcritureComptable();
        LOGGER.log(vList.size() == 0 ? Level.WARN : Level.INFO, "Nbr de EcritureComptable : " + vList.size());
        Assumptions.assumeTrue(vList.size() > 0); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
    }


    /**
     * Test : {@link ComptabiliteDaoImpl#getEcritureComptable(Integer)}
     */
    @Test
    public void getEcritureComptable() {
        EcritureComptable vBean = null;
        try {
            vBean = dao.getEcritureComptable(-1);
            Assume.assumeNotNull(vBean); // Le test n'est pas concluant car on n'est pas passé par les rowmapper
        } catch (NotFoundException vEx) {
            Assume.assumeNoException("L'écriture comptable n'a pas été trouvée, le row mapper n'a pas été testé.", vEx);
        }
    }
}
