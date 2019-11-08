/*
package com.jpa.dao.impl;

import com.jpa.dao.BaseJpaDao;
import com.jpa.filter.FilterConverter;
import com.jpa.filter.FilterItem;
import com.jpa.filter.Order;
import com.jpa.operator.FilterOperator;
import com.jpa.operator.OrderOperator;
import com.jpa.query.Filters;
import com.jpa.query.Query;
import com.jpa.utils.StringUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.*;

public class BaseJpaDaoImpl implements BaseJpaDao {
    public static final Logger logger = Logger.getLogger(BaseJpaDaoImpl.class);
    @PersistenceContext(
            unitName = "entityManagerFactory"
    )
    private EntityManager em;

    public BaseJpaDaoImpl() {
    }

    public <T> T find(Class<T> clazz, Object id) {
        return this.getEm().find(clazz, id);
    }

    public <T> List<T> findAllByExample(T pojo, FilterOperator filterOperator) {
        return this.findAllByExample(pojo, (List)(new ArrayList()), filterOperator);
    }

    public <T> List<T> findAllByExample(T pojo, Order order, FilterOperator filterOperator) {
        ArrayList<Order> orders = new ArrayList();
        if (order != null) {
            orders.add(order);
        }

        return this.findAllByExample(pojo, (List)orders, filterOperator);
    }

    public <T> List<T> findAllByExample(T pojo, List<Order> orders, FilterOperator filterOperator) {
        Session session = (Session)this.getEm().getDelegate();
        Example example = Example.create(pojo).excludeZeroes();
        if (FilterOperator.LIKE.equals(filterOperator)) {
            example.enableLike();
        } else if (FilterOperator.LEFTLIKE.equals(filterOperator)) {
            example.enableLike(MatchMode.START);
        }

        if (FilterOperator.RIGHTLIKE.equals(filterOperator)) {
            example.enableLike(MatchMode.END);
        }

        Criteria criteria = session.createCriteria(pojo.getClass()).add(example);
        if (orders != null) {
            Iterator iterator = orders.iterator();

            while(iterator.hasNext()) {
                Order tmp = (Order)iterator.next();
                if (tmp.getOrderOperator().equals(OrderOperator.ASC)) {
                    criteria.addOrder(org.hibernate.criterion.Order.asc(tmp.getField()));
                } else if (tmp.getOrderOperator().equals(OrderOperator.DESC)) {
                    criteria.addOrder(org.hibernate.criterion.Order.desc(tmp.getField()));
                }
            }
        }

        return criteria.list();
    }

    public <T> List<T> find(Class<T> clazz, FilterOperator op, String field, Object value) {
        Query query = Query.from(clazz);
        query.filter(op, field, value);
        return this.findAll(query);
    }

    public void flush() {
        this.getEm().flush();
    }

    public <T> T save(T t) {
        if (t instanceof ICreateEntity && ((ICreateEntity)t).getCreatedTime() == null) {
            ((ICreateEntity)t).setCreatedTime(new Date());
        }

        if (t instanceof IRemovable && (((IRemovable)t).getIsRemoved() == null || "".equals(((IRemovable)t).getIsRemoved()))) {
            ((IRemovable)t).setIsRemoved("0");
        }

        if (t instanceof IUpdateEntity) {
            ((IUpdateEntity)t).setLastModifiedTime(new Date());
        }

        return this.getEm().merge(t);
    }

    public void save(Iterable<?> iterable) {
        Iterator it = iterable.iterator();

        while(it.hasNext()) {
            this.save(it.next());
        }

    }

    public void delete(Query query) {
        this.deleteByQuery(query);
    }

    public void deleteByQuery(Query query) {
        HashMap<String, List<FilterItem>> filterItemMap = new HashMap();
        filterItemMap.put("obj", query.getFilterItems());
        HashMap<String, Object> paramMap = new HashMap();
        String hql;
        if (IRemovable.class.isAssignableFrom(query.getEntityClass())) {
            hql = "update " + query.getEntityClass().getName() + " as obj set obj.isRemoved='" + "1" + "'";
        } else {
            hql = "delete " + query.getEntityClass().getName() + " as obj";
        }

        hql = this.parseFilterItemMap(filterItemMap, paramMap, hql, (Map)null, (Order[])null);
        this.executeUpdate(hql, paramMap);
    }

    public void delete(Object obj) {
        if (obj != null) {
            if (obj instanceof Query) {
                this.deleteByQuery((Query)obj);
            } else {
                if (obj instanceof IRemovable) {
                    ((IRemovable)obj).setIsRemoved("1");
                    this.getEm().merge(obj);
                } else {
                    this.getEm().remove(obj);
                }

            }
        }
    }

    public void delete(Class<?> clazz, Object id) {
        Object t = this.find(clazz, id);
        if (t == null) {
            logger.warn("未找到" + clazz.getName() + "数据" + id.toString());
        }

        if (t instanceof IRemovable) {
            ((IRemovable)t).setIsRemoved("1");
            this.getEm().merge(t);
        } else {
            this.getEm().remove(t);
        }

    }

    public void delete(Class<?> clazz, Iterable<?> iterable) {
        Iterator it = iterable.iterator();

        while(it.hasNext()) {
            this.delete(clazz, it.next());
        }

    }

    public int removedPhysiclly(Class<?> clazz, Object id) {
        int total = 0;
        Object t = this.find(clazz, id);
        if (t == null) {
            logger.warn("未找到" + clazz.getName() + "数据" + id.toString());
        } else {
            this.getEm().remove(t);
            ++total;
        }

        return total;
    }

    public int removedPhysiclly(Class<?> clazz, Iterable<?> ids) {
        int total = 0;

        for(Iterator it = ids.iterator(); it.hasNext(); total += this.removedPhysiclly(clazz, it.next())) {
        }

        return total;
    }

    public void removedPhysiclly(Query query) {
        HashMap<String, List<FilterItem>> filterItemMap = new HashMap();
        filterItemMap.put("obj", query.getFilterItems());
        HashMap<String, Object> paramMap = new HashMap();
        String hql = "delete " + query.getEntityClass().getName() + " as obj";
        hql = this.parseFilterItemMap(filterItemMap, paramMap, hql, (Map)null, (Order[])null);
        this.executeUpdate(hql, paramMap);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return this.findAll(Query.from(clazz));
    }

    public <T> List<T> findAll(Query<T> query) {
        if (query == null) {
            return null;
        } else {
            if (IRemovable.class.isAssignableFrom(query.getEntityClass())) {
                query.filter(Filters.equal("isRemoved", "0"));
            }

            return this.findAllWithRemoved(query);
        }
    }

    public <T> Page<T> findAll(Query<T> query, Pageable pageable) {
        if (query == null) {
            return null;
        } else {
            if (IRemovable.class.isAssignableFrom(query.getEntityClass())) {
                query.filter(Filters.equal("isRemoved", "0"));
            }

            return this.findAllWithRemoved(query, pageable);
        }
    }

    public long count(Query query) {
        if (IRemovable.class.isAssignableFrom(query.getEntityClass())) {
            query.filter(Filters.equal("isRemoved", "0"));
        }

        CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
        CriteriaQuery<?> cq = cb.createQuery(query.getEntityClass());
        Root<?> root = cq.from(query.getEntityClass());
        query.getQuery(root, cq, cb);
        TypedQuery<?> tq = this.getEm().createQuery(cq);
        CriteriaQuery<Tuple> cqTuple = cb.createQuery(Tuple.class);
        cqTuple.select(cb.tuple(new Selection[]{cb.count(root).alias("_resultTotal")}));
        query.clearOrders();
        Root<?> rootTuple = cqTuple.from(query.getEntityClass());
        query.getQuery(rootTuple, cqTuple, cb);
        TypedQuery<Tuple> tqTuple = this.getEm().createQuery(cqTuple);
        List<Tuple> tupleResult = tqTuple.getResultList();
        long total = (Long)((Long)((Tuple)tupleResult.get(0)).get(0));
        return total;
    }

    public <T> List<T> findAllWithRemoved(Query<T> query) {
        CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
        CriteriaQuery<?> cq = cb.createQuery(query.getEntityClass());
        Root<?> root = cq.from(query.getEntityClass());
        query.getQuery(root, cq, cb);
        TypedQuery<?> tq = this.getEm().createQuery(cq);
        List<?> rows = tq.getResultList();
        return rows;
    }

    public <T> Page<T> findAllWithRemoved(Query<T> query, Pageable pageable) {
        CriteriaBuilder cb = this.getEm().getCriteriaBuilder();
        CriteriaQuery<?> cq = cb.createQuery(query.getEntityClass());
        Root<?> root = cq.from(query.getEntityClass());
        query.getQuery(root, cq, cb);
        TypedQuery<?> tq = this.getEm().createQuery(cq);
        CriteriaQuery<Tuple> cqTuple = cb.createQuery(Tuple.class);
        cqTuple.select(cb.tuple(new Selection[]{cb.count(root).alias("_resultTotal")}));
        query.clearOrders();
        Root<?> rootTuple = cqTuple.from(query.getEntityClass());
        query.getQuery(rootTuple, cqTuple, cb);
        TypedQuery<Tuple> tqTuple = this.getEm().createQuery(cqTuple);
        List<Tuple> tupleResult = tqTuple.getResultList();
        long total = (Long)((Long)((Tuple)tupleResult.get(0)).get(0));
        if (pageable != null) {
            tq.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            tq.setMaxResults(pageable.getPageSize());
        }

        List<?> rows = tq.getResultList();
        Page<T> page = new PageImpl(rows, pageable, total);
        return page;
    }

    public List<?> executeQuery(String hql, Map<String, ?> parameters) {
        javax.persistence.Query query = this.getEm().createQuery(hql);
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();

            while(it.hasNext()) {
                String key = (String)it.next();
                query.setParameter(key, parameters.get(key));
            }
        }

        return query.getResultList();
    }

    public Page executeQuery(String hql, Map<String, ?> parameters, Pageable pageable) {
        javax.persistence.Query query = this.getEm().createQuery(hql);
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();

            while(it.hasNext()) {
                String key = (String)it.next();
                query.setParameter(key, parameters.get(key));
            }
        }

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<?> rows = query.getResultList();
        int firstIndex = hql.indexOf("from");
        if (firstIndex != -1) {
            hql = hql.substring(firstIndex);
        }

        hql = this.filterOrderBy(hql);
        query = this.getEm().createQuery("select count(*)  as count " + hql + "");
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();

            while(it.hasNext()) {
                String key = (String)it.next();
                query.setParameter(key, parameters.get(key));
            }
        }

        Number total = (Number)query.getSingleResult();
        Page page = new PageImpl(rows, pageable, total.longValue());
        return page;
    }

    public Page getQueryList(Map filterItemMap, String hql, Map clazzMap, Map parameters, Pageable pageable) {
        return this.getQueryList(filterItemMap, hql, clazzMap, parameters, pageable, (Order[])null);
    }

    public Page getQueryList(Map<String, ?> filterItemMap, String hql, Map<Class, String> clazzMap, Map<String, Object> parameters, Pageable pageable, Order[] orders) {
        if (!StringUtil.isValidStr(hql)) {
            return null;
        } else {
            Map<String, Object> paramMap = new HashMap();
            hql = this.parseFilterItemMap(filterItemMap, paramMap, hql, clazzMap, orders);
            javax.persistence.Query query = this.getEm().createQuery(hql);
            this.parseParameterMap(paramMap, parameters, query);
            if (pageable != null) {
                query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
                query.setMaxResults(pageable.getPageSize());
            }

            List<?> rows = query.getResultList();
            int firstIndex = hql.indexOf("from");
            if (firstIndex != -1) {
                hql = hql.substring(firstIndex);
            }

            hql = this.filterOrderBy(hql);
            query = this.getEm().createQuery("select count(*)  as count " + hql + "");
            this.parseParameterMap(paramMap, parameters, query);
            Number total = (Number)query.getSingleResult();
            Page page = new PageImpl(rows, pageable, total.longValue());
            return page;
        }
    }

    public List<?> executeSqlQuery(String sql, Map<String, ?> parameters) {
        javax.persistence.Query query = this.getEm().createNativeQuery(sql);
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();

            while(it.hasNext()) {
                String key = (String)it.next();
                query.setParameter(key, parameters.get(key));
            }
        }

        ((SQLQuery)query.unwrap(SQLQuery.class)).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public int executeUpdate(String hql, Map<String, ?> parameters) {
        javax.persistence.Query query = this.getEm().createQuery(hql);
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();

            while(it.hasNext()) {
                String key = (String)it.next();
                query.setParameter(key, parameters.get(key));
            }
        }

        return query.executeUpdate();
    }

    public int executeSqlUpdate(String sql, Map<String, ?> parameters) {
        javax.persistence.Query query = this.getEm().createNativeQuery(sql);
        if (parameters != null) {
            Iterator it = parameters.keySet().iterator();

            while(it.hasNext()) {
                String key = (String)it.next();
                query.setParameter(key, parameters.get(key));
            }
        }

        return query.executeUpdate();
    }

    private String filterOrderBy(String hql) {
        String word = "([\\w\\.]+\\s*\\([\\s\\S]*?\\)|[\\w\\.]+)(\\s+(desc|asc))?";
        String pattern = "(?i)order[\\s]+by\\s+" + word + "(\\s*,\\s*" + word + ")*";
        return hql.replaceAll(pattern, "");
    }

    public static void main(String[] args) {
        BaseJpaDaoImpl bjdi = new BaseJpaDaoImpl();
        String hql = "select * from (select * from table order by a.aaa , b.bbb ) order by c.ccc(mm , nn) , dd.dd asc";
        String result = bjdi.filterOrderBy(hql);
        System.out.println(result);
    }

    private String parseFilterItemMap(Map<String, ?> filterItemMap, Map<String, Object> paramMap, String hql, Map<Class, String> clazzMap, Order[] orders) {
        if (hql.indexOf("where") == -1) {
            hql = hql + " where";
        }

        hql = hql + " ";
        if (filterItemMap != null) {
            Iterator iterator = filterItemMap.keySet().iterator();

            label47:
            while(true) {
                String key;
                FilterItem[] filterValueArray;
                do {
                    if (!iterator.hasNext()) {
                        break label47;
                    }

                    key = (String)iterator.next();
                    filterValueArray = new FilterItem[0];
                    if (filterItemMap.get(key) instanceof Collection) {
                        filterValueArray = (FilterItem[])((FilterItem[])((Collection)filterItemMap.get(key)).toArray(new FilterItem[0]));
                    } else if (filterItemMap.get(key) instanceof FilterItem[]) {
                        filterValueArray = (FilterItem[])((FilterItem[])filterItemMap.get(key));
                    }
                } while(filterValueArray.length == 0);

                FilterItem[] var9 = filterValueArray;
                int var10 = filterValueArray.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    FilterItem filterValue = var9[var11];
                    if (!hql.trim().endsWith("where")) {
                        hql = hql + " and ";
                    }

                    hql = hql + FilterConverter.toHqlCondition(key, paramMap, filterValue);
                }
            }
        }

        if (orders != null) {
            String order = FilterConverter.toHqlOrder(clazzMap, orders);
            if (order != null && !"".equals(order.trim())) {
                hql = hql + " order by " + order;
            }
        }

        return hql;
    }

    private void parseParameterMap(Map<String, Object> paramMap, Map<String, Object> parameters, javax.persistence.Query query) {
        if (query != null) {
            Iterator it;
            String key;
            if (paramMap != null) {
                it = paramMap.keySet().iterator();

                while(it.hasNext()) {
                    key = (String)it.next();
                    query.setParameter(key, paramMap.get(key));
                }
            }

            if (parameters != null) {
                it = parameters.keySet().iterator();

                while(it.hasNext()) {
                    key = (String)it.next();
                    query.setParameter(key, parameters.get(key));
                }
            }

        }
    }

    public EntityManager getEm() {
        return this.em;
    }
}

*/
