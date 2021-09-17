package com.orders.api.controller;

import com.orders.api.resource.UpdateStateOrderApi;
import com.orders.services.OrderService;
import com.orders.services.OrderStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getOrderState(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrderState(id));
    }
}
