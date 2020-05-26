package com.dummy.myerp.business.impl.stub;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StubComptabililteDao implements ComptabiliteDao {
    @Override
    public List<CompteComptable> getListCompteComptable() {
        List<CompteComptable> compteComptableList = new ArrayList<>();
        compteComptableList.add(new CompteComptable(1, "AC"));
        compteComptableList.add(new CompteComptable(2, "AD"));
        return compteComptableList;
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
        List<JournalComptable> journalComptableList = new ArrayList<>();
        journalComptableList.add(new JournalComptable("AC", "Achat"));
        journalComptableList.add(new JournalComptable("BQ", "Banque"));
        return journalComptableList;
    }

    @Override
    public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
        List<SequenceEcritureComptable> sequenceEcritureComptableList = new ArrayList<>();
        sequenceEcritureComptableList.add(new SequenceEcritureComptable(2020, 50));
        sequenceEcritureComptableList.add(new SequenceEcritureComptable(2020, 49));
        return sequenceEcritureComptableList;
    }

    @Override
    public SequenceEcritureComptable getSequenceEcritureComptable(String journal_code, int year) throws NotFoundException {
        SequenceEcritureComptable vSequenceEcritureComptable = new SequenceEcritureComptable();
        vSequenceEcritureComptable.setJournal_code(journal_code);
        vSequenceEcritureComptable.setAnnee(year);
        vSequenceEcritureComptable.setDerniereValeur(44);
        return vSequenceEcritureComptable;
    }

    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable) {

    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable) {

    }

    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        List<EcritureComptable> ecritureComptableList = new ArrayList<>();
        EcritureComptable ecritureComptable_1 = new EcritureComptable();
        EcritureComptable ecritureComptable_2 = new EcritureComptable();
        ecritureComptable_1.setReference("AC-2000/00002");
        ecritureComptable_1.setId(1);
        ecritureComptable_2.setReference("AC-2000/00003");
        ecritureComptable_2.setId(2);
        ecritureComptableList.add(ecritureComptable_1);
        ecritureComptableList.add(ecritureComptable_2);
        return ecritureComptableList;
    }

    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setReference("AC-2000/00002");
        ecritureComptable.setId(1);
        return ecritureComptable;
    }

    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        EcritureComptable ecritureComptable = new EcritureComptable();
        ecritureComptable.setReference(pReference);
        ecritureComptable.setId(1);
        return ecritureComptable;
    }

    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {

    }

    @Override
    public void deleteEcritureComptable(Integer pId) {

    }
}
