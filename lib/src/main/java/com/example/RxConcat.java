package com.example;

import java.io.Serializable;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.RxConcat
 * @author: robert
 * @date: 2016-09-22 14:18
 */

public class RxConcat {

    public static void main(String[] args) {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        Observable<Integer> observable1 = Observable.just(2, 3, 4, 5);
        Observable<String> observable2 = Observable.just("6", "7", "8");

        Observable.concat(observable, observable1).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
//                System.out.println(integer.toString());
            }
        });

        Observable.merge(observable1, observable2).subscribe(new Action1<Serializable>() {
            @Override
            public void call(Serializable serializable) {
//                System.out.println(serializable);
            }
        });

        Observable.just(1, 2, 3).startWith(4, 5, 6).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
//                System.out.println(integer.toString());
            }
        });
        /**
         * 使用一个函数组合多个Observable发射的数据集合，然后再发射这个结果。
         * 如果多个Observable发射的数据量不一样，则以最少的Observable为标准进行压合。内部通过OperatorZip进行压合。
         **/
        Observable.zip(observable1, observable2, new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return integer.toString() + ":" + s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
//                System.out.println(s);
            }
        });

        Observable.combineLatest(observable2,observable1 , new Func2<String, Integer, String>() {
            @Override
            public String call(String integer, Integer s) {
                return integer.toString() + ":" + s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }
}
