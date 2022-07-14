package com.kurdi.multipledatasource.repositories.orders;

import com.kurdi.multipledatasource.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, String> {
}
