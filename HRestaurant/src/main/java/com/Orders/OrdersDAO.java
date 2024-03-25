package com.Orders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Main.Dashboard;
import com.Main.MainMenu;
import com.UserRegistration.UserRegistration;
import com.UserRegistration.UserRegistrationDAOException;

public class OrdersDAO {

	private Session session;
	
	public OrdersDAO(Session session) {
		this.session = session;
	}
	
	public void insertOrder(Orders ord, String Email) throws OrdersDAOException{
		Transaction tr = session.beginTransaction();
		try {
			 boolean ordersTableExists = session.getMetamodel().getEntities().stream()
	                    .anyMatch(entityType -> "Orders".equals(entityType.getName()));
			 UserRegistration user = session.createQuery("FROM UserRegistration WHERE email = :email", UserRegistration.class)
                     .setParameter("email", Email)
                     .uniqueResult();
			 if (!ordersTableExists) { // Table doesn't exist, create it
	                String username = user.getUsername();
					String phone = user.getPhone(); 
					int id = user.getId();
					// Set order details
					ord.setOrderItem(ord.getOrderItem());
					ord.setQuantity(ord.getQuantity());
					ord.setPrice(ord.getPrice()*ord.getQuantity());
					ord.setUsername(username);
					ord.setPhone(phone);
					ord.setUserId(session.get(UserRegistration.class, id));
					LocalDate localDate = LocalDate.now();
					Date date = Date.valueOf(localDate);
					ord.setDate(date); // Set current date for the order
	                session.save(ord);
	                session.getTransaction().commit();
	                confirmation(session);
	            }
			 else {
				 // User found, retrieve data
				String username = user.getUsername();
				String phone = user.getPhone(); 
				int id = user.getId();
				// Set order details
				ord.setOrderItem(ord.getOrderItem());
				ord.setQuantity(ord.getQuantity());
				ord.setPrice(ord.getPrice()*ord.getQuantity());
				ord.setUsername(username);
				ord.setPhone(phone);
				ord.setUserId(session.get(UserRegistration.class, id));
				LocalDate localDate = LocalDate.now();
				Date date = Date.valueOf(localDate);
				ord.setDate(date); // Set current date for the order
				session.save(ord);
                session.getTransaction().commit();
                confirmation(session);
			 }
			 
		}
		catch (ConstraintViolationException e) {
            tr.rollback();
            throw new OrdersDAOException("Error inserting order: Constraint violation occurred", "CONSTRAINT_VIOLATION");
        } catch (Exception e) {
            tr.rollback();
            throw new OrdersDAOException("Error inserting order", "GENERAL_ERROR");
        }
	}
	
	
	
	public static void confirmation(Session session) {
	    System.out.println("");
	    System.out.println("Would you like to order anything else?");
	    System.out.println("Yes/No");
	    System.out.println("");
	    
	    Scanner sc = new Scanner(System.in);
	    String confirm = sc.nextLine();
	    if (confirm.equalsIgnoreCase("Yes") || confirm.equalsIgnoreCase("Y")) {
	        MainMenu.mainMenu(session);
	    }
	    else if(confirm.equalsIgnoreCase("No") || confirm.equalsIgnoreCase("N")){
	    	System.out.println("");
			System.out.println("==========================");
			System.out.println("Order placed successfully!");
			System.out.println("==========================");
			System.out.println("");
			
			MainMenu.UserLogin(session);
	    }
	    else {
	    	System.out.println("");
			System.out.println("=======================================");
			System.out.println("Invalid option/Something went wrong!!!!");
			System.out.println("=======================================");
			System.out.println("");
			MainMenu.UserLogin(session);
	    }
	    sc.close();

	}
	
	public static void viewOrders(Session session, String Email) {
		UserRegistration user = session.createQuery("FROM UserRegistration WHERE email = :email", UserRegistration.class)
                .setParameter("email", Email)
                .uniqueResult();
		int id = user.getId();
		List<Orders> userOrders = session.createQuery("FROM Orders WHERE UReg.id = :userId", Orders.class)
                .setParameter("userId", id)
                .getResultList();
		
		for (Orders order : userOrders) {
		System.out.print("Order ID: " + order.getOrderID()+" ");
		System.out.print("Order Item: " + order.getOrderItem()+" ");
		System.out.print("Price: " + order.getPrice()+" ");
		System.out.print("Quantity: " + order.getQuantity()+" ");
		System.out.print("Date: " + order.getDate()+" ");
		System.out.println();
		}
		MainMenu.UserLogin(session);
	}
	
	public static void cancelOrder(Session session, int id) {
		Orders order = session.createQuery("FROM Orders WHERE OrderID = :OrderID", Orders.class)
				.setParameter("OrderID", id)
				.getSingleResult();
		if(order!=null) {
			Transaction tr = session.beginTransaction();
			try {
				session.delete(order);
				session.getTransaction().commit();
				System.out.println("");
				System.out.println("====================================================");
				System.out.println("Order with ID :" + id + " cancelled successfully!!!!");
				System.out.println("====================================================");
				System.out.println("");
				MainMenu.UserLogin(session);
			}
			catch(Exception e) {
				tr.rollback();
				MainMenu.UserLogin(session);
			}
		}
	}
	
	
	public void ModifyOrderByAdmin(Orders ord, Session session) throws OrdersDAOException{
		
			Transaction tx = session.beginTransaction();
			try {
				
				Orders order = session.createQuery("FROM Orders WHERE OrderID = :OrderID", Orders.class)
						.setParameter("OrderID", ord.getOrderID())
						.getSingleResult();
				
				
				if (order == null) {
		            throw new OrdersDAOException("User not found with the Order ID: " + ord.getOrderID(), "ORDER_ID_NOT_FOUND_ERROR");
		        }
				else {
					System.out.println("ORDER_ID : "+ord.getOrderID()+ " ORDER_ITEM : "+order.getOrderItem()+ " QUANTITY: "+order.getQuantity() + " PRICE: "+order.getPrice());
				}
				
				Scanner sc = new Scanner(System.in);
				System.out.print("Enter the ORDER_ITEM to modify : ");
				order.setOrderItem(sc.nextLine());
				
				System.out.print("Enter the QUANTITY : ");
				order.setQuantity(sc.nextInt());
				
				System.out.print("Enter the PRICE : ");
				order.setPrice(sc.nextInt());
			    
			    session.update(order);
	            session.getTransaction().commit();
				
	            System.out.println("");
				System.out.println("================================================================");
				System.out.println("Order with ID :" + ord.getOrderID() + " updated successfully!!!!");
				System.out.println("================================================================");
				System.out.println("");
				sc.close();
				Dashboard.adminLogin(session);
				
			}
			catch (OrdersDAOException e) {
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
	
	
	
	public void CancelOrderByAdmin(Orders ord, Session session) throws OrdersDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
			
			Orders order = session.createQuery("FROM Orders WHERE OrderID = :OrderID", Orders.class)
					.setParameter("OrderID", ord.getOrderID())
					.getSingleResult();
			
			
			if (order == null) {
	            throw new OrdersDAOException("User not found with the Order ID: " + ord.getOrderID(), "ORDER_ID_NOT_FOUND_ERROR");
	        }
			else {
				System.out.println("ORDER_ID : "+ord.getOrderID()+ " ORDER_ITEM : "+order.getOrderItem()+ " QUANTITY: "+order.getQuantity() + " PRICE: "+order.getPrice());
			}
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Do you want to delete this Order : "+ord.getOrderID());
			System.out.print("Y/N :");
		    String op = sc.nextLine();
		    
		    if (op.equalsIgnoreCase("Yes") || op.equalsIgnoreCase("Y")) {
		    	session.delete(order);
	            session.getTransaction().commit();
	            
	            System.out.println("");
				System.out.println("================================================================");
				System.out.println("Order with ID :" + ord.getOrderID() + " deleted successfully!!!!");
				System.out.println("================================================================");
				System.out.println("");
				
				Dashboard.adminLogin(session);
				
		    } else if (op.equalsIgnoreCase("No") || op.equalsIgnoreCase("N")) {
		        Dashboard.adminLogin(session);
		        
		    } else {
		        System.out.println("Invalid input!!!!!");
		        Dashboard.adminLogin(session);
		    }
			
		}
		catch (OrdersDAOException e) {
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
	
	public void getAllOrdersByAdmin(Session session) throws OrdersDAOException {
	    try {
	        List<Orders> ordersList = session.createQuery("FROM Orders", Orders.class).getResultList();
	        if (ordersList.isEmpty()) {
	            System.out.println("No orders found.");
	        } else {
	            for (Orders order : ordersList) {
	                // Print or process each order as needed
	                System.out.print("Order ID: " + order.getOrderID() + " ORDER_ITEM: " + order.getOrderItem() + " QUANTITY: " 
	                + order.getQuantity() + " PRICE: " + order.getPrice() + " DATE: " + order.getDate() + " USERNAME: " 
	                + order.getUsername() + " PHONE: " + order.getPhone() + " USER_ID: " + order.getOrderID());
	                System.out.println();
	            }
	        }
	        Dashboard.adminLogin(session);
	    } catch (Exception e) {
	        throw new OrdersDAOException("Error retrieving orders.", "GET_ORDERS_ERROR");
	    }
	}
	
	public void getAllUsersByAdmin(Session session) throws OrdersDAOException {
	    try {
	        List<UserRegistration> usersList = session.createQuery("FROM UserRegistration", UserRegistration.class).getResultList();
	        if (usersList.isEmpty()) {
	            System.out.println("No orders found.");
	        } else {
	            for (UserRegistration user : usersList) {
	                // Print or process each order as needed
	                System.out.println("USER_ID: " + user.getId() + " USERNAME: " + user.getUsername() + " EMAIL: " + user.getEmail()
	                 + " PHONE: " + user.getPhone());
	                System.out.println();
	            }
	        }
	        Dashboard.adminLogin(session);
	    } catch (Exception e) {
	        throw new OrdersDAOException("Error retrieving users.", "GET_USERS_ERROR");
	    }
	}
	
	
	public void GetOrdersByNameByAdmin(Orders ord, Session session) throws OrdersDAOException {
		try {
			List<Orders> ordersList = session.createQuery("FROM Orders WHERE Username = :username", Orders.class)
					.setParameter("username", ord.getUsername())
					.getResultList();
	        if (ordersList.isEmpty()) {
	            System.out.println("No orders found.");
	        } else {
	            for (Orders order : ordersList) {
	                // Print or process each order as needed
	                System.out.print("Order ID: " + order.getOrderID() + " ORDER_ITEM: " + order.getOrderItem() + " QUANTITY: " 
	                + order.getQuantity() + " PRICE: " + order.getPrice() + " DATE: " + order.getDate() + " USERNAME: " 
	                + order.getUsername() + " PHONE: " + order.getPhone() + " USER_ID: " + order.getOrderID());
	                System.out.println();
	            }
	        }
	        Dashboard.adminLogin(session);
	    } catch (Exception e) {
	        throw new OrdersDAOException("Error retrieving orders.", "GET_ORDERS_ERROR");
	    }
	}
}
