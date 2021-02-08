package com.orders.repository.entity;

import com.orders.api.resource.OrderStates;
import com.orders.repository.converter.StringListConverter;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "products")
    @Convert(converter = StringListConverter.class)
    private List<String> products;

    private Integer clientId;

    private Date orderDate;

    private String state = String.valueOf(OrderStates.SUBMITTED);

    public void setState(OrderStates state) {
        this.state = String.valueOf(state);
    }
}
