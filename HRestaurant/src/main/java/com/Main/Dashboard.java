package com.Main;

import java.util.Scanner;

import org.hibernate.Session;

import com.Admin.Admin;
import com.Admin.AdminDAO;
import com.Admin.AdminDAOException;
import com.Orders.Orders;
import com.Orders.OrdersDAO;
import com.Orders.OrdersDAOException;
import com.UserLogin.UserLogin;
import com.UserLogin.UserLoginDAO;
import com.UserLogin.UserLoginDAOException;
import com.UserRegistration.UserRegistration;
import com.UserRegistration.UserRegistrationDAO;
import com.UserRegistration.UserRegistrationDAOException;




public class Dashboard {
	
	public static void insertUser(Scanner sc, Session session) {
		System.out.print("Enter your name: ");
		String username = sc.nextLine();
		
		System.out.print("Enter your email: ");
		String email = sc.nextLine();
		
		System.out.print("Enter your phone number: ");
		String phone = sc.nextLine();
		
		System.out.print("Enter your password: ");
		String password = sc.nextLine();
		
		
		UserRegistration UReg = new UserRegistration(username,email,phone,password);
		
		UserRegistrationDAO URegDao = new UserRegistrationDAO(session);
		
		try {
			URegDao.insertUser(UReg);
			
			
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			System.out.println("");
        	System.out.println("=====================================================");
        	System.out.println("DUPLICATE ERROR: Email/Phone number already exists!!!");
        	System.out.println("=====================================================");
        	System.out.println("");
        	
        	Main.start(session);
		}
	}
	
	public static void validateLogin(Scanner sc, Session session) {
		
		System.out.print("Enter your emailID: ");
		String email = sc.nextLine();
		
		System.out.print("Enter your password: ");
		String password = sc.nextLine();
		
		UserLogin ULog = new UserLogin(email, password);
		UserLoginDAO ULDao = new UserLoginDAO(session);
		try {
			ULDao.validateLogin(ULog);
		} catch (UserLoginDAOException e) {
            // Handle the exception here
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("Error message: " + e.getMessage());
        }
				
	}
	
	
	public static void validateAdminLogin(Scanner sc, Session session) {
		Admin ad = new Admin(1,"admin","admin@gmail.com","admin123");
		AdminDAO adDAO = new AdminDAO(session);
		
		try {
			adDAO.insertAdmin(ad, session);
			
			System.out.print("Enter admin mailID : ");
			String email = sc.nextLine();
			System.out.print("Enter admin password : ");
			String password = sc.nextLine();
			
			adDAO.adminValidateLogin(ad, email, password);
		} catch (AdminDAOException e) {
			System.out.println("Error code: " + e.getErrorCode());
            System.out.println("Error message: " + e.getMessage());
		}
		
	}
	
	
	public static void insertUserByAdmin(Scanner sc, Session session) {
		
		System.out.print("Enter your name: ");
		String username = sc.nextLine();
		
		System.out.print("Enter your email: ");
		String email = sc.nextLine();
		
		System.out.print("Enter your phone number: ");
		String phone = sc.nextLine();
		
		System.out.print("Enter your password: ");
		String password = sc.nextLine();
		
		UserRegistration UReg = new UserRegistration(username,email,phone,password);
		
		UserRegistrationDAO URegDao = new UserRegistrationDAO(session);
		
		try {
			URegDao.insertUserByAdmin(UReg);
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			System.out.println("");
        	System.out.println("=====================================================");
        	System.out.println("DUPLICATE ERROR: Email/Phone number already exists!!!");
        	System.out.println("=====================================================");
        	System.out.println("");
        	
        	adminLogin(session);
		}
		
	}
	
	public static void deleteUserByAdmin(Scanner sc, Session session) {
		System.out.println("Enter the user phone number : ");
		String phone = sc.nextLine();
		
		UserRegistration UReg = new UserRegistration(phone);
		UserRegistrationDAO UDao = new UserRegistrationDAO(session);
		try {
			UDao.DeleteUserByAdmin(UReg);
		} catch (UserRegistrationDAOException e) {
			e.printStackTrace();
			adminLogin(session);
		}
	}
	
	public static void UpdateUserByAdmin(Scanner sc, Session session) {
		
		System.out.print("Enter the Email of which user you want to change : ");
		String email = sc.nextLine();
		
		System.out.print("Enter the Username : ");
        String name = sc.nextLine();

        System.out.print("Enter the User Number : ");
        String phone = sc.nextLine();

        System.out.print("Enter the User Password : ");
        String password = sc.nextLine();
        
        System.out.println("");
        UserRegistration UReg = new UserRegistration(name, email, phone, password);
		UserRegistrationDAO UDao = new UserRegistrationDAO(session);
        try {
			UDao.UpdateUserByAdmin(UReg);
		} catch (UserRegistrationDAOException e) {
			e.printStackTrace();
			adminLogin(session);
		}
		
	}
	
	public static void ModifyOrderByAdmin(Scanner sc, Session session) {
		
		System.out.print("Enter your ORDER_ID to modify : ");
		int OrderID = sc.nextInt();
		sc.nextLine();
		
//		  System.out.print("Enter your ORDER_ITEM : ");
//        String ordered_item = sc.nextLine();
//
//        System.out.print("Enter your QUANTITY : ");
//        int quantity = sc.nextInt();
//        sc.nextLine();
//        
//        System.out.print("Enter your PRICE : ");
//        int price = sc.nextInt();
//        sc.nextLine();
//        
//        System.out.println("");
//        
//        Orders ord = new Orders(OrderID, quantity, price, ordered_item);
//		  OrdersDAO ordDao = new OrdersDAO(session);
		  Orders ord = new Orders(OrderID);
		  OrdersDAO ordDao = new OrdersDAO(session);
		  try {
			ordDao.ModifyOrderByAdmin(ord, session);
		} catch (OrdersDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void CancelOrderByAdmin(Scanner sc, Session session) {
		
		System.out.print("Enter your ORDER_ID to Cancel the Order : ");
		int OrderID = sc.nextInt();
		sc.nextLine();
		
		Orders ord = new Orders(OrderID);
		  OrdersDAO ordDao = new OrdersDAO(session);
		  try {
			ordDao.CancelOrderByAdmin(ord, session);
		} catch (OrdersDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void GetAllOrders(Scanner sc, Session session) {
		OrdersDAO ordDao = new OrdersDAO(session);
		try {
			ordDao.getAllOrdersByAdmin(session);
		} catch (OrdersDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void GetAllUsers(Scanner sc, Session session) {
		OrdersDAO ordDao = new OrdersDAO(session);
		try {
			ordDao.getAllUsersByAdmin(session);
		} catch (OrdersDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void GetOrdersByName(Scanner sc, Session session) {
		System.out.print("Enter the username to get orders: ");
		String name = sc.nextLine();
		Orders ord = new Orders(name);
		OrdersDAO ordDao = new OrdersDAO(session);
		try {
			ordDao.GetOrdersByNameByAdmin(ord, session);
		} catch (OrdersDAOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void adminLogin(Session session) {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Select an option:");
			System.out.println("");
		    System.out.println("1. Add the user");
		    System.out.println("2. Delete the user");
		    System.out.println("3. Update user details");
		    System.out.println("4. Modify Order");
		    System.out.println("5. Cancel Order");
		    System.out.println("6. Get all Orders");
		    System.out.println("7. Get all Users");
		    System.out.println("8. Get Orders by name");
		    System.out.println("0. Logout");
		    System.out.println("");
			System.out.println("Select an option: ");
			
			int option = sc.nextInt();
			sc.nextLine();
			switch(option) {
				case 1:
					insertUserByAdmin(sc, session);
					break;
				case 2:
		            deleteUserByAdmin(sc, session);
		            break;
		        case 3:
		            UpdateUserByAdmin(sc, session);
		            break;
		        case 4:
		            ModifyOrderByAdmin(sc, session);
		            break;
		        case 5:
		        	CancelOrderByAdmin(sc, session);
		        	break;
		        case 6:
		        	GetAllOrders(sc, session);
		        	break;
		        	
		        case 7:
		        	GetAllUsers(sc, session);
		        	break;
		        case 8:
		        	GetOrdersByName(sc, session);
		        	break;						
				case 0:
					System.out.println("Logging out........");
		            System.out.println("Logged Out Successfully!!!!");
		            System.out.println("");
	            	Main.start(session);
	                System.exit(0); // Exit the program
	                break;
	            default:
	            	System.out.println("");
	            	System.out.println("===============================================");
	                System.out.println("Invalid option. Please select either 1, 2 or 0.");
	                System.out.println("===============================================");
	                System.out.println("");
			}
	}
	
}
