package com.UserLogin;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.Main.MainMenu;
import com.UserRegistration.UserRegistration;


public class UserLoginDAO {
	private Session session;
	
	public UserLoginDAO(Session session) {
		this.session = session;
	}
	
	public void validateLogin(UserLogin Ulog) throws UserLoginDAOException{
		try {
		    session.beginTransaction();
		    String query = "FROM UserRegistration where Email = :email AND Password = :password";
		    UserRegistration UReg = (UserRegistration) session.createQuery(query)
		            .setParameter("email", Ulog.getEmail())
		            .setParameter("password", Ulog.getPassword())
		            .uniqueResult();
		    
		    session.getTransaction().commit();
		    if (UReg != null) {
		        System.out.println("");
		        System.out.println("=====================");
		        System.out.println("Login Successful!!!!!");
		        System.out.println("=====================");
		        System.out.println("");
		        
		        
		        MainMenu.UserLogin(session);
		    } else {
		    	
		        System.out.println("");
		        System.out.println("=========================");
		        System.out.println("Email/Password wrong!!!!!");
		        System.out.println("=========================");
		        System.out.println("");
		        throw new UserLoginDAOException("Email/Password wrong!!!!!", "DAO_LOGIN_ERROR");
		    }
		} catch (HibernateException ex) {
		    if (session.getTransaction() != null) {
		        session.getTransaction().rollback();
		    }
		    ex.printStackTrace(); 
		} catch (UserLoginDAOException e) {
		    System.out.println("Error code: " + e.getErrorCode());
		    System.out.println("Error message: " + e.getMessage());
		} catch (Exception e) {
		    e.printStackTrace(); 
		}

	}
	
	
	
	
	
}
