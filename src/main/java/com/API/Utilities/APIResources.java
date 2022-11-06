package com.API.Utilities;

/**
 * @author Shivakumar
 *
 */

public enum APIResources {

	//enum is special class in java which has collection of constants and methods
	

	LoginAPI("/api/ecom/auth/login"),
	AddProductAPI("/api/ecom/product/add-product"),
	CreateOrderAPI("/api/ecom/order/create-order"),
	ViewOrderDetailsAPI("/api/ecom/order/get-orders-details"),
	DeleteProductAPI("/api/ecom/product/delete-product"),
	AddBookAPI("/Library/Addbook.php"),
	TokenServiceAPI("/digx-auth/v1/token"),
	TokenInfoAPI("/digx-auth/v1/token/info"),
	UserExistsAPI("/digx/v1/users");

	
	private String resource;
	APIResources(String resource){
		this.resource=resource;
	}
	public String getResource() {
		return resource;
	}
}
