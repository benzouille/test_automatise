package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.business.impl.stub.StubDaoProxy;
import com.dummy.myerp.business.impl.stub.StubTransactionManager;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

    private ComptabiliteManagerImpl comptabiliteManager;
    private TestExectutable testExectutable;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @BeforeEach
    public void init() {
        comptabiliteManager = new ComptabiliteManagerImpl();
        TransactionManager stubTransactionManager = new StubTransactionManager();
        comptabiliteManager.configureBusinessManager(null, new StubDaoProxy(), new StubTransactionManager());
        testExectutable = new TestExectutable();
    }

    @Test
    public void getSequenceEcritureComptable_shouldReturn_AnSequenceEcritureComptable() throws NotFoundException {
        SequenceEcritureComptable sequenceEcritureComptable;
        sequenceEcritureComptable = comptabiliteManager.getSequenceEcritureComptable("TEST", 2021);
        System.out.println(sequenceEcritureComptable.toString());
        assertThat(sequenceEcritureComptable).isNotSameAs(null);
    }

    @Test
    public void getListCompteComptable_shouldReturn_aListCompteComptable(){
        List<CompteComptable> compteComptableList;
        compteComptableList = comptabiliteManager.getListCompteComptable();
        System.out.println(compteComptableList.toString());
        assertThat(compteComptableList).isNotSameAs(null);
    }

    @Test
    public void getListJournalComptable_shouldReturn_aListJournalComptable(){
        List<JournalComptable> journalComptableList;
        journalComptableList = comptabiliteManager.getListJournalComptable();
        assertThat(journalComptableList).isNotSameAs(null);
    }

    @Test
    public void getListEcritureComptable_shouldReturn_aListEcritureComptable(){
        List<EcritureComptable> ecritureComptableList;
        ecritureComptableList = comptabiliteManager.getListEcritureComptable();
        assertThat(ecritureComptableList).isNotSameAs(null);
    }

    @Test
    public void checkEcritureComptableUnit() throws Throwable {

        //GIVEN
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
        //WHEN


        //THEN
        assertDoesNotThrow(new TestExectutable());
    }

    @Test
    public void checkEcritureComptableUnitViolation_ConstraintValidator_shouldReturn_FunctionalException () {

        //GIVEN
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123.456),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //WHEN
        //THEN
        assertThrows(FunctionalException.class, () ->
                comptabiliteManager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptableUnit_RG2_shouldReturn_FunctionalException() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));
        assertThrows(FunctionalException.class, () ->
                comptabiliteManager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptable_UnitRG3_shouldReturn_FunctionalException() {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-123),
                null));
        assertThrows(FunctionalException.class, () ->
                comptabiliteManager.checkEcritureComptableUnit(vEcritureComptable));
    }

    @Test
    public void checkEcritureComptable_UnitRG5_shouldReturn_FunctionalException() {
        testExectutable.addLigneEcritureComptableToList(testExectutable.setLigneEcritureComptable(
                1,
                null,
                new BigDecimal(123.00),
                null));

        testExectutable.addLigneEcritureComptableToList(testExectutable.setLigneEcritureComptable(2,
                null,
                null,
                new BigDecimal(123.00)));

        testExectutable.setEcritureComptable(
                "AC",
                "Achat",
                "AD-2020/00001",
                testExectutable.getLigneEcritureComptableList());

        assertThrows(FunctionalException.class, () ->
                comptabiliteManager.checkEcritureComptableUnit(
                        testExectutable.getEcritureComptable()));
    }

    @Test
    public void verifySequence_should_return_SequenceEcritureComptable_with_DerniereValeur(){
        SequenceEcritureComptable sequenceEcritureComptable = null;
        SequenceEcritureComptable newSequenceEcritureComptable;

        newSequenceEcritureComptable = comptabiliteManager.verifySequence(sequenceEcritureComptable, 2020);

        assertThat(newSequenceEcritureComptable.getDerniereValeur()).isEqualTo(1);
    }

    @Test
    public void addRefToEcriture(){
        //GIVEN
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, null,
                new BigDecimal(123)));

        SequenceEcritureComptable sequence = new SequenceEcritureComptable(2020, 7);

        //WHEN
        vEcritureComptable = comptabiliteManager.addRefToEcriture(vEcritureComptable, sequence);

        System.out.println(vEcritureComptable);
    }

    /**
     *
     */
    private class TestExectutable implements Executable {

        @Override
        public void execute() throws Throwable {
            comptabiliteManager.checkEcritureComptableUnit(getEcritureComptableForExectutable());
        }

        private List<LigneEcritureComptable> vLigneEcritureComptableList = new ArrayList<>();

        private EcritureComptable vEcritureComptable;

        private LigneEcritureComptable setLigneEcritureComptable(int pNumero, String pLibelle, BigDecimal pDebit, BigDecimal pCredit){
            LigneEcritureComptable vLigneEcritureComptable;
            vLigneEcritureComptable = new LigneEcritureComptable(new CompteComptable(pNumero), pLibelle, pDebit, pCredit);
            return vLigneEcritureComptable;
        }

        private void addLigneEcritureComptableToList(LigneEcritureComptable pLigneEcritureComptable){
            vLigneEcritureComptableList.add(pLigneEcritureComptable);
        }

        private List<LigneEcritureComptable> getLigneEcritureComptableList(){
            return vLigneEcritureComptableList;
        }

        private void  setEcritureComptable(String pcode, String pLibelle, String pReference, List<LigneEcritureComptable> pLigneEcritureComptableList){
            vEcritureComptable = new EcritureComptable();
            vEcritureComptable.setJournal(new JournalComptable(pcode, pLibelle));
            vEcritureComptable.setDate(new Date());
            vEcritureComptable.setReference(pReference);
            vEcritureComptable.setLibelle(pLibelle);
            vEcritureComptable.getListLigneEcriture().addAll(pLigneEcritureComptableList);
        }

        private EcritureComptable getEcritureComptable(){
            return vEcritureComptable;
        }

        private EcritureComptable getEcritureComptableForExectutable(){
            addLigneEcritureComptableToList(setLigneEcritureComptable(1, null, new BigDecimal(123), null));
            addLigneEcritureComptableToList(setLigneEcritureComptable(1, null, null, new BigDecimal(123)));
            setEcritureComptable("AC", "Achat", "AC-2020/00001", getLigneEcritureComptableList());
            return getEcritureComptable();
        }
    }
}
