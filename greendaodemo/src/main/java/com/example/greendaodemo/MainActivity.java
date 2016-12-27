package com.example.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.daos.DaoMaster;
import com.example.daos.DaoSession;
import com.example.daos.Order;
import com.example.daos.OrderItem;
import com.example.daos.User;
import com.example.daos.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DaoSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDao();

        List<OrderItem> itemList = session.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(1)).unique().getOrderList().get(0).getOrderItemList();
        for (OrderItem i : itemList) {
            Log.i("==", "" + i.getOrderid() + ":" +
                    i.getNum() + ":" +
                    i.getPrice());
        }
    }

    private void initDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        session = master.newSession();
        User user = new User(null, "whm");

        session.getUserDao().insert(user);

        Order order = new Order(null, user.getId(), 100f);


        session.getOrderDao().insert(order);


        OrderItem item1 = new OrderItem(20, 20f, order.getId());
        OrderItem item2 = new OrderItem(21, 20f, order.getId());
        OrderItem item3 = new OrderItem(22, 20f, order.getId());
        OrderItem item4 = new OrderItem(23, 20f, order.getId());


        session.getOrderItemDao().insert(item1);
        session.getOrderItemDao().insert(item2);
        session.getOrderItemDao().insert(item3);
        session.getOrderItemDao().insert(item4);
    }
}
