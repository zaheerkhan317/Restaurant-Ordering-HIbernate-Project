package com.Orders;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.UserRegistration.UserRegistration;

@Entity
@Table(name = "orders")
public class Orders {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
	private int OrderID;
	
	@NotNull
    @Column(name = "ORDER_ITEM", columnDefinition = "VARCHAR(50) NOT NULL")
	private String st;
	
	@NotNull
    @Column(name = "PRICE", columnDefinition = "INT NOT NULL")
	private int price;
	
	@NotNull
    @Column(name = "QUANTITY", columnDefinition = "VARCHAR(50) NOT NULL")
    private int quantity;
	
	@NotNull
    @Column(name = "USERNAME", columnDefinition = "VARCHAR(50) NOT NULL")
	private String username;
	
	@NotNull
    @Column(name = "PHONE", columnDefinition = "VARCHAR(10) NOT NULL")
    private String Phone;
	
	@NotNull
    @Temporal(TemporalType.DATE) // Use TemporalType.DATE for date-only fields
    @Column(name = "DATE", columnDefinition = "DATE NOT NULL")
    private Date date;
	
	@ManyToOne
	@JoinColumn(name = "User_id")
	private UserRegistration UReg;
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPrice() {
    	return price;
    }
    
    public void setPrice(int price) {
    	this.price = price;
    }
    
	public String getOrderItem() {
		return st;
	}
	
	public void setOrderItem(String st) {
		this.st = st;
	}
	
	
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getOrderID() {
    	return OrderID;
    }
    
    public void setOrderID(int OrderID) {
    	this.OrderID = OrderID;
    }
    
    public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPhone() {
		return Phone;
	}
	
	public void setPhone(String phone) {
		this.Phone = phone;
	}
	
	public UserRegistration getUserId() {
		return UReg;
	}
	
	public void setUserId(UserRegistration UReg) {
		this.UReg = UReg;
	}
	
	public Orders(int price, String st,  int quantity) {
    	super();
    	this.price = price;
    	this.st = st;
    	this.quantity = quantity;
    }
	
	public Orders(int OrderID, int quantity, int price, String st) {
    	super();
    	this.quantity = quantity;
    	this.st = st;
    	this.price = price;
    	this.OrderID = OrderID;
    }
	
	public Orders(int OrderID) {
		this.OrderID = OrderID;
	}
	
	public Orders() {
		
	}
	
	public Orders(String username) {
		this.username = username;
	}

	
	@Override
	public String toString() {
		return "Orders [OrderID=" + OrderID + ", st=" + st + ", price=" + price + ", quantity=" + quantity
				+ ", username=" + username + ", Phone=" + Phone + ", date=" + date + ", UReg=" + UReg + "]";
	}

	
	
}
