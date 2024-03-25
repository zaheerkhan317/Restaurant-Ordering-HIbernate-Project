package com.UserRegistration;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Admin.Admin;
import com.Main.Dashboard;
import com.Main.Main;

public class UserRegistrationDAO {
	
	private Session session;
	
	public UserRegistrationDAO(Session session) {
		this.session = session;
	}
	
	public void insertUser(UserRegistration UReg) throws UserRegistrationDAOException {
		try {
			session.beginTransaction();
			
			if (UReg.getUsername() == null || UReg.getUsername().trim().isEmpty() ||
	                UReg.getEmail() == null || UReg.getEmail().trim().isEmpty() ||
	                UReg.getPhone() == null || UReg.getPhone().trim().isEmpty() ||
	                UReg.getPassword() == null || UReg.getPassword().trim().isEmpty()) {
	                throw new UserRegistrationDAOException("One or more required fields are null or empty.", "INVALID_USER_DATA_ERROR");
	                
	            }
						
            List<UserRegistration> queryByEmail = session.createQuery("FROM UserRegistration WHERE Email = :email", UserRegistration.class).setParameter("email", UReg.getEmail()).getResultList();
            List<UserRegistration> queryByPhone = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class).setParameter("phone", UReg.getPhone()).getResultList();
   
            if (!queryByEmail.isEmpty() || !queryByPhone.isEmpty()) {
                throw new UserRegistrationDAOException("User with the same email or phone already exists.", "DUPLICATE_USER_ERROR");
            }

            session.save(UReg);
            session.getTransaction().commit();
            System.out.println("");
        	System.out.println("==========================");
        	System.out.println("Registration successful!!!");
        	System.out.println("==========================");
        	System.out.println("");
        	
        	Main.start(session);
		}
		catch (Exception e) {
	        System.out.println("Error code: " + ((UserRegistrationDAOException) e).getErrorCode());
	        System.out.println("Error message: " + ((UserRegistrationDAOException) e).getMessage());
	    }
	}
	
	
	public void insertUserByAdmin(UserRegistration UReg)throws UserRegistrationDAOException{
		
		try {
			session.beginTransaction();
			
			if (UReg.getUsername() == null || UReg.getUsername().trim().isEmpty() ||
	                UReg.getEmail() == null || UReg.getEmail().trim().isEmpty() ||
	                UReg.getPhone() == null || UReg.getPhone().trim().isEmpty() ||
	                UReg.getPassword() == null || UReg.getPassword().trim().isEmpty()) {
	                throw new UserRegistrationDAOException("One or more required fields are null or empty.", "INVALID_USER_DATA_ERROR");
	                
	            }
						
            List<UserRegistration> queryByEmail = session.createQuery("FROM UserRegistration WHERE Email = :email", UserRegistration.class).setParameter("email", UReg.getEmail()).getResultList();
            List<UserRegistration> queryByPhone = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class).setParameter("phone", UReg.getPhone()).getResultList();
   
            if (!queryByEmail.isEmpty() || !queryByPhone.isEmpty()) {
                throw new UserRegistrationDAOException("User with the same email or phone already exists.", "DUPLICATE_USER_ERROR");
            }

            session.save(UReg);
            session.getTransaction().commit();
            System.out.println("");
        	System.out.println("==========================");
        	System.out.println("Registration successful!!!");
        	System.out.println("==========================");
        	System.out.println("");
        	
        	Dashboard.adminLogin(session);
		}
		catch (Exception e) {
	        System.out.println("Error code: " + ((UserRegistrationDAOException) e).getErrorCode());
	        System.out.println("Error message: " + ((UserRegistrationDAOException) e).getMessage());
	        
	        Dashboard.adminLogin(session);
	    }
		
	}
	
	
	public void DeleteUserByAdmin(UserRegistration UReg)throws UserRegistrationDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
			
			if (UReg.getPhone() == null || UReg.getPhone().trim().isEmpty()) {
	                throw new UserRegistrationDAOException("Field is empty", "INVALID_USER_PHONE_DATA_ERROR");  
	            }
				
			UserRegistration query = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class)
                    .setParameter("phone", UReg.getPhone())
                    .uniqueResult();
		    
		    if (query == null) {
	            throw new UserRegistrationDAOException("User not found with phone number: " + UReg.getPhone(), "USER_NOT_FOUND_ERROR");
	        }
		    
		    session.delete(query);
            session.getTransaction().commit();
        	
            System.out.println("");
            System.out.println("============================");
            System.out.println("User deleted successfully!!!");
            System.out.println("============================");
            System.out.println("");
            
        	Dashboard.adminLogin(session);
		}
		catch (UserRegistrationDAOException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error code: " + e.getErrorCode());
	        System.out.println("Error message: " + e.getMessage());
	        Dashboard.adminLogin(session);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        Dashboard.adminLogin(session);
	    } finally {
	        session.close();
	    }
		
	}
	
	public void UpdateUserByAdmin(UserRegistration UReg) throws UserRegistrationDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
			
			if (UReg.getUsername() == null || UReg.getUsername().trim().isEmpty() ||
	                UReg.getEmail() == null || UReg.getEmail().trim().isEmpty() ||
	                UReg.getPhone() == null || UReg.getPhone().trim().isEmpty() ||
	                UReg.getPassword() == null || UReg.getPassword().trim().isEmpty()) {
	                throw new UserRegistrationDAOException("One or more required fields are null or empty.", "INVALID_USER_DATA_ERROR");
	            }
				
			//UserRegistration query = session.get(UserRegistration.class, UReg.getId());
			UserRegistration query = session.createQuery("FROM UserRegistration WHERE Email = :email", UserRegistration.class)
                    .setParameter("email", UReg.getEmail())
                    .uniqueResult();
		    
		    if (query == null) {
	            throw new UserRegistrationDAOException("User not found with the email: " + UReg.getEmail(), "USER_NOT_FOUND_ERROR");
	        }
		    
		    query.setEmail(UReg.getEmail());
		    query.setUsername(UReg.getUsername());
		    query.setPassword(UReg.getPassword());
		    query.setPhone(UReg.getPhone());
		    
		    
		    session.update(query);
            session.getTransaction().commit();
        	
            System.out.println("");
            System.out.println("============================");
            System.out.println("User Updated successfully!!!");
            System.out.println("============================");
            System.out.println("");
            
        	Dashboard.adminLogin(session);
		}
		catch (UserRegistrationDAOException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error code: " + e.getErrorCode());
	        System.out.println("Error message: " + e.getMessage());
	        Dashboard.adminLogin(session);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        Dashboard.adminLogin(session);
	    } finally {
	        session.close();
	    }
		
	}
}
