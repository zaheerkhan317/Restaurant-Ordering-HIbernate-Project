package com.Admin;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Main.Dashboard;
import com.Main.Main;


public class AdminDAO {

	private Session session;
	
	public AdminDAO(Session session) {
		this.session = session;
	}
	
	public void insertAdmin(Admin ad, Session session) throws AdminDAOException {
		
		Transaction tr = session.beginTransaction();
		
		try {
			boolean ordersTableExists = session.getMetamodel().getEntities().stream()
                    .anyMatch(entityType -> "Admin".equals(entityType.getName()));
			if (ordersTableExists) { 
			    Admin existingAdmin = session.get(Admin.class, ad.getAdminId());
			    if (existingAdmin != null) {
			        existingAdmin.setAdminName(ad.getAdminName());
			        existingAdmin.setAdminEmail(ad.getAdminEmail());
			        existingAdmin.setAdminPassword(ad.getAdminPassword());
			        session.update(existingAdmin);
			    } else {
			        session.save(ad);
			    }
			}
			session.getTransaction().commit();
		}
		catch (ConstraintViolationException e) {
            tr.rollback();
            throw new AdminDAOException("Error inserting admin details: Constraint violation occurred", "CONSTRAINT_VIOLATION",session);
        } catch (Exception e) {
            tr.rollback();
            throw new AdminDAOException("Error inserting admin details", "GENERAL_ERROR",session);
        }
	}
	
	
	public void adminValidateLogin(Admin admin, String email, String password)throws AdminDAOException {
		
		try {
		    session.beginTransaction();
		    String query = "FROM Admin where adminEmail = :adminEmail AND adminPassword = :adminPassword";
		    Admin adm = (Admin) session.createQuery(query)
		            .setParameter("adminEmail", email)
		            .setParameter("adminPassword", password)
		            .uniqueResult();
		    
		    session.getTransaction().commit();
		    if (adm != null) {
		        System.out.println("");
		        System.out.println("===========================");
		        System.out.println("Admin Login Successful!!!!!");
		        System.out.println("===========================");
		        System.out.println("");
		        
		        Dashboard.adminLogin(session);
		        
		    } else {
		    	
		        System.out.println("");
		        System.out.println("=========================");
		        System.out.println("Email/Password wrong!!!!!");
		        System.out.println("=========================");
		        System.out.println("");
		        throw new AdminDAOException("Email/Password wrong!!!!!", "ADMIN_DAO_LOGIN_ERROR", session);
		        
		    }
		} catch (HibernateException ex) {
		    if (session.getTransaction() != null) {
		        session.getTransaction().rollback();
		    }
		    ex.printStackTrace(); 
		} catch (AdminDAOException e) {
		    System.out.println("Error code: " + e.getErrorCode());
		    System.out.println("Error message: " + e.getMessage());
		} catch (Exception e) {
		    e.printStackTrace(); 
		}

		
	}
	
	
	
	
	
	
}
