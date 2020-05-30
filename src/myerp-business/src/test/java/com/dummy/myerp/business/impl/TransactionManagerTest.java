package com.dummy.myerp.business.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionManagerTest {

    @Test
    public void getInstance_shouldNot_throwAnException(){
        TransactionManager transactionManager = TransactionManager.getInstance();

        assertThat(transactionManager).isNotNull();
    }
}
