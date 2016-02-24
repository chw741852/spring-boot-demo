package com.hong.eureka.service;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;

import java.io.IOException;

/**
 * Created by caihongwei on 16/2/22.
 */
public class EurekaService {
    public static void main(String[] args) {
        try {
            // specify eureka property file
            ConfigurationManager.loadCascadedPropertiesFromResources("sample-eureka-service");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DiscoveryManager.getInstance().initComponent(
                new MyDataCenterInstanceConfig(),
                new DefaultEurekaClientConfig()
        );
        DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
        ApplicationInfoManager applicationInfoManager = ApplicationInfoManager.getInstance();

        EurekaClient eurekaClient = DiscoveryManager.getInstance().getEurekaClient();
        BaseService baseService = new BaseService(applicationInfoManager, eurekaClient, configInstance);
        try {
            baseService.start();
        } finally {
            baseService.stop();
        }
    }
}
