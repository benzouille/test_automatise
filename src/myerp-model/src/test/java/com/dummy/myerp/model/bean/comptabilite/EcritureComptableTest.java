package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;



public class EcritureComptableTest {

    private EcritureComptable ecritureComptableTest;

    @BeforeEach
    public void init(){
        ecritureComptableTest = new EcritureComptable();
    }

    @AfterEach
    public void atEnd(){
        ecritureComptableTest = null;
    }

    @Test
    public void getTotalDebit_should_return_BigDecimal_ZERO(){
        //GIVEN
        final int compteComptable = 1;
        final String debit = null;
        final String credit = null;

        ecritureComptableTest.getListLigneEcriture().add(
                createLigne(
                        compteComptable,
                        debit,
                        credit));
        //WHEN
        BigDecimal retour = ecritureComptableTest.getTotalDebit();

        //THEN
        assertThat(retour).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void getTotalDebit_should_return_BigDecimal(){
        //GIVEN
        final int compteComptable = 1;
        final String [] debits = {"100.00", "150.50", "349.50"};
        final String credit = null;

        for(String debit : debits){
            ecritureComptableTest.getListLigneEcriture().add(
                    createLigne(
                            compteComptable,
                            debit,
                            credit));
        }

        //WHEN
        BigDecimal retour = ecritureComptableTest.getTotalDebit();

        //THEN
        assertThat(retour).isEqualByComparingTo(new BigDecimal("600.00"));
    }

    @Test
    public void getTotalCredit_should_return_BigDecimal_ZERO(){
        //GIVEN
        final int compteComptable = 1;
        final String debit = null;
        final String credit = null;

        ecritureComptableTest.getListLigneEcriture().add(createLigne(
                        compteComptable,
                        debit,
                        credit));
        //WHEN
        BigDecimal retour = ecritureComptableTest.getTotalCredit();

        //THEN
        assertThat(retour).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    public void getTotalCredit_should_return_BigDecimal(){
        //GIVEN
        final int compteComptable = 1;
        final String debit = null;
        final String [] credits = {"200.00", "150.50", "349.50"};

        for(String credit : credits){
            ecritureComptableTest.getListLigneEcriture().add(createLigne(
                            compteComptable,
                            debit,
                            credit));
        }

        //WHEN
        BigDecimal retour = ecritureComptableTest.getTotalCredit();

        //THEN
        assertThat(retour).isEqualByComparingTo(new BigDecimal("700.00"));


    }

    @Test
    public void isEquilibree_should_return_true() {
        //GIVEN
        ecritureComptableTest.setLibelle("Equilibrée");
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(1, "100.50", "33.00"));
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(2, null, "301.00"));
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(2, "40", "7.00"));
        //WHEN
        boolean equilibre = ecritureComptableTest.isEquilibree();

        //THEN
        assertThat(equilibre).isTrue();
    }

    @Test
    public void isEquilibree_should_return_false(){
        //GIVEN
        ecritureComptableTest.setLibelle("Non équilibrée");
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(1, "10", null));
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        ecritureComptableTest.getListLigneEcriture().add(this.createLigne(2, "1", "2"));

        //WHEN
        boolean equilibre = ecritureComptableTest.isEquilibree();

        //THEN
        assertThat(equilibre).isFalse();
    }

    //TODO que faire de cette methode ?
    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
        return vRetour;
    }
}
