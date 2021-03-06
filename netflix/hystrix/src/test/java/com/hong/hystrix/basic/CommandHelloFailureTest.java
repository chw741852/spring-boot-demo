package com.hong.hystrix.basic;

import org.junit.Test;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by caihongwei on 16/3/7.
 */
public class CommandHelloFailureTest {
    @Test
    public void testSynchronous() {
        assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
        assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
    }

    @Test
    public void testAsynchronous1() throws Exception {
        assertEquals("Hello Failure World!", new CommandHelloFailure("World").queue().get());
        assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws Exception {

        Future<String> fWorld = new CommandHelloFailure("World").queue();
        Future<String> fBob = new CommandHelloFailure("Bob").queue();

        assertEquals("Hello Failure World!", fWorld.get());
        assertEquals("Hello Failure Bob!", fBob.get());
    }
}