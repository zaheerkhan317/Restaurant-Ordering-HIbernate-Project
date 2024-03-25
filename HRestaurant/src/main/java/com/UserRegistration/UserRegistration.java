package com.UserRegistration;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.Orders.Orders;

@Entity
@Table(name = "users")

public class UserRegistration {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int id;
    
    @NotNull
    @Column(name = "USERNAME", columnDefinition = "VARCHAR(50) NOT NULL")
    private String Username;
    
    @NotNull
    @Column(name = "EMAIL", columnDefinition = "VARCHAR(50) NOT NULL")
    private String Email;
    
    @NotNull
    @Column(name = "PHONE", columnDefinition = "VARCHAR(10) NOT NULL")
    private String Phone;
    
    @NotNull
    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(50) NOT NULL")
    private String Password;
    
    @OneToMany(mappedBy = "UReg", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Orders> Orders;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	public List<Orders> getOrders() {
        return Orders;
    }

    public void setOrders(List<Orders> Orders) {
        this.Orders = Orders;
    }
	
	public UserRegistration(String Username, String Email, String Phone, String Password) {
		super();
		this.Username = Username;
		this.Email = Email;
		this.Phone = Phone;
		this.Password = Password;
	}
	
	
	
	
	public UserRegistration(String Phone) {
		this.Phone = Phone;
	}
	
	public UserRegistration() {
		
	}
	
	@Override
	public String toString() {
		return "UserRegistration [id=" + id + ", Username=" + Username + ", Email=" + Email + ", Phone=" + Phone
				+ ", Password=" + Password + "]";
	}
	
	
}
