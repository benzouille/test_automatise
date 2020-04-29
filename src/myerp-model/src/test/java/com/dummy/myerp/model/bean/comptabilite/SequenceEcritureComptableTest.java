package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SequenceEcritureComptableTest {

    private SequenceEcritureComptable sequenceEcritureComptable;

    @BeforeEach
    public void init(){
        sequenceEcritureComptable = new SequenceEcritureComptable();
    }

    @AfterEach
    public void atEnd(){
        sequenceEcritureComptable = null;
    }

    @Test
    public void to_string_should_return_detail(){
        //GIVEN
        sequenceEcritureComptable.setAnnee(2000);
        sequenceEcritureComptable.setDerniereValeur(1234);
        SequenceEcritureComptable expected = new SequenceEcritureComptable(2000, 1234);

        //WHEN
        String result = sequenceEcritureComptable.toString();

        //THEN
        assertThat(result).isEqualTo(expected.toString());
    }
}
