package com.orders.services;

import com.orders.api.mapping.OrderApiOrderMapper;
import com.orders.api.resource.OrderApi;
import com.orders.repository.OrderRepository;
import com.orders.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public final OrderApiOrderMapper orderApiOrderMapper;

    public List<OrderApi> getOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(el -> orderApiOrderMapper.orderDtoToOrderApi(el))
                .collect(Collectors.toList());
    }

    public OrderApi addOrder(OrderApi order) {
        Order orderToSave = orderApiOrderMapper.orderApiToOrderDto(order);
        orderToSave = orderRepository.save(orderToSave);
        return orderApiOrderMapper.orderDtoToOrderApi(orderToSave);
    }

    public OrderApi getOrderApi(long id) {
        return orderApiOrderMapper.orderDtoToOrderApi(getOrder(id));
    }

    private Order getOrder(long id) {
        return Optional.ofNullable(orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("order id: " + id))).get();
    }

    public String getOrderState(long id) {
        try {
            return getOrder(id).getState();
        } catch (EntityNotFoundException e) {
            return "order not found";
        }
    }


}
