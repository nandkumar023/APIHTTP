package POJO_Classes;

/**
 * @author Shivakumar
 *
 */

import java.util.List;

public class CreateOrderResponse {

	private List<String> orders;
	private List<String> productOrderId;
	private String message;

	public List<String> getOrders() {
		return orders;
	}
	public List<String> getProductOrderId() {
		return productOrderId;
	}
	public String getMessage() {
		return message;
	}

	
	
	
}
