package com.example;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

public class HelloRxDefer {
    public static void main(String[] args) {

        /**
         * defer： 只有当订阅者订阅才创建Observable，为每个订阅创建一个新的Observable。内部通过OnSubscribeDefer在订阅时调用Func0创建Observable。
         */
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just("hello");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("call:" + s);
            }
        });
    }


}
