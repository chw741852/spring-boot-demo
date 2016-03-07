package com.hong.hystrix.basic;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by caihongwei on 16/3/7.
 */
public class CommandHelloWorldTest {
    @Test
    public void testSynchronous() {
        assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
    }

    @Test
    public void testAsynchronous1() throws ExecutionException, InterruptedException {
        assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws ExecutionException, InterruptedException {

        Future<String> fWorld = new CommandHelloWorld("World").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();

        assertEquals("Hello World!", fWorld.get());
        assertEquals("Hello Bob!", fBob.get());
    }

    @Test
    public void testObservable() {

        Observable<String> fworld = new CommandHelloWorld("World").observe();
        Observable<String> fbob = new CommandHelloWorld("Bob").observe();

        // blocking
        assertEquals("Hello World!", fworld.toBlocking().single());
        assertEquals("Hello Bob!", fbob.toBlocking().single());

        // non-blocking
        fworld.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                // nothing
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }
        });

        fbob.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("onNext: " + s);
            }
        });
    }
}