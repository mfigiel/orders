package com.testing.services;

import com.testing.api.mapping.OrderApiOrderMapperImpl;
import com.testing.api.resource.OrderApi;
import com.testing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    OrderApiOrderMapperImpl orderApiOrderMapper = new OrderApiOrderMapperImpl();

    public List<OrderApi> getOrders() {
        return (List) orderRepository.findAll();
    }

    public void addOrder(OrderApi order) {
        orderRepository.save(orderApiOrderMapper.orderApiToOrderDto(order));
    }

    public OrderApi getOrder(long id) {
        Optional<OrderApi> order = Optional.ofNullable(orderApiOrderMapper.orderDtoToOrderApi(orderRepository.findById(id)));
        return order.get();
    }
}
