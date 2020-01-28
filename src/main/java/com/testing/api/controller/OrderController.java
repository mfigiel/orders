package com.testing.api.controller;

import com.testing.api.mapping.OrderApiOrderMapperImpl;
import com.testing.api.resource.OrderApi;
import com.testing.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class OrderController {

    private final OrderRepository orderRepository;
    OrderApiOrderMapperImpl orderApiOrderMapper = new OrderApiOrderMapperImpl();
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public List<OrderApi> getOrders() {
        return (List) orderRepository.findAll();
    }

    @PostMapping("/orders")
    void addOrder(@RequestBody OrderApi order) {
        orderRepository.save(orderApiOrderMapper.orderApiToOrderDto(order));
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public OrderApi getOrderInformation(@PathVariable("id") long id) {
        Optional<OrderApi> order = Optional.ofNullable(orderApiOrderMapper.orderDtoToOrderApi(orderRepository.findById(id)));
        return order.get();
    }

}
