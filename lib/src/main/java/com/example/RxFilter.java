package com.example;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.exceptions.Exceptions;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.RxFilter
 * @author: robert
 * @date: 2016-09-22 14:35
 */

public class RxFilter {
    public static void main(String[] args) {
/*        *//**
         * filter： 过滤数据。内部通过OnSubscribeFilter过滤数据。
         *//*
        Observable.just(3, 4, 5, 6)
                .filter(item -> item > 4)
                .subscribe(item -> {
                    System.out.println(item.toString());
                }); //5,6


        Observable.just(1, 2, "3")
                .ofType(String.class)
                .subscribe(System.out::println);

        Observable.just(3, 4, 5, 6)
                .take(1)//发射前三个数据项
                .take(1, TimeUnit.MILLISECONDS).subscribe(integer -> {
            System.out.println(integer.toString());
        });//发射100ms内的数据

        Observable.just(3, 4, 5, 6)
                .takeLast(2)
                .subscribe(integer -> {
                    System.out.print(integer.toString());
                });

        Observable.just(3, 4, 5, 6)
                .takeFirst(integer -> integer > 3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer.toString());
            }
        });

        Observable.just(3, 4, 5, 6)
                .first()
                .subscribe(System.out::println);
        *//**
         * 隔一段时间执行下一个onNext。隔得时间必须是设定的时间的倍数
         *//*
        Observable.create(subscriber -> {
            subscriber.onNext(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw Exceptions.propagate(e);
            }
            subscriber.onNext(2);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw Exceptions.propagate(e);
//            }

            subscriber.onNext(3);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw Exceptions.propagate(e);
            }
            subscriber.onNext(4);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw Exceptions.propagate(e);
            }
            subscriber.onNext(5);
            subscriber.onCompleted();

        }).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(item -> System.out.println(item.toString()));*/

        Observable.create(subscriber -> {
            subscriber.onNext(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw Exceptions.propagate(e);
            }
            subscriber.onNext(2);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw Exceptions.propagate(e);
            }

            subscriber.onNext(3);
            try {
                Thread.sleep(1001);
            } catch (InterruptedException e) {
                throw Exceptions.propagate(e);
            }
            subscriber.onNext(4);
            subscriber.onNext(5);
            subscriber.onCompleted();

        }).debounce(1000, TimeUnit.MILLISECONDS)//或者为throttleWithTimeout(1000, TimeUnit.MILLISECONDS)
                .subscribe(item -> System.out.println(item.toString())); //结果为3,5
    }
}
