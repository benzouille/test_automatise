package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LigneEcritureComptableTest {

    private LigneEcritureComptable ligneEcritureComptable;

    @BeforeEach
    public void init(){
        ligneEcritureComptable = new LigneEcritureComptable();
    }

    @AfterEach
    public void atEnd(){
        ligneEcritureComptable = null;
    }

    @Test
    public void to_string_should_return_detail(){
        //GIVEN
        ligneEcritureComptable = new LigneEcritureComptable();

        //WHEN
        String result = ligneEcritureComptable.toString();

        //THEN
        assertThat(result).isEqualTo(new LigneEcritureComptable().toString());
    }
}
