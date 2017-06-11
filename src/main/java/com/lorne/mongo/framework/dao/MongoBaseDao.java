package com.lorne.mongo.framework.dao;

import com.lorne.core.framework.model.Page;
import com.lorne.mongo.framework.entity.MongoEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by yuliang on 2017/4/17.
 */
public interface MongoBaseDao<T extends MongoEntity> {

    void saveOrUpdate(T t);

    void saveOrUpdate(T t, String tableName);

    List<T> findAll();

    List<T> findAll(String tableName);

    Page<T> pageForBeanList(Query query, int nowPage, int pageSize);

    Page<T> pageForBeanList(Query query, int nowPage, int pageSize, String tableName);

    void remove(T t);

    void remove(T t, String tableName);

    void dropTable();

    void dropTable(String tableName);

}
