package com.hong.ribbon;

import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.client.ClientFactory;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caihongwei on 16/3/17 上午11:11.
 */
public class RibbonEurekaClient {
    public void sendRequestToServiceUsingEureka() throws IOException {
        ConfigurationManager.loadPropertiesFromResources("myclient.properties");

        // initialize the client
        DiscoveryManager.getInstance().initComponent(
                new MyDataCenterInstanceConfig(),
                new DefaultEurekaClientConfig()
        );

        // get LoadBalancer instance from configuration
        DynamicServerListLoadBalancer lb = (DynamicServerListLoadBalancer) ClientFactory.getNamedLoadBalancer("myclient");
        // show all servers
        List<Server> servers = lb.getAllServers();
        Iterator<Server> it = servers.iterator();
        while (it.hasNext()) {
            Server server = it.next();
            System.out.println("application service host:" + server.getHost() + ";port=" + server.getPort());
        }

        // use RandomRule's RandomRule algorithm to get random server from lb's server list
        RandomRule randomRule = new RandomRule();
        Server randomAlgorithmServer = randomRule.choose(lb, null);
        System.out.println("random algorithm server host:" + randomAlgorithmServer.getHost() + ";port:" + randomAlgorithmServer.getPort());

        // communicate with the server
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(randomAlgorithmServer.getHost(), randomAlgorithmServer.getPort()));
        } catch (IOException e) {
            System.out.println("Could not connect to the server :"
                    + randomAlgorithmServer.getHost() + " at port " + randomAlgorithmServer.getPort());
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

    public static void main(String[] args) throws IOException {
        RibbonEurekaClient ribbonEurekaClient = new RibbonEurekaClient();
        ribbonEurekaClient.sendRequestToServiceUsingEureka();
    }
}
