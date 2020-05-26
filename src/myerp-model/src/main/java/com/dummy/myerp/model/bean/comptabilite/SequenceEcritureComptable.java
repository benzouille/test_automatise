package com.dummy.myerp.model.bean.comptabilite;


/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================

    //CORRECTION ajout de la clé primaire journal_code dans l'objet
    /** Journal_code */
    private String journal_code;
    /** L'année */
    private Integer annee;
    /** La dernière valeur utilisée */
    private Integer derniereValeur;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructeur
     *
     * @param pAnnee -
     * @param pDerniereValeur -
     */
    public SequenceEcritureComptable(Integer pAnnee, Integer pDerniereValeur) {
        annee = pAnnee;
        derniereValeur = pDerniereValeur;
    }


    // ==================== Getters/Setters ====================

    public String getJournal_code() {
        return journal_code;
    }
    public void setJournal_code(String journal_code) {
        this.journal_code = journal_code;
    }
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) {
        annee = pAnnee;
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }


    @Override
    public String toString() {
        return "SequenceEcritureComptable{" +
                "journal_code='" + journal_code + '\'' +
                ", annee=" + annee +
                ", derniereValeur=" + derniereValeur +
                '}';
    }
}
