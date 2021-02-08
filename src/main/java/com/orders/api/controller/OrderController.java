package com.orders.api.controller;

import com.orders.api.resource.OrderApi;
import com.orders.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderApi> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/orders")
    public OrderApi addOrder(@RequestBody OrderApi order) {
        return orderService.addOrder(order);
    }

    @GetMapping(value = "/order/{id}")
    public OrderApi getOrderInformation(@PathVariable("id") Long id) {
        return orderService.getOrderApi(id);
    }

}
