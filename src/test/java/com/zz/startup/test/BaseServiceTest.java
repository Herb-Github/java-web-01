package com.zz.startup.test;


import org.springframework.transaction.annotation.Transactional;

@Transactional("transactionManager")
public class BaseServiceTest extends BaseContext {
}
