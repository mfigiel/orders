package com.testing.api.controller;

import com.testing.api.resource.OrderApi;
import com.testing.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public List<OrderApi> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/orders")
    public OrderApi addOrder(@RequestBody OrderApi order) {
        return orderService.addOrder(order);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public OrderApi getOrderInformation(@PathVariable("id") long id) {
        return orderService.getOrder(id);
    }

}
