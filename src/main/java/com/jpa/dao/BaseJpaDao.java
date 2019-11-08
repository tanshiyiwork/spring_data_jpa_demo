package com.jpa.dao;

import com.jpa.filter.FilterItem;
import com.jpa.filter.Order;
import com.jpa.operator.FilterOperator;
import com.jpa.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseJpaDao {
    <T> T find(Class<T> var1, Object var2);

    <T> List<T> findAllByExample(T var1, FilterOperator var2);

    <T> List<T> findAllByExample(T var1, Order var2, FilterOperator var3);

    <T> List<T> findAllByExample(T var1, List<Order> var2, FilterOperator var3);

    <T> List<T> find(Class<T> var1, FilterOperator var2, String var3, Object var4);

    <T> List<T> findAll(Query<T> var1);

    long count(Query var1);

    <T> List<T> findAll(Class<T> var1);

    <T> Page<T> findAll(Query<T> var1, Pageable var2);

    <T> List<T> findAllWithRemoved(Query<T> var1);

    <T> Page<T> findAllWithRemoved(Query<T> var1, Pageable var2);

    <T> T save(T var1);

    void save(Iterable<?> var1);

    void flush();

    void delete(Object var1);

    void delete(Query var1);

    void delete(Class<?> var1, Object var2);

    void delete(Class<?> var1, Iterable<?> var2);

    int removedPhysiclly(Class<?> var1, Object var2);

    void removedPhysiclly(Query var1);

    int removedPhysiclly(Class<?> var1, Iterable<?> var2);

    List<?> executeQuery(String var1, Map<String, ?> var2);

    Page executeQuery(String var1, Map<String, ?> var2, Pageable var3);

    Page getQueryList(Map<String, Collection<FilterItem>> var1, String var2, Map var3, Map var4, Pageable var5);

    Page getQueryList(Map<String, ?> var1, String var2, Map<Class, String> var3, Map<String, Object> var4, Pageable var5, Order[] var6);

    List<?> executeSqlQuery(String var1, Map<String, ?> var2);

    int executeUpdate(String var1, Map<String, ?> var2);

    int executeSqlUpdate(String var1, Map<String, ?> var2);
}
