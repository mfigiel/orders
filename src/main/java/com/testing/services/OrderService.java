package com.testing.services;

import com.testing.api.mapping.OrderApiOrderMapperImpl;
import com.testing.api.resource.OrderApi;
import com.testing.repository.OrderRepository;
import com.testing.repository.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public OrderApi addOrder(OrderApi order) {
        Order orderToSave = orderApiOrderMapper.orderApiToOrderDto(order);
        orderToSave = orderRepository.save(orderToSave);
        return orderApiOrderMapper.orderDtoToOrderApi(orderToSave);
    }

    public OrderApi getOrder(long id) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order id: " + id)));

        return orderApiOrderMapper.orderDtoToOrderApi(order.get());
    }
}
