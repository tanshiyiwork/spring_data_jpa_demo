package com.jpa.query;

import com.jpa.filter.FilterConverter;
import com.jpa.filter.FilterGroup;
import com.jpa.filter.FilterItem;
import com.jpa.filter.Order;
import com.jpa.operator.FilterOperator;
import com.jpa.operator.GroupOperator;
import com.jpa.utils.StringUtil;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

public class Query<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Logger logger = Logger.getLogger(Query.class);
    private List<FilterItem> filterItems;
    private List<FilterGroup> filterGroups;
    private List<Order> orders;
    private Class<?> entityClass;

    public Query<T> filter(FilterOperator op, String field, Object value) {
        this.filterItems.add(new FilterItem(op, field, value));
        return this;
    }

    public Query<T> filter(FilterOperator op, String field, Object value1, Object value2) {
        this.filterItems.add(new FilterItem(op, field, value1, value2));
        return this;
    }

    public Query<T> filter(FilterItem filterItem) {
        this.filterItems.add(filterItem);
        return this;
    }

    public Query<T> filter(FilterGroup filterGroup) {
        this.filterGroups.add(filterGroup);
        return this;
    }

    public Query<T> filter(Object fieldValueMap, Map<String, FilterOperator> fieldOperaotorMap) {
        if (fieldValueMap != null) {
            this.handleFieldValueAndFilterOperatorMap(fieldValueMap, fieldOperaotorMap, FilterOperator.EQUAL);
        }

        return this;
    }

    public Query<T> filter(Object fieldValueMap, Map<String, FilterOperator> fieldOperaotorMap, FilterOperator defaultOperator) {
        if (defaultOperator == null) {
            defaultOperator = FilterOperator.EQUAL;
        }

        if (fieldValueMap != null) {
            this.handleFieldValueAndFilterOperatorMap(fieldValueMap, fieldOperaotorMap, defaultOperator);
        }

        return this;
    }

    public Query<T> orderBy(Order order) {
        this.orders.add(order);
        return this;
    }

    public Specification<?> getRestriction() {
        Specification spec = new Specification() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> defaultPredList = new ArrayList();
                Iterator itx = Query.this.filterItems.iterator();

                while(itx.hasNext()) {
                    FilterItem item = (FilterItem)itx.next();
                    Predicate tempP = FilterConverter.filterItemToPredicate(root, cb, (GroupOperator)null, new FilterItem[]{item});
                    if (tempP != null) {
                        defaultPredList.add(tempP);
                    }
                }

                Predicate[] defaultPreds = new Predicate[defaultPredList.size()];
                Predicate p = cb.and((Predicate[])defaultPredList.toArray(defaultPreds));

                FilterGroup group;
                Iterator it;
                for(it = Query.this.filterGroups.iterator(); it.hasNext(); p = FilterConverter.filterItemToPredicate(p, root, cb, GroupOperator.AND, group)) {
                    group = (FilterGroup)it.next();
                }

                if (p != null) {
                    query.where(p);
                }

                it = Query.this.orders.iterator();

                while(it.hasNext()) {
                    Order order = (Order)it.next();
                    query.orderBy(new javax.persistence.criteria.Order[]{FilterConverter.convertOrder(root, cb, order)});
                }

                return query.getRestriction();
            }
        };
        return spec;
    }

    public void clearOrders() {
        this.orders = new ArrayList();
    }

    public CriteriaQuery<?> getQuery(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> defaultPredList = new ArrayList();
        Iterator it = this.filterItems.iterator();

        while(it.hasNext()) {
            FilterItem item = (FilterItem)it.next();
            Predicate tempP = FilterConverter.filterItemToPredicate(root, cb, (GroupOperator)null, new FilterItem[]{item});
            if (tempP != null) {
                defaultPredList.add(tempP);
            }
        }

        Predicate[] defaultPreds = new Predicate[defaultPredList.size()];
        Predicate p = cb.and((Predicate[])defaultPredList.toArray(defaultPreds));

        FilterGroup group;
        for(Iterator itx = this.filterGroups.iterator(); itx.hasNext(); p = FilterConverter.filterItemToPredicate(p, root, cb, GroupOperator.AND, group)) {
            group = (FilterGroup)itx.next();
        }

        if (p != null) {
            query.where(p);
        }

        List<javax.persistence.criteria.Order> orderList = new ArrayList();
        Iterator itx = this.orders.iterator();

        while(itx.hasNext()) {
            Order order = (Order)itx.next();
            orderList.add(FilterConverter.convertOrder(root, cb, order));
        }

        if (orderList.size() > 0) {
            query.orderBy(orderList);
        }

        return query;
    }

    public List<FilterItem> getFilterItems() {
        return this.filterItems;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    private Query() {
        this.init();
    }

    private Query(Class<?> entityClass) {
        this.init();
        this.entityClass = entityClass;
    }

    private void init() {
        this.filterItems = new ArrayList();
        this.filterGroups = new ArrayList();
        this.orders = new ArrayList();
    }

    public static <T> Query<T> from(Class<T> entityClass) {
        return new Query(entityClass);
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    private void handleFieldValueAndFilterOperatorMap(Object root, Map<String, FilterOperator> fieldOperaotorMap, FilterOperator defaultOperator) {
        if (root != null) {
            Iterator<String> it = null;
            if (root instanceof Map) {
                it = ((Map)root).keySet().iterator();
            } else {
                Field[] fs = root.getClass().getDeclaredFields();
                HashSet<String> set = new HashSet();

                for(int i = 0; i < fs.length; ++i) {
                    Field field = fs[i];
                    set.add(field.getName());
                }

                it = set.iterator();
            }

            while(it.hasNext()) {
                String field = (String)it.next();

                try {
                    if (field.indexOf(".") != -1 || this.hasField(field, this.entityClass)) {
                        Object value = this.getFieldValue(field, root);
                        if (value != null && StringUtil.isValidStr(value.toString())) {
                            FilterOperator op = null;
                            if (fieldOperaotorMap != null) {
                                op = (FilterOperator)fieldOperaotorMap.get(field);
                            }

                            if (op == null && defaultOperator != null) {
                                op = defaultOperator;
                            }

                            switch(op) {
                                case EQUAL:
                                    this.filterItems.add(Filters.equal(field, value));
                                    break;
                                case LEFTLIKE:
                                    this.filterItems.add(Filters.rightLike(field, value));
                                    break;
                                case RIGHTLIKE:
                                    this.filterItems.add(Filters.leftLike(field, value));
                                    break;
                                case LIKE:
                                    this.filterItems.add(Filters.like(field, value));
                                    break;
                                case LT:
                                    this.filterItems.add(Filters.lessThan(field, value));
                                    break;
                                case LE:
                                    this.filterItems.add(Filters.lessThanOrEqualTo(field, value));
                                    break;
                                case GE:
                                    this.filterItems.add(Filters.greaterThan(field, value));
                                    break;
                                case GT:
                                    this.filterItems.add(Filters.greaterThanOrEqualTo(field, value));
                                    break;
                                default:
                                    this.filterItems.add(Filters.equal(field, value));
                            }
                        }
                    }
                } catch (Exception var9) {
                }
            }

        }
    }

    private Object getFieldValue(String field, Object root) throws OgnlException {
        return root instanceof Map ? ((Map)root).get(field) : Ognl.getValue(field, root);
    }

    private boolean hasField(String fieldStr, Class clazz) {
        for(; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(fieldStr);
                if (field != null) {
                    return true;
                }
            } catch (Exception var4) {
            }
        }

        return false;
    }
}
