package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CompteComptableTest {

    private CompteComptable compteComptable;

    @BeforeEach
    public void init(){
        compteComptable = new CompteComptable();
    }

    @AfterEach
    public void atEnd(){
        compteComptable = null;
    }

    @Test
    public void getByNumero_should_return_null(){
        //GIVEN
        final int [] numCompteComptables = {100, 150, 349};
        List<CompteComptable> compteComptableList = new ArrayList<>();
        for(int numero : numCompteComptables){
            compteComptableList.add(new CompteComptable(numero));
        }

        //WHEN
        CompteComptable result = compteComptable.getByNum_forTest(compteComptableList, 1);

        //THEN
        assertThat(result).isNull();
    }

    @Test
    public void getByNumero_should_return_CompteComptable(){
        //GIVEN
        final int [] numCompteComptables = {1, 100, 150, 349};
        List<CompteComptable> compteComptableList = new ArrayList<>();
        for(int numero : numCompteComptables){
            compteComptableList.add(new CompteComptable(numero));
        }

        //WHEN
        CompteComptable result = compteComptable.getByNum_forTest(compteComptableList, 1);

        //THEN
        assertThat(result).isEqualToComparingFieldByField(new CompteComptable(1, null));
    }

    @Test
    public void to_string_should_return_detail(){
        //GIVEN
        compteComptable = new CompteComptable(1, "libelle");

        //WHEN
        String result = compteComptable.toString();

        //THEN
        assertThat(result).isEqualTo(new CompteComptable(1, "libelle").toString());
    }
}
