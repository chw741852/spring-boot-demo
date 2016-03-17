package com.hong.eureka.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/**
 * Created by caihongwei on 16/2/23.
 */
public class EurekaClient {
    public void sendRequestToServiceUsingEureka() {
        // initialize the client
        DiscoveryManager.getInstance().initComponent(
                new MyDataCenterInstanceConfig(),
                new DefaultEurekaClientConfig()
        );

        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties
        String vipAddress = "sampleservice.mydomain.net";

        InstanceInfo nextServerInfo = null;
        try {
            nextServerInfo = DiscoveryManager.getInstance().getEurekaClient().getNextServerFromEureka(vipAddress, false);
        } catch (Exception e) {
            System.err.println("Can't get an instance of example service to talk to from eureka.");
            System.exit(-1);
        }

        System.out.println("Found an instance of example service to talk to from eureka: "
            + nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

        System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
        System.out.println("Override: " + nextServerInfo.getOverriddenStatus());

        Socket s = new Socket();
        int serverPort = nextServerInfo.getPort();
        try {
            s.connect(new InetSocketAddress(nextServerInfo.getHostName(), serverPort));
        } catch (IOException e) {
            System.err.println("Could not connect to the server: " +
                    nextServerInfo.getHostName() + " at port " + nextServerInfo.getPort());
        } catch (Exception e) {
            System.err.println("Could not connect to the server: " +
                    nextServerInfo.getHostName() + " at port " + nextServerInfo.getPort() +
                    " due the Exception " + e);
        }

        try {
            String request = "Foo " + new Date();
            System.out.println("Connected to server. Sending a sample request: " + request);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(request);

            System.out.println("Waiting for service response ...");
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = rd.readLine();
            if (str != null) {
                System.out.println("Received response from server: " + str);
                System.out.println("Exiting the client. Deme over ...");
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // finally shutdown
        DiscoveryManager.getInstance().getEurekaClient().shutdown();
    }

    public static void main(String[] args) {
        EurekaClient eurekaClient = new EurekaClient();
        eurekaClient.sendRequestToServiceUsingEureka();
    }
}
