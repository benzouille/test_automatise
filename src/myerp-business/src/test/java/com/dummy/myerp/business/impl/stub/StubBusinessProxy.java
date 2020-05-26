package com.dummy.myerp.business.impl.stub;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.BusinessProxyImpl;

public class StubBusinessProxy implements BusinessProxy {
    @Override
    public ComptabiliteManager getComptabiliteManager() {
        return null;
    }
}
