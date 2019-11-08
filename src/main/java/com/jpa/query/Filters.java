package com.jpa.query;

import com.jpa.filter.FilterGroup;
import com.jpa.filter.FilterItem;
import com.jpa.operator.FilterOperator;
import com.jpa.operator.GroupOperator;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class Filters {
    public static final Logger logger = Logger.getLogger(Filters.class);

    public Filters() {
    }

    public static FilterItem equal(String field, Object value) {
        return new FilterItem(FilterOperator.EQUAL, field, value);
    }

    public static FilterItem like(String field, Object value) {
        return new FilterItem(FilterOperator.LIKE, field, value);
    }

    public static FilterItem leftLike(String field, Object value) {
        return new FilterItem(FilterOperator.LEFTLIKE, field, value);
    }

    public static FilterItem rightLike(String field, Object value) {
        return new FilterItem(FilterOperator.RIGHTLIKE, field, value);
    }

    public static FilterItem lessThan(String field, Object value) {
        return new FilterItem(FilterOperator.LT, field, value);
    }

    public static FilterItem lessThanOrEqualTo(String field, Object value) {
        return new FilterItem(FilterOperator.LE, field, value);
    }

    public static FilterItem greaterThan(String field, Object value) {
        return new FilterItem(FilterOperator.GT, field, value);
    }

    public static FilterItem greaterThanOrEqualTo(String field, Object value) {
        return new FilterItem(FilterOperator.GE, field, value);
    }

    public static FilterItem between(String field, Object value1, Object value2) {
        if (value1 == null && value2 != null) {
            return new FilterItem(FilterOperator.LE, field, value2);
        } else {
            return value1 != null && value2 == null ? new FilterItem(FilterOperator.GE, field, value1) : new FilterItem(FilterOperator.BETWEEN, field, value1, value2);
        }
    }

    public static FilterItem betweenWithBoundaryValue(String field, Date value1, Date value2) {
        if (value2 != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(value2);
            cal.add(5, 1);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            value2 = cal.getTime();
        }

        if (value1 == null && value2 != null) {
            return new FilterItem(FilterOperator.LE, field, value2);
        } else {
            return value1 != null && value2 == null ? new FilterItem(FilterOperator.GE, field, value1) : new FilterItem(FilterOperator.BETWEEN, field, value1, value2);
        }
    }

    public static FilterItem notEqual(String field, Object value) {
        return new FilterItem(FilterOperator.NOTEQUAL, field, value);
    }

    public static FilterItem notLike(String field, Object value) {
        return new FilterItem(FilterOperator.NOTLIKE, field, value);
    }

    public static FilterItem isNull(String field) {
        return new FilterItem(FilterOperator.ISNULL, field, (Object)null);
    }

    public static FilterItem isNotNull(String field) {
        return new FilterItem(FilterOperator.ISNOTNULL, field, (Object)null);
    }

    public static FilterGroup and(FilterItem item1, FilterItem item2, FilterItem... items) {
        return (new FilterGroup(GroupOperator.AND)).add(item1, item2, items);
    }

    public static FilterGroup and(FilterItem[] items) {
        return (new FilterGroup(GroupOperator.AND)).setFilterItems(items);
    }

    public static FilterGroup or(FilterItem item1, FilterItem item2, FilterItem... items) {
        return (new FilterGroup(GroupOperator.OR)).add(item1, item2, items);
    }

    public static FilterGroup or(FilterItem[] items) {
        return (new FilterGroup(GroupOperator.OR)).setFilterItems(items);
    }

    public static FilterItem in(String field, Collection<?> value) {
        return new FilterItem(FilterOperator.IN, field, value);
    }
}
