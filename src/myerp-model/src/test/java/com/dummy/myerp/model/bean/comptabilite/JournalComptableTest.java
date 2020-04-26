package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JournalComptableTest {

    private JournalComptable journalComptable;

    @BeforeEach
    public void init(){
        journalComptable = new JournalComptable();
    }

    @AfterEach
    public void atEnd(){
        journalComptable = null;
    }

    @Test
    public void getByCode_should_return_null(){
        //GIVEN
        final String [] numJournalComptables = {"100", "150", "349"};
        List<JournalComptable> journalComptableList = new ArrayList<>();
        for(String code : numJournalComptables){
            journalComptableList.add(new JournalComptable(code, "libelle"));
        }

        //WHEN
        JournalComptable result = journalComptable.getByCode_forTest(journalComptableList, "1");

        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void getByCode_should_return_JournalComptable(){
        //GIVEN
        final String [] numJournalComptables = {"1", "100", "150", "349"};
        List<JournalComptable> journalComptableList = new ArrayList<>();
        for(String code : numJournalComptables){
            journalComptableList.add(new JournalComptable(code, null));
        }

        //WHEN
        JournalComptable result = journalComptable.getByCode_forTest(journalComptableList, "1");

        //THEN
        assertThat(result).isEqualToComparingFieldByField(new JournalComptable("1", null));
    }

    @Test
    public void to_string_should_return_detail(){
        //GIVEN
        journalComptable = new JournalComptable("1", "libelle");

        //WHEN
        String result = journalComptable.toString();

        //THEN
        assertThat(result).isEqualTo(new JournalComptable("1", "libelle").toString());
    }
}
