package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    private static Entity userTable, rankRable;

    public static void main(String[] args) {


        System.out.println("../greendaogenernate/src/main/java-gen");
        //数据库版本和dao 文件包名（路径）
        //schema 标记性语言，理解成数据库的创建语句
        Schema schema = new Schema(14, "com.robert.greendaolib");
        //添加实体，会自动生成对应的 bean 文件和 dao 文件


        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("sex");
        user.addStringProperty("address");
        user.addStringProperty("phoneNum");
        //一对多

/*

        Entity order = schema.addEntity("Order");
        //order.setTableName("_ORDER");
        order.addIdProperty();
        Property usrid = order.addLongProperty("usrid").notNull().getProperty();
        order.addFloatProperty("Total");
        //用户和订单的关系是一对多
        ToMany userToOrder = user.addToMany(order, usrid);
        //订单和订单项的关系是一对多



        Entity orderItem = schema.addEntity("OrderItem");
        orderItem.addIntProperty("Num");
        orderItem.addFloatProperty("Price");
        ToMany orderToItem = order.addToMany(orderItem, orderItem.addLongProperty("orderid").notNull().getProperty());
*/


        //生成对应文件
        try {
            new DaoGenerator().generateAll(schema, "greendaolib/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
