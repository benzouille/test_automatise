package com.dummy.myerp.business.impl.stub;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


public class StubDaoProxy implements DaoProxy {

    ComptabiliteDao comptabiliteDao = new StubComptabililteDao();

    @Override
    public ComptabiliteDao getComptabiliteDao() {
        return comptabiliteDao;
    }
}
