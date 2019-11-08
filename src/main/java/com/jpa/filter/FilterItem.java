package com.jpa.filter;

import com.jpa.operator.FilterOperator;
import org.apache.log4j.Logger;
import java.io.Serializable;

public class FilterItem implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Logger logger = Logger.getLogger(FilterItem.class);
    private FilterOperator op;
    private String field;
    private Object value;
    private Object anotherValue;

    public FilterItem(FilterOperator op, String field, Object value) {
        this.op = op;
        this.field = field;
        this.value = value;
    }

    public FilterItem(FilterOperator op, String field, Object value1, Object value2) {
        this.op = op;
        this.field = field;
        this.value = value1;
        this.anotherValue = value2;
    }

    public FilterOperator getFilterOperator() {
        return this.op;
    }

    public String getField() {
        return this.field;
    }

    public Object getValue() {
        return this.value;
    }

    public Object getAnotherValue() {
        return this.anotherValue;
    }
}
