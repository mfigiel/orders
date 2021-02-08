package com.orders.services;

import com.orders.api.resource.OrderApi;
import com.orders.repository.OrderRepository;
import com.orders.repository.entity.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class OrderServiceClass {

    @TestConfiguration
    static class OrderServiceImplTestContextConfiguration {

        @Bean
        public OrderService orderService() {
            return new OrderService();
        }
    }

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        Order order = new Order();
        order.setId((long) 1);
        order.setClientId(2);

        Mockito.when(orderRepository.findById((long) 1))
                .thenReturn(Optional.of(order));

        List<Order> orderList = new ArrayList<>();

        Order orderSecond = new Order();
        orderSecond.setId((long) 2);
        orderList.add(orderSecond);
        Mockito.when(orderRepository.findAll())
                .thenReturn(orderList);
    }

    @Test
    public void getOneOrder() {
        OrderApi found = orderService.getOrderApi(1);

        assertThat(found.getClientId())
                .isEqualTo(2);
        assertThat(found.getId())
                .isEqualTo(1);
    }

    @Test
    public void getAllOrders() {
        List<OrderApi> found = orderService.getOrders();

        assertEquals(found.size(), 2);
        assertEquals(found.get(0).getId(), 1);
        assertEquals(found.get(1).getId(), 2);
    }
}
