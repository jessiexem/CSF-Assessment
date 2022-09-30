package vttp2022.assessment.csf.orderbackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.Request;
import vttp2022.assessment.csf.orderbackend.services.OrderService;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping (path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    OrderService orderSvc;

    private Logger logger = Logger.getLogger(OrderRestController.class.getName());

    @PostMapping(path="/order")
    public ResponseEntity<String> createOrder(@RequestBody Request req) {
        logger.info("in addContact controller");
        System.out.println(req.getToppings()[0]);

        Order order = Order.convertRequestToOrder(req);

        try {
            orderSvc.createOrder(order);

            //managed to create order
            JsonObject resp = Json.createObjectBuilder()
            .add("message", String.format("Order for %s was sucessfully created", order.getName()))
            .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(resp.toString());
    
        } catch (Exception ex) {

            //unable to createOrder
            JsonObject resp = Json.createObjectBuilder()
            .add("message", String.format("There is an error in creating order for %s. Please try again.", order.getName()))
            .build();

            return ResponseEntity.status(500).body(resp.toString());
        }
        
    }

    @GetMapping(path="/order/{email}/all")
    public ResponseEntity<List<OrderSummary>> getAllOrders(@PathVariable (name="email", required = true) String email) {
        logger.info("in getAllOrders controller"+ email);

        List<OrderSummary> list = orderSvc.getOrdersByEmail(email);

        if (list.size()==0 || list == null) {
            // JsonObject resp = Json.createObjectBuilder()
            // .add("message", String.format("There is no orders for this email %s.", email))
            // .build();

            return ResponseEntity.status(500).body(null);
        } else {

            return ResponseEntity.status(HttpStatus.OK).body(list);
        }

    }
}
