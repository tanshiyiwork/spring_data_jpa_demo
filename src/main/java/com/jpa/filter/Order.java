package com.jpa.filter;

import com.jpa.operator.OrderOperator;
import org.apache.log4j.Logger;

import java.io.Serializable;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Logger logger = Logger.getLogger(Order.class);
    private OrderOperator op;
    private String field;
    private String clazzName;

    public Order(OrderOperator op, String field) {
        this.op = op;
        this.field = field;
    }

    public Order(OrderOperator op, String field, String clazzName) {
        this.op = op;
        this.field = field;
        this.clazzName = clazzName;
    }

    public OrderOperator getOrderOperator() {
        return this.op;
    }

    public String getField() {
        return this.field;
    }

    public String getClazzName() {
        return this.clazzName;
    }
}
