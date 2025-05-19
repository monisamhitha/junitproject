package com.example.service;

import com.example.entity.Order;
import com.example.util.DBConnectionUtil;
import java.sql.*;
import java.util.*;

public class OrderServiceImpl implements OrderService {

    @Override
    public String addOrder(Order order) {
        String query = "INSERT INTO orders (customerName, orderDate, totalAmount, orderStatus) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getOrderDate());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getOrderStatus());

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Order added successfully." : "Failed to add order.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE orderId = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            int rows = stmt.executeUpdate();
            return rows > 0 ? "Order deleted successfully." : "Order not found.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Order order = new Order(
                    rs.getInt("orderId"),
                    rs.getString("customerName"),
                    rs.getString("orderDate"),
                    rs.getDouble("totalAmount"),
                    rs.getString("orderStatus")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return orders;
    }
}
