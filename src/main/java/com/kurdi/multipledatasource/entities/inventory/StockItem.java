package com.kurdi.multipledatasource.entities.inventory;

import lombok.*;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="stock_items")
public class StockItem {
    @Id
    private String id = new GUIDGenerator().toString();
    private String name;
}
