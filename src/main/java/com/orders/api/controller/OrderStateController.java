package com.orders.api.controller;

import com.orders.api.resource.UpdateStateOrderApi;
import com.orders.services.OrderService;
import com.orders.services.OrderStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderStateController {

    private final OrderStateService orderStateService;
    private final OrderService orderService;

    @PostMapping("/updateStateOrder")
    public void updateOrderState(@RequestBody UpdateStateOrderApi updateOrderStateApi) {
        orderStateService.updateState(updateOrderStateApi);
    }

    @GetMapping(value = "orderState/order/{id}")
    public String getOrderState(@PathVariable("id") Long id) {
        return orderService.getOrderState(id);
    }
}
