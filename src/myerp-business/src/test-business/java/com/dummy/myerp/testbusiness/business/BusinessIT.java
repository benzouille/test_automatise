package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BusinessIT extends BusinessTestCase{

    /** Logger Log4j pour la classe */
    private static final Logger LOGGER = LogManager.getLogger(BusinessIT.class);

    public BusinessIT() {
        super();
    }

    @BeforeEach
    public void init(){
        SpringRegistry.init();
    }

    @Test
    public void getListJournalComptable_shouldNot_BeNull(){
        List<JournalComptable> journalComptableList = SpringRegistry.getBusinessProxy().getComptabiliteManager().getListJournalComptable();
        System.out.println(journalComptableList);
        LOGGER.log(Level.INFO, "getListJournalComptable_shouldNot_BeNull");
        assertNotNull(journalComptableList);

    }

    @Test
    public void insertEcritureComptable_shouldNot_returnAnError(){
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("BQ", "Achat"));
        vEcritureComptable.setDate(new Date());

        SpringRegistry.getBusinessProxy().getComptabiliteManager().addReference(vEcritureComptable);

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
        LOGGER.log(Level.INFO, "insertEcritureComptable_shouldNot_returnAnError");

    }
    @Test
    public void deleteEcritureComptable_shouldNot_returnAnError(){
        SpringRegistry.getBusinessProxy().getComptabiliteManager().deleteEcritureComptable(4);
        LOGGER.log(Level.INFO, "deleteEcritureComptable_shouldNot_returnAnError");
    }
}
