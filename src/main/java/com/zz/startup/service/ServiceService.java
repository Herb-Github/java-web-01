package com.zz.startup.service;

import com.zz.startup.entity.Service;
import com.zz.startup.repository.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService extends BaseService<Service, String> {

    @Autowired
    private ServiceDao serviceDao;
}
