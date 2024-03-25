package com.Orders;

public class OrdersDAOException extends Exception {
	private String errorCode;

    public OrdersDAOException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
