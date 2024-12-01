package com.restaurant.management.service;

import com.restaurant.management.enums.OrderMethod;
import com.restaurant.management.enums.OrderStatus;
import com.restaurant.management.model.Dish;
import com.restaurant.management.model.Order;
import com.restaurant.management.model.OrderItem;
import com.restaurant.management.repository.DishRepository;
import com.restaurant.management.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private PaymentService paymentService;

    /*
        {
      "customerId": "1",
      "orderDate": "2023-11-05T12:00:00",
      "totalAmount": 55.5,
      "orderMethod": "CASH",
      "orderItems": [
        {
          "dishDetails": { "dishId": 1 },
          "quantity": 2,
          "price": 99.0
        },
        {
          "dishDetails": { "dishId": 2 },
          "quantity": 1,
          "price": 22.0
        }
      ]
    }
    */
    @Transactional
    public String createOrder(Map<String, Object> orderData) {
        String customerId = (String) orderData.get("customerId");
        LocalDateTime orderDate = LocalDateTime.now();
        OrderMethod orderMethod = OrderMethod.valueOf((String) orderData.get("orderMethod"));

        double[] totalAmount = {0.0};

        String orderID = generateOrderID(customerId);

        List<Map<String, Object>> orderItemsData = (List<Map<String, Object>>) orderData.get("orderItems");
        List<OrderItem> orderItems = new ArrayList<>();

        orderItemsData.forEach(itemData -> {
            Map<String, Object> dishData = (Map<String, Object>) itemData.get("dishDetails");
            Long dishId = ((Number) dishData.get("dishId")).longValue();

            Dish dish = dishRepository.findById(dishId)
                    .orElseThrow(() -> new RuntimeException("Dish not found with id: " + dishId));

            Double price = dish.getPrice();
            Integer quantity = (Integer) itemData.get("quantity");
            System.out.println("price:: " + price + " quantity:: " + quantity);
            totalAmount[0] += price * quantity;

            OrderItem orderItem = OrderItem.builder()
                    .dish(dish)
                    .quantity(quantity)
                    .price(price)
                    .build();

            orderItems.add(orderItem);
        });

        Order order = Order.builder()
                .id(orderID)
                .orderMethod(orderMethod)
                .customerId(customerId)
                .orderDate(orderDate)
                .totalAmount(totalAmount[0])
                .orderItems(orderItems)
                .build();

        orderItems.forEach(item -> item.setOrder(order));

        orderRepository.save(order);
        return order.getId();
    }

    private String generateOrderID(String customerId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now().format(formatter);
        String randomNumber = String.format("%04d", new Random().nextInt(10000));
        return date + customerId + randomNumber;
    }

    public String payOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new RuntimeException("Order not found with id: " + orderId));
        return paymentService.payOrder(orderId, order.getTotalAmount(), "Thanh toan don hang " + order.getId());
    }

    public boolean checkOrderId(String orderId) {
        return orderRepository.existsById(orderId);
    }

    public boolean checkAmount(String orderId, Double amount) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(o -> o.getTotalAmount().equals(amount / 100.0)).orElse(false);
    }

    public boolean checkOrderPendingStatus(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(o -> o.getOrderStatus() == OrderStatus.PENDING).orElse(false); // Checks if status is pending
    }

    public boolean updateOrderStatus(String orderId, OrderStatus newStatus) {
        return orderRepository.findById(orderId).map(order -> {
            order.setOrderStatus(newStatus);
            orderRepository.save(order);
            return true;
        }).orElse(false);
    }

    public Optional<Order> findOrderByTableIdAndStatus(Long tableId) {
        return orderRepository.findFirstByDiningTableIdAndAndOrderStatusIn(tableId, Arrays.asList(OrderStatus.PAID, OrderStatus.UNPAID, OrderStatus.PENDING));
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
