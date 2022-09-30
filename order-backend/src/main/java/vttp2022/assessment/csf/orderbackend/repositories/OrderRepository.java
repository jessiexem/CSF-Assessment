package vttp2022.assessment.csf.orderbackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

import static vttp2022.assessment.csf.orderbackend.repositories.Queries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class OrderRepository {
    
    @Autowired
    private JdbcTemplate template;

    private final Logger logger = Logger.getLogger(OrderRepository.class.getName());

    public boolean addOrder(Order order) {
        List<String> toppingList =  order.getToppings();
        String top = String.join(",",toppingList);
        System.out.println("in repo addOrder :" + top);

        int added = template.update(SQL_INSERT_ORDER, 
        order.getName(),
        order.getEmail(),
        order.getSize(),
        order.isThickCrust(),
        order.getSauce(),
        top,
        order.getComments());

        return added>0;
    }

    public Optional<List<Order>> getAllOrdersByEmail (String email) {
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
        if (!rs.isBeforeFirst()) {
            logger.warning(">>>> OrderRepository: getAllOrdersByEmail: no data found");
            return Optional.empty();
        }
        else {
            List<Order> orderList = new ArrayList<>();
            while (rs.next()) {
                orderList.add(Order.convertSqlRowSetTOrder(rs));
            }
            return Optional.of(orderList);
        }
    }
}
