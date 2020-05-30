package com.dummy.myerp.business.impl;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BusinessProxyImplTest {
    @Mock
    private DaoProxy daoProxy;

    @Mock
    private TransactionManager transactionManager;

    /**
     * Test de static BusinessProxyImpl static DaoProxyImpl getInstance() sans configuration
     * UnsatisfiedLinkError attendu
     */
    @Test
    public void getInstanceSansConfig() {
        BusinessProxyImpl.getInstance(null, transactionManager);
        //BusinessProxyImpl.getInstance();

        assertThrows(UnsatisfiedLinkError.class, () -> BusinessProxyImpl.getInstance());
    }

    /**
     * Test de static BusinessProxyImpl getInstance(DaoProxy pDaoProxy, TransactionManager pTransactionManager) et static DaoProxyImpl getInstance()
     */
    @Test
    public void getInstance_shouldNot_throwAnError() {
// Test que le résultat est non null
        BusinessProxyImpl vDaoProxyImpl = BusinessProxyImpl.getInstance(daoProxy, transactionManager);
        assertThat(vDaoProxyImpl).isNotNull();

// test que la méthode retourne le même objet (Pattern Singleton)
//        BusinessProxyImpl vDaoProxyImpl2 = BusinessProxyImpl.getInstance();
//        Assert.assertTrue("Test BusinessProxyImplTest.getInstance()", vDaoProxyImpl == vDaoProxyImpl2);
    }
}
