package com.API.Utilities;

import java.io.IOException;
import java.util.ArrayList;

import POJO_Classes.CreateOrderRequest_OrderDetails;
import POJO_Classes.CreateOrderRequest_Orders;
import POJO_Classes.LoginRequest;

/**
 * @author Shivakumar
 *
 */

public class TestDataBuild extends Utils {

	public LoginRequest loginRequestPayload() throws IOException {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail(getGlobalValues("userName"));
		loginRequest.setUserPassword(getGlobalValues("password"));
		return loginRequest;
	}

	public CreateOrderRequest_Orders createOrderPayload(String productID) throws IOException {
		CreateOrderRequest_Orders orders = new CreateOrderRequest_Orders();
		CreateOrderRequest_OrderDetails orderDetails = new CreateOrderRequest_OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productID);
		ArrayList<CreateOrderRequest_OrderDetails> orderDetailsList = new ArrayList<CreateOrderRequest_OrderDetails>();
		orderDetailsList.add(orderDetails);
		orders.setOrders(orderDetailsList);
		return orders;
	}

}
