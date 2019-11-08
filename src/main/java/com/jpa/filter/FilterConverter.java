package com.jpa.filter;

import com.jpa.operator.FilterOperator;
import com.jpa.operator.GroupOperator;
import com.jpa.operator.OrderOperator;
import com.jpa.utils.StringUtil;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import org.apache.log4j.Logger;

import static com.jpa.operator.OrderOperator.DESC;

public class FilterConverter {
    public static final Logger logger = Logger.getLogger(FilterConverter.class);

    public FilterConverter() {
    }

    private static <T, R> Path<R> getPath(Class<R> resultType, Root<?> root, String path) {
        String[] pathElements = path.split("\\.");
        Path<?> retVal = root;
        String[] var5 = pathElements;
        int var6 = pathElements.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String pathEl = var5[var7];
            retVal = ((Path)retVal).get(pathEl);
            if (Collection.class.isAssignableFrom(((Path)retVal).getJavaType())) {
                Join<Object, Object> lfJoin = null;
                Attribute<?, ?> attribute = root.getModel().getAttribute(pathEl);
                if (PluralAttribute.class.isAssignableFrom(attribute.getClass())) {
                    Class cls = ((PluralAttribute)attribute).getBindableJavaType();
                    Iterator iter = root.getJoins().iterator();

                    while(iter.hasNext()) {
                        Join join = (Join)iter.next();
                        if (join.getJavaType().equals(cls)) {
                            lfJoin = join;
                            break;
                        }
                    }
                }

                if (lfJoin == null) {
                    lfJoin = root.join(pathEl, JoinType.LEFT);
                }

                retVal = lfJoin;
            }
        }

        return (Path)retVal;
    }

    public static Predicate filterItemToPredicate(Root<?> root, CriteriaBuilder cb, GroupOperator op, FilterItem... items) {
        Predicate p = null;
        if (items != null) {
            FilterItem[] var5 = items;
            int var6 = items.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                FilterItem item = var5[var7];

                try {
                    Predicate temp = null;
                    Object value = item.getValue();
                    value = processLikeValue(value, item.getFilterOperator());
                    switch(item.getFilterOperator()) {
                        case EQUAL:
                            temp = cb.equal(getPath(item.getField().getClass(), root, item.getField()), value);
                            break;
                        case LIKE:
                            temp = cb.like(getPath(String.class, root, item.getField()), value.toString());
                            break;
                        case LEFTLIKE:
                            temp = cb.like(getPath(String.class, root, item.getField()), value.toString());
                            break;
                        case RIGHTLIKE:
                            temp = cb.like(getPath(String.class, root, item.getField()), value.toString());
                            break;
                        case GT:
                            if (value instanceof String) {
                                temp = cb.greaterThan(getPath(String.class, root, item.getField()), (String)value);
                            } else if (value instanceof Integer) {
                                temp = cb.greaterThan(getPath(Integer.class, root, item.getField()), (Integer)value);
                            } else if (value instanceof Double) {
                                temp = cb.greaterThan(getPath(Double.class, root, item.getField()), (Double)value);
                            } else if (value instanceof Float) {
                                temp = cb.greaterThan(getPath(Float.class, root, item.getField()), (Float)value);
                            } else if (value instanceof Date) {
                                temp = cb.greaterThan(getPath(Date.class, root, item.getField()), (Date)value);
                            } else {
                                logger.warn("字段" + item.getField() + " 类型" + item.getField().getClass() + "不能进行小于比较");
                            }
                            break;
                        case GE:
                            if (value instanceof String) {
                                temp = cb.greaterThanOrEqualTo(getPath(String.class, root, item.getField()), (String)value);
                            } else if (value instanceof Integer) {
                                temp = cb.greaterThanOrEqualTo(getPath(Integer.class, root, item.getField()), (Integer)value);
                            } else if (value instanceof Double) {
                                temp = cb.greaterThanOrEqualTo(getPath(Double.class, root, item.getField()), (Double)value);
                            } else if (value instanceof Float) {
                                temp = cb.greaterThanOrEqualTo(getPath(Float.class, root, item.getField()), (Float)value);
                            } else if (value instanceof Date) {
                                temp = cb.greaterThanOrEqualTo(getPath(Date.class, root, item.getField()), (Date)value);
                            } else {
                                logger.warn("字段" + item.getField() + " 类型" + item.getField().getClass() + "不能进行小于等于比较");
                            }
                            break;
                        case LT:
                            if (value instanceof String) {
                                temp = cb.lessThan(getPath(String.class, root, item.getField()), (String)value);
                            } else if (value instanceof Integer) {
                                temp = cb.lessThan(getPath(Integer.class, root, item.getField()), (Integer)value);
                            } else if (value instanceof Double) {
                                temp = cb.lessThan(getPath(Double.class, root, item.getField()), (Double)value);
                            } else if (value instanceof Float) {
                                temp = cb.lessThan(getPath(Float.class, root, item.getField()), (Float)value);
                            } else if (value instanceof Date) {
                                temp = cb.lessThan(getPath(Date.class, root, item.getField()), (Date)value);
                            } else {
                                logger.warn("字段" + item.getField() + " 类型" + item.getField().getClass() + "不能进行大于比较");
                            }
                            break;
                        case LE:
                            if (value instanceof String) {
                                temp = cb.lessThanOrEqualTo(root.get(item.getField()).as(String.class), (String)value);
                            } else if (value instanceof Integer) {
                                temp = cb.lessThanOrEqualTo(getPath(Integer.class, root, item.getField()), (Integer)value);
                            } else if (value instanceof Double) {
                                temp = cb.lessThanOrEqualTo(getPath(Double.class, root, item.getField()), (Double)value);
                            } else if (value instanceof Float) {
                                temp = cb.lessThanOrEqualTo(getPath(Float.class, root, item.getField()), (Float)value);
                            } else if (value instanceof Date) {
                                temp = cb.lessThanOrEqualTo(getPath(Date.class, root, item.getField()), (Date)value);
                            } else {
                                logger.warn("字段" + item.getField() + " 类型" + item.getField().getClass() + "不能进行大于等于比较");
                            }
                            break;
                        case BETWEEN:
                            Object value2 = item.getAnotherValue();
                            if (value instanceof String) {
                                temp = cb.between(getPath(String.class, root, item.getField()), (String)value, (String)value2);
                            } else if (value instanceof Integer) {
                                temp = cb.between(getPath(Integer.class, root, item.getField()), (Integer)value, (Integer)value2);
                            } else if (value instanceof Double) {
                                temp = cb.between(getPath(Double.class, root, item.getField()), (Double)value, (Double)value2);
                            } else if (value instanceof Float) {
                                temp = cb.between(getPath(Float.class, root, item.getField()), (Float)value, (Float)value2);
                            } else if (value instanceof Date) {
                                temp = cb.between(getPath(Date.class, root, item.getField()), (Date)value, (Date)value2);
                            } else {
                                logger.warn("字段" + item.getField() + " 类型" + item.getField().getClass() + "不能进行大于等于比较");
                            }
                            break;
                        case NOTEQUAL:
                            temp = cb.notEqual(getPath(Object.class, root, item.getField()), value);
                            break;
                        case NOTLIKE:
                            temp = cb.notLike(getPath(String.class, root, item.getField()), value.toString());
                            break;
                        case ISNULL:
                            temp = cb.isNull(getPath(Object.class, root, item.getField()));
                            break;
                        case ISNOTNULL:
                            temp = cb.isNotNull(getPath(Object.class, root, item.getField()));
                            break;
                        case IN:
                            Expression<?> exp = getPath(Object.class, root, item.getField());
                            temp = exp.in(new Object[]{value});
                    }

                    if (p == null) {
                        p = temp;
                    } else if (GroupOperator.OR.equals(op)) {
                        p = cb.or(p, temp);
                    } else {
                        p = cb.and(p, temp);
                    }
                } catch (Exception var13) {
                }
            }
        }

        return p;
    }

    public static javax.persistence.criteria.Order convertOrder(Root<?> root, CriteriaBuilder cb, com.jpa.filter.Order order) {
        Order o = null;
        switch(order.getOrderOperator()) {
            case DESC:
                o = cb.desc(getPath(Object.class, root, order.getField()));
                break;
            case ASC:
                o = cb.asc(getPath(Object.class, root, order.getField()));
        }

        return o;
    }

    public static Predicate filterItemToPredicate(Predicate p, Root<?> root, CriteriaBuilder cb, GroupOperator op, FilterGroup group) {
        Predicate tempP = filterItemToPredicate(root, cb, group.getGroupOperator(), group.getFilterItems());
        if (group != null) {
            FilterGroup[] fGroup = group.getFilterGroups();
            if (fGroup != null) {
                FilterGroup[] var7 = fGroup;
                int var8 = fGroup.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    FilterGroup filterGroup = var7[var9];
                    switch(group.getGroupOperator()) {
                        case AND:
                            tempP = filterItemToPredicate(tempP, root, cb, group.getGroupOperator(), filterGroup);
                            break;
                        case OR:
                            tempP = filterItemToPredicate(tempP, root, cb, group.getGroupOperator(), filterGroup);
                    }
                }
            }
        }

        if (tempP != null) {
            switch(op) {
                case AND:
                    p = cb.and(p, tempP);
                    break;
                case OR:
                    p = cb.or(p, tempP);
            }
        }

        return p;
    }

    public static String toHqlCondition(String fieldEntityName, Map<String, Object> paramMap, FilterItem item) {
        String paramKey = fieldEntityName + "_" + item.getField();

        for(int var4 = 1; paramMap.containsKey(paramKey); paramKey = paramKey + var4++) {
        }

        Object value = item.getValue();
        value = processLikeValue(value, item.getFilterOperator());
        paramMap.put(paramKey, value);
        String temp = null;
        String fieldName = item.getField();
        switch(item.getFilterOperator()) {
            case EQUAL:
                temp = fieldEntityName + "." + fieldName + " = :" + paramKey;
                break;
            case LIKE:
                temp = fieldEntityName + "." + fieldName + " like :" + paramKey;
                break;
            case LEFTLIKE:
                temp = fieldEntityName + "." + fieldName + " like :" + paramKey;
                break;
            case RIGHTLIKE:
                temp = fieldEntityName + "." + fieldName + " like :" + paramKey;
                break;
            case GT:
                temp = fieldEntityName + "." + fieldName + " > :" + paramKey;
                break;
            case GE:
                temp = fieldEntityName + "." + fieldName + " >= :" + paramKey;
                break;
            case LT:
                temp = fieldEntityName + "." + fieldName + " < :" + paramKey;
                break;
            case LE:
                temp = fieldEntityName + "." + fieldName + " <= :" + paramKey;
                break;
            case BETWEEN:
                String betweenKeyEnd = paramKey + "_a";
                paramMap.put(betweenKeyEnd, item.getAnotherValue());
                temp = fieldEntityName + "." + fieldName + " between :" + paramKey + " and :" + betweenKeyEnd;
                break;
            case NOTEQUAL:
                temp = fieldEntityName + "." + fieldName + " != :" + paramKey;
                break;
            case NOTLIKE:
                temp = fieldEntityName + "." + fieldName + " not like :" + paramKey;
                break;
            case ISNULL:
                temp = fieldEntityName + "." + fieldName + " is null";
                break;
            case ISNOTNULL:
                temp = fieldEntityName + "." + fieldName + " is not null";
                break;
            case IN:
                temp = fieldEntityName + "." + fieldName + " in :" + paramKey;
        }

        return temp;
    }

    public static String toHqlOrder(Map<Class, String> clazzMap, com.jpa.filter.Order[] orders) {
        if (orders == null) {
            return null;
        } else {
            boolean isFirst = true;
            String order = "";

            for(int i = 0; i < orders.length; ++i) {
                com.jpa.filter.Order orderFilter = orders[i];
                String alias = getAlias(clazzMap, orderFilter.getClazzName());
                if (alias != null) {
                    String oneOrder = toHqlOrder(orderFilter, alias);
                    if (isFirst) {
                        order = oneOrder;
                        isFirst = false;
                    } else {
                        order = order + " ," + oneOrder;
                    }
                }
            }

            return order;
        }
    }

    private static Object processLikeValue(Object value, FilterOperator op) {
        switch(op) {
            case LIKE:
                if (value != null && StringUtil.isValidStr(value.toString()) && value.toString().indexOf("%") == -1 && value.toString().indexOf("_") == -1) {
                    value = "%" + value + "%";
                }
                break;
            case LEFTLIKE:
                if (value != null && StringUtil.isValidStr(value.toString()) && value.toString().indexOf("%") == -1 && value.toString().indexOf("_") == -1) {
                    value = value + "%";
                }
            case RIGHTLIKE:
                if (value != null && StringUtil.isValidStr(value.toString()) && value.toString().indexOf("%") == -1 && value.toString().indexOf("_") == -1) {
                    value = "%" + value;
                }
        }

        return value;
    }

    private static String toHqlOrder(com.jpa.filter.Order orderFilter, String alias) {
        String order = alias + "." + orderFilter.getField();
        if (DESC.equals(orderFilter.getOrderOperator())) {
            order = order + " desc ";
        }

        return order;
    }

    private static String getAlias(Map<Class, String> clazzMap, String entityName) {
        if (clazzMap != null) {
            Set<Class> keySet = clazzMap.keySet();
            Iterator iter = keySet.iterator();

            while(iter.hasNext()) {
                Class clazz = (Class)iter.next();
                if (clazz.getName().equals(entityName)) {
                    return (String)clazzMap.get(clazz);
                }
            }
        }

        return null;
    }
}
