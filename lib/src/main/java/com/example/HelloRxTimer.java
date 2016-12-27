package com.example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HelloRxTimer {
    public static void main(String[] args) {

        /**
         * range： 创建一个发射指定范围的整数序列的Observable<Integer>
         */
        Observable.range(2, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer.toString());// 2,3,4,5,6 从2开始发射5个数据
            }
        });
        /**
         * interval： 创建一个按照给定的时间间隔发射从0开始的整数序列的Observable<Long>，内部通过OnSubscribeTimerPeriodically工作。
         */
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.immediate())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println("onNext:" + aLong.toString() + "time:" + new Date().toString());
                    }
                });
    }


}
