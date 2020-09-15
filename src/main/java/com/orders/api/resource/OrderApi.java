package com.orders.api.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderApi {

    private Long id;
    private List<String> products;
    private Integer clientId;
    private Date orderDate;
}
