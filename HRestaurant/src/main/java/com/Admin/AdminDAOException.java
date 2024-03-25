package com.Admin;

import org.hibernate.Session;

import com.Main.Main;

public class AdminDAOException extends Exception {
	private String errorCode;
	
	private Session session;
	
    public AdminDAOException(String message, String errorCode, Session session) {
        super(message);
        this.errorCode = errorCode;
        this.session = session;
        Main.start(session);
    }

    public String getErrorCode() {
        return errorCode;
    }
}