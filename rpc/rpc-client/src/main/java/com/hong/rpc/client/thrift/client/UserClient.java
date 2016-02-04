package com.hong.rpc.client.thrift.client;

import com.hong.rpc.api.thrift.service.UserService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.util.StopWatch;

/**
 * Created by caihongwei on 2016/2/4 19:31.
 *
 * https://git1-us-west.apache.org/repos/asf?p=thrift.git;a=tree;f=tutorial/java/src;hb=HEAD
 * http://blog.csdn.net/m13321169565/article/details/7836006
 * http://www.micmiu.com/soa/rpc/thrift-sample/
 */
public class UserClient {
    private static StopWatch stopWatch = new StopWatch();

    public static void main(String[] args) {
        blockClient();
    }

    private static void blockClient() {
        try {
            TTransport transport = new TSocket("localhost", 8090);
            transport.open();

            stopWatch.start();
            TProtocol protocol = new TCompactProtocol(transport);
            UserService.Client client = new UserService.Client(protocol);
            System.out.println(client.sayHello("caihongwei"));
            stopWatch.stop();
            System.err.println("total time seconds: " + stopWatch.getTotalTimeSeconds());
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
