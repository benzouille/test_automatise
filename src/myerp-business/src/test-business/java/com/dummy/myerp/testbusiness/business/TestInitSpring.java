package com.dummy.myerp.testbusiness.business;



import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class TestInitSpring extends BusinessTestCase {

    /**
     * Constructeur.
     */
    public TestInitSpring() {
        super();
    }


    /**
     * Teste l'initialisation du contexte Spring
     */
    @Test
    public void testInit() {
        SpringRegistry.init();
        assertNotNull(SpringRegistry.getBusinessProxy());
        assertNotNull(SpringRegistry.getTransactionManager());
    }

    @Test
    public void test(){
        SpringRegistry.init();
        List<JournalComptable> journalComptableList = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListJournalComptable();
        System.out.println(journalComptableList);
        assertNotNull(journalComptableList);
    }

    @Test
    public void insertEcritureComptable_shouldNot_returnAnError(){
        SpringRegistry.init();
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setReference("AC-2020/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512),
                "achat", new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "facture", null,
                new BigDecimal(123)));

        try {
            SpringRegistry.getBusinessProxy().getComptabiliteManager().insertEcritureComptable(vEcritureComptable);
        } catch (FunctionalException e) {
            e.printStackTrace();
        }

    }

    //    @Test
//    @Disabled
    public void addReference_shouldNotReturn_Error() throws NotFoundException {

//        //GIVEN
//        EcritureComptable vEcritureComptable;
//        vEcritureComptable = new EcritureComptable();
//        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
//        vEcritureComptable.setDate(new Date());
//        vEcritureComptable.setLibelle("Libelle");
//        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
//                null, new BigDecimal(123),
//                null));
//        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
//                null, null,
//                new BigDecimal(123)));
//
//        SequenceEcritureComptable sequenceEritureComptable = new SequenceEcritureComptable(2020, 44);
//        sequenceEritureComptable.setJournal_code(vEcritureComptable.getJournal().getCode());
//
//        when(comptabiliteManager.getSequenceEcritureComptable(anyString(), anyInt())).thenReturn(sequenceEritureComptable);
//
//
//        //WHEN
//        comptabiliteManager.addReference(vEcritureComptable);
//
//        //THEN
//        verify(comptabiliteManager.getSequenceEcritureComptable(anyString(), anyInt()));
//        doNothing().when(comptabiliteManager).addReference(vEcritureComptable);


    }
}
