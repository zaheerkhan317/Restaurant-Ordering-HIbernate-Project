package com.Main;

import java.util.Scanner;

import org.hibernate.Session;

import com.Orders.Orders;
import com.Orders.OrdersDAO;
import com.Orders.OrdersDAOException;
import com.UserLogin.UserLogin;


public class MainMenu {
	
	public static void UserLogin(Session session) {
		System.out.println("");
        System.out.println("1. Order an item");
        System.out.println("2. View my orders");
        System.out.println("3. Cancel my order");
        System.out.println("0. Logout");
        System.out.println("");
        
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.println("");
        switch (option) {
	        case 1:
	            mainMenu(session);
	            break;
	        case 2:
	            viewMyOrders(session);
	            break;
	        case 3:
	            cancelOrder(session, sc);
	            break;
	        case 4:
	            displayBeverages(session);
	            break;
	        case 0:
	            System.out.println("Logging out........");
	            System.out.println("Logged Out Successfully!!!!");
	            System.out.println("");
        		System.out.println("===========================");
        		System.out.println("Thank You. Visit Again!!!!!");
        		System.out.println("===========================");
        		System.out.println("");
        		System.exit(0); // Exit the program
	            break;
	        default:
	            System.out.println("Invalid option. Please select a valid option.");
	            mainMenu(session); // Display main menu again for invalid option
        }
	}
	
	public static void mainMenu(Session session) {
		
		System.out.println("Select an option:");
        System.out.println("");
        System.out.println("1. Starters");
        System.out.println("2. Biryanis");
        System.out.println("3. Desserts");
        System.out.println("4. Beverages");
        System.out.println("0. Logout");
        System.out.println("");
        
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.println("");
        switch (option) {
	        case 1:
	            displayStarters(session);
	            break;
	        case 2:
	            displayBiryanis(session);
	            break;
	        case 3:
	            displayDesserts(session);
	            break;
	        case 4:
	            displayBeverages(session);
	            break;
	        case 0:
	            System.out.println("Logging out........");
	            System.out.println("Logged Out Successfully!!!!");
	            System.out.println("");
        		System.out.println("===========================");
        		System.out.println("Thank You. Visit Again!!!!!");
        		System.out.println("===========================");
        		System.out.println("");
        		System.exit(0);
	            
	            System.exit(0); // Exit the program
	            break;
	        default:
	            System.out.println("Invalid option. Please select a valid option.");
	            mainMenu(session); // Display main menu again for invalid option
        }
	}
	
	
	 public static void displayStarters(Session session) {
	        System.out.println("Starters menu:");
	        System.out.println("");
	        System.out.println("1. Chicken Lollipop \t\t Rs.340");
	        System.out.println("2. Chicken Wings \t\t Rs.300");
	        System.out.println("3. Chicken 65 \t\t\t Rs.250");
	        System.out.println("4. Chicken Kabab \t\t Rs.360");
	        System.out.println("0. Back to main menu");
	        System.out.println("");
	        
	        Scanner sc = new Scanner(System.in);
	        int option = sc.nextInt();
	        System.out.println("");
	        sc.nextLine(); // Consume newline
	        
	        switch (option) {
		        case 0:
		            mainMenu(session); // Return to main menu
		            break;
		        case 1:
		        case 2:
		        case 3:
		        case 4:
		            String st = (option == 1) ? "Chicken Lollipop" :
		                        (option == 2) ? "Chicken Wings" :
		                        (option == 3) ? "Chicken 65" : "Chicken Kabab";
		            int price = (option == 1) ? 340 :
		                        (option == 2) ? 300 :
		                        (option == 3) ? 250 : 360;
		            System.out.print("Enter Quantity : ");
		            int quantity = sc.nextInt();
		            System.out.println("");
		            sc.nextLine();
		            Orders order = new Orders(price, st, quantity);
		            OrdersDAO od = new OrdersDAO(session);
		            try {
						od.insertOrder(order, UserLogin.getEmail());
					} catch (OrdersDAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            break;
		        default:
		            System.out.println("Invalid option. Please select a valid option.");
		            displayStarters(session); // Display starters menu again for invalid option
	        }
	        sc.close();
	    }
	
	
	public static void displayBiryanis(Session session) {
        System.out.println("Biryanis menu:");
        System.out.println("1. Chicken Biryani \t\t Rs.280");
        System.out.println("2. Vegetable Biryani \t\t Rs.180");
        System.out.println("3. Mutton Biryani \t\t Rs.380");
        System.out.println("4. Fish Biryani \t\t Rs.300");
        System.out.println("5. Egg Biryani \t\t\t Rs.250");
        System.out.println("0. Back to main menu");

        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (option) {
            case 0:
                mainMenu(session); // Return to main menu
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                String biryaniName = (option == 1) ? "Chicken Biryani" :
                                      (option == 2) ? "Vegetable Biryani" :
                                      (option == 3) ? "Mutton Biryani" :
                                      (option == 4) ? "Fish Biryani" : "Egg Biryani";
                int price = (option == 1) ? 280 :
                    (option == 2) ? 180 :
                    (option == 3) ? 380 : 
                    (option == 4) ? 300 : 250;
                
                System.out.print("Enter Quantity : ");
	            int quantity = sc.nextInt();
	            sc.nextLine();
	            Orders order = new Orders(price, biryaniName, quantity);
	            OrdersDAO od = new OrdersDAO(session);
				try {
					od.insertOrder(order, UserLogin.getEmail());
				} catch (OrdersDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            default:
                System.out.println("Invalid option. Please select a valid option.");
                displayBiryanis(session); 
        }
        sc.close();
    }
	
	
	public static void displayDesserts(Session session) {
        // Display desserts menu
        System.out.println("Desserts menu:");
        System.out.println("1. Chocolate Cake \t\t Rs.150");
        System.out.println("2. Cheesecake \t\t\t Rs.180");
        System.out.println("3. Tiramisu \t\t\t Rs.200");
        System.out.println("4. Strawberry Shortcake \t Rs.160");
        System.out.println("5. Apple Pie \t\t\t Rs.170");
        // Add more desserts as needed
        System.out.println("0. Back to main menu");
        System.out.println("");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (option) {
            case 0:
                mainMenu(session); // Return to main menu
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                String dessertName = (option == 1) ? "Chocolate Cake" :
                                     (option == 2) ? "Cheesecake" :
                                     (option == 3) ? "Tiramisu" :
                                     (option == 4) ? "Strawberry Shortcake" : "Apple Pie";
                int price = (option == 1) ? 150 :
                                   (option == 2) ? 180 :
                                   (option == 3) ? 200 :
                                   (option == 4) ? 160 : 170;
                
                System.out.print("Enter Quantity : ");
	            int quantity = sc.nextInt();
	            sc.nextLine();
	            Orders order = new Orders(price, dessertName, quantity);
	            OrdersDAO od = new OrdersDAO(session);
				try {
					od.insertOrder(order, UserLogin.getEmail());
				} catch (OrdersDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            default:
                System.out.println("Invalid option. Please select a valid option.");
                displayDesserts(session);
        }
        sc.close();
    }
	
	
	public static void displayBeverages(Session session) {
        // Display beverages menu
        System.out.println("Beverages menu:");
        System.out.println("1. Water \t\t Rs.20");
        System.out.println("2. Soft Drinks \t\t Rs.30");
        System.out.println("3. Badam Milk \t\t Rs.40");
        System.out.println("4. Coffee \t\t Rs.50");
        System.out.println("5. Lemonade \t\t Rs.35");
        // Add more beverages as needed
        System.out.println("0. Back to main menu");
        
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (option) {
            case 0:
                mainMenu(session); // Return to main menu
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                String beverageName = (option == 1) ? "Water" :
                                       (option == 2) ? "Soft Drinks" :
                                       (option == 3) ? "Badam Milk" :
                                       (option == 4) ? "Coffee" : "Lemonade";
                int price = (option == 1) ? 20 :
                                    (option == 2) ? 30 :
                                    (option == 3) ? 40 :
                                    (option == 4) ? 50 : 35;
                System.out.print("Enter Quantity : ");
	            int quantity = sc.nextInt();
	            sc.nextLine();
	            Orders order = new Orders(price, beverageName, quantity);
	            OrdersDAO od = new OrdersDAO(session);
				try {
					od.insertOrder(order, UserLogin.getEmail());
				} catch (OrdersDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            default:
                System.out.println("Invalid option. Please select a valid option.");
                displayBeverages(session); 
        }
        sc.close();
    }
	
	
	
	public static void confirmation(Session session) {
	    System.out.println("");
	    System.out.println("Would you like to order anything else?");
	    System.out.println("Yes/No");
	    System.out.println("");
	    
	    Scanner sc = new Scanner(System.in);
	    String confirm = sc.nextLine();
	    if (confirm.equalsIgnoreCase("Yes") || confirm.equalsIgnoreCase("Y")) {
	        mainMenu(session);
	    }
	    else if(confirm.equalsIgnoreCase("No") || confirm.equalsIgnoreCase("N")){
	    	System.out.println("");
			System.out.println("==========================");
			System.out.println("Order placed successfully!");
			System.out.println("==========================");
			System.out.println("");
	    }
	    else {
	    	System.out.println("");
			System.out.println("=======================================");
			System.out.println("Invalid option/Something went wrong!!!!");
			System.out.println("=======================================");
			System.out.println("");
	    }

	}

	
	public static void viewMyOrders(Session session) {
		OrdersDAO.viewOrders(session, UserLogin.getEmail());
	}
	
	
	public static void cancelOrder(Session session, Scanner sc) {
		System.out.print("Enter the Order ID : ");
		int id = sc.nextInt();
		OrdersDAO.cancelOrder(session, id);
	}
	
	
}
