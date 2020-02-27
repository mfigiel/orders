package com.testing.services;

import com.testing.api.mapping.OrderApiOrderMapper;
import com.testing.api.resource.OrderApi;
import com.testing.repository.OrderRepository;
import com.testing.repository.entity.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        order.setId(1);
        order.setClientId(2);

        Mockito.when(orderRepository.findById((long) 1))
                .thenReturn(Optional.of(order));
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        OrderApi found = orderService.getOrder(1);

        assertThat(found.getClientId())
                .isEqualTo(2);
        assertThat(found.getId())
                .isEqualTo(1);
    }
}
