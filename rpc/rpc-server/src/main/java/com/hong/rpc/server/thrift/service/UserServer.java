package com.hong.rpc.server.thrift.service;

import com.hong.rpc.api.thrift.service.UserService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.*;

/**
 * Created by caihongwei on 2016/2/4 17:10.
 */
public class UserServer {
    public static UserServiceImpl userService;
    public static UserService.Processor processor;

//    public static void main(String[] args) {
//        userService = new UserServiceImpl();
//        processor = new UserService.Processor<>(userService);
//
//        Runnable blockServer = new Runnable() {
//            @Override
//            public void run() {
//                blockServer(processor);
//            }
//        };
//
//        Runnable nonBlockServer = new Runnable() {
//            @Override
//            public void run() {
//                nonblockingServer(processor);
//            }
//        };
//
//        new Thread(blockServer).start();
//        new Thread(nonBlockServer).start();
//    }
    /**
     * blocking server
     */
    private static void blockServer(UserService.Processor processor) {
        try {
            TServerTransport transport = new TServerSocket(8090);
            TProtocolFactory factory = new TCompactProtocol.Factory();
//            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
//            TProtocolFactory protocolFactory = new TJSONProtocol.Factory();
            TServer server = new TSimpleServer(new TServer.Args(transport).processor(processor).protocolFactory(factory));

            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new TServer.Args(transport).processor(processor).protocolFactory(factory));

            System.out.println("Starting blocking server...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * non blocking server
     * @param processor
     */
    private static void nonblockingServer(UserService.Processor processor) {
        try {
            TNonblockingServerTransport transport = new TNonblockingServerSocket(8091);
            TTransportFactory transportFactory = new TFramedTransport.Factory();
            TProtocolFactory protocolFactory = new TCompactProtocol.Factory();
            TServer server = new TNonblockingServer(new TNonblockingServer.Args(transport)
                    .processor(processor)
                    // 非阻塞式IO，服务端和客户端要指定TFramedTransport数据传输方式
                    .transportFactory(transportFactory)
                    .protocolFactory(protocolFactory));

            // 半同步半异步服务模型
//            TServer server1 = new THsHaServer(new THsHaServer.Args(transport)
//                    .processor(processor)
//                    .transportFactory(transportFactory)
//                    .protocolFactory(protocolFactory));

            System.out.println("Starting nonblocking server...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
