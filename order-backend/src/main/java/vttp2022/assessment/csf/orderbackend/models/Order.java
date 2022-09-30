package vttp2022.assessment.csf.orderbackend.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import vttp2022.assessment.csf.orderbackend.models.Request;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order convertSqlRowSetTOrder(SqlRowSet rs) {
		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setName(rs.getString("name"));
		order.setEmail(rs.getString("email"));
		order.setSize(rs.getInt("pizza_size"));
		order.setThickCrust(rs.getBoolean("thick_crust"));
		order.setSauce(rs.getString("sauce"));

		String toppings = rs.getString("toppings");
		List<String> toppingList = new ArrayList<String>(Arrays.asList(toppings.split(",")));
		order.setToppings(toppingList);

		order.setComments(rs.getString("comments"));

		return order;
	}

	public static Order convertRequestToOrder(Request req) {
		Order order = new Order();
		order.setName(req.getName());
		order.setEmail(req.getEmail());
		order.setSauce(req.getSauce());
		order.setSize(req.getSize());
		if (req.getBase().contains("thick")) {
			order.setThickCrust(true);
		} else {
			order.setThickCrust(false);
		}

		List<String> toppingList = new ArrayList<>();
		String [] toppings = req.getToppings();
		for (String topping : toppings) {
			toppingList.add(topping);
		}
		order.setToppings(toppingList);

		order.setComments(req.getComments());
		return order;
	}
}
