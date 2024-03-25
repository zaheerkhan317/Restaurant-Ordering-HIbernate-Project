package com.Main;


import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
	public static void Logins() {
	    System.out.println("Select an option:");
	    System.out.println("");
	    System.out.println("1. Register");
	    System.out.println("2. User Login");
	    System.out.println("3. Admin Login");
	    System.out.println("0. Exit");
	    System.out.println("");
	}
	
	public static void start(Session session) {
		Scanner sc = new Scanner(System.in);
		Logins();
		int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.println("");
        switch (option) {
            case 1:
                Dashboard.insertUser(sc, session);
                break;
                
            case 2:
            	Dashboard.validateLogin(sc, session);
                break;
                
            case 3:
            	Dashboard.validateAdminLogin(sc,session);
            	break;
            case 0:
            	exit();
            	String a = sc.nextLine();
            	if(a.equalsIgnoreCase("Y")) {
            		System.out.println("");
            		System.out.println("===========================");
            		System.out.println("Thank You. Visit Again!!!!!");
            		System.out.println("===========================");
            		System.out.println("");
            		System.exit(0); // Exit the program
            	}
            	else {
            		start(session);
            	}
                break;
            default:
            	System.out.println("");
            	System.out.println("===============================================");
                System.out.println("Invalid option. Please select either 1, 2 or 0.");
                System.out.println("===============================================");
                System.out.println("");
        }

        sc.close();

	
	}
	
	public static void exit() {
		System.out.println("");
    	System.out.println("========================");
        System.out.println("Do you want to exit? Y/N");
        System.out.println("========================");
        System.out.println("");
	}
	
	public static void main(String args[]) {
		
		SessionFactory sf = new Configuration().configure("Hibernate.cfg.xml").buildSessionFactory();
        Session session = sf.openSession();
        
		System.out.println("\n");
        System.out.println("=======================================");
        System.out.println("||         WELCOME TO OUR            ||");
        System.out.println("||           RESTAURANT!!!           ||");
        System.out.println("=======================================");
        System.out.println("\n");
        
        start(session);
	}

}

