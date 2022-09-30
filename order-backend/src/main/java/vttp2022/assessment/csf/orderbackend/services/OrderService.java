package vttp2022.assessment.csf.orderbackend.services;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	private PricingService priceSvc;

	private final Logger logger = Logger.getLogger(OrderService.class.getName());

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) throws Exception {
		boolean isAdded = orderRepo.addOrder(order);
		if (!isAdded) {
			throw new Exception("Unable to create order");
		}
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order
		Optional<List<Order>> optOrder = orderRepo.getAllOrdersByEmail(email);
		List<OrderSummary> orderSummaries = new ArrayList<>();
		if (optOrder.isEmpty()) {
			logger.info("There are no orders for this email: "+ email);
			return orderSummaries; //list length should be 0

		} else {
			List<Order> orderList = optOrder.get();
			

			for (Order order: orderList) {
				Float totalAmount = 0f;
				
				totalAmount+= priceSvc.size(order.getSize()); //pizza size
				totalAmount+= priceSvc.sauce(order.getSauce()); //sauce
				
				if (order.isThickCrust()) { //thick or thin crust
					totalAmount+= priceSvc.thickCrust();
				} else {
					totalAmount+= priceSvc.thinCrust();
				}

				List<String> toppingList = order.getToppings(); //toppings
				for (String topping : toppingList) {
					totalAmount+= priceSvc.topping(topping);
				}

				OrderSummary summary = OrderSummary.calculateOrderSummary(order, totalAmount);
				orderSummaries.add(summary);
			}
			return orderSummaries;
		}
	}

	
}
