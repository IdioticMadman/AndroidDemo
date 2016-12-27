package com.robert.rxjavademo;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author robert
 * @version 1.0
 * @time 2016/6/14.
 * @description com.robert.rxjavademo
 */
public class RxJavaTest {
    public static void main(String[] args) {
        Observable.just(1, 2)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer);
                        System.out.println("Thread:" + Thread.currentThread().getName() + ",number:" + integer);
                    }
                });
    }
}
