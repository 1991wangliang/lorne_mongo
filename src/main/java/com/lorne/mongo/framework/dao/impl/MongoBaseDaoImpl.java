package com.lorne.mongo.framework.dao.impl;


import com.lorne.core.framework.model.Page;
import com.lorne.mongo.framework.dao.MongoBaseDao;
import com.lorne.mongo.framework.entity.MongoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by yuliang on 2016/3/23.
 */
public class MongoBaseDaoImpl<T extends MongoEntity> implements MongoBaseDao<T> {


    @Autowired
    protected MongoTemplate mongoTemplate;


    protected Class clazz;

    public MongoBaseDaoImpl() {
        this.clazz = (Class<?>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void saveOrUpdate(T t) {
        mongoTemplate.save(t);
    }

    public List<T> findAll() {
        Query query = new Query();
        return mongoTemplate.find(query, clazz);
    }

    @Override
    public void saveOrUpdate(T t, String tableName) {
        mongoTemplate.save(t, tableName);
    }

    @Override
    public List<T> findAll(String tableName) {
        Query query = new Query();
        return mongoTemplate.find(query, clazz, tableName);
    }

    @Override
    public Page<T> pageForBeanList(Query query, int nowPage, int pageSize) {
        return pageForBeanList(query, nowPage, pageSize);
    }

    @Override
    public Page<T> pageForBeanList(Query query, int nowPage, int pageSize, String tableName) {
        Page<T> page = new Page<T>();
        long count = mongoTemplate.count(query, clazz);
        page.setTotal((int) count);

        Pageable pageable = new PageRequest(nowPage - 1, pageSize);
        query.with(pageable);
        List<T> list = null;
        if (StringUtils.isEmpty(tableName)) {
            mongoTemplate.find(query, clazz);
        } else {
            list = mongoTemplate.find(query, clazz, tableName);
        }
        page.setRows(list);
        page.setNowPage(nowPage);
        page.setPageSize(pageSize);

        long pageNumber = 0;
        if (count > 0) {
            if (count % pageSize == 0) {
                pageNumber = count / pageSize;
            } else {
                pageNumber = count / pageSize + 1;
            }
        }
        page.setPageNumber((int) pageNumber);
        return page;
    }

    @Override
    public void remove(T t) {
        mongoTemplate.remove(t);
    }

    @Override
    public void remove(T t, String tableName) {
        mongoTemplate.remove(t, tableName);
    }

    @Override
    public void dropTable() {
        mongoTemplate.dropCollection(clazz);
    }


    @Override
    public void dropTable(String tableName) {
        mongoTemplate.dropCollection(tableName);
    }
}
