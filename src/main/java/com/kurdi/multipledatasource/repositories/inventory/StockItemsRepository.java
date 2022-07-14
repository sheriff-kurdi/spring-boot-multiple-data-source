package com.kurdi.multipledatasource.repositories.inventory;

import com.kurdi.multipledatasource.entities.inventory.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemsRepository extends JpaRepository<StockItem, String> {
}
