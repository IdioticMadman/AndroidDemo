package com.example.daos;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig orderDaoConfig;
    private final DaoConfig orderItemDaoConfig;

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        orderItemDaoConfig = daoConfigMap.get(OrderItemDao.class).clone();
        orderItemDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);
        orderItemDao = new OrderItemDao(orderItemDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Order.class, orderDao);
        registerDao(OrderItem.class, orderItemDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        orderDaoConfig.getIdentityScope().clear();
        orderItemDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public OrderItemDao getOrderItemDao() {
        return orderItemDao;
    }

}
