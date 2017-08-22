package com.hao.sqlite.dao;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public interface BaseDao<T> {
    boolean insert(T record);
    boolean delete(String id);
    boolean update(T record);
    T query(String condition);
    List<T> query(String... conditions);
    List<T> queryAll();
    List<T> queryBatch(int offset, int limit);

    /**
     * 获取数据库中的数据总量
     * @return
     */
    int getTotalCount();
}
