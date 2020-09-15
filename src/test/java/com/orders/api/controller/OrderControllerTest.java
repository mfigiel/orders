package com.orders.api.controller;

import com.orders.api.resource.OrderApi;
import com.orders.repository.OrderRepository;
import com.orders.repository.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderControllerTest {

    private static final String HTTP_LOCALHOST = "http://localhost:";

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void getOrder_NoResults() throws Exception {
        mockMvc.perform(get("/order", 1)
                        .param("id", "1"))
                .andExpect(status().is4xxClientError());

        assertThat(this.testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/order/?id=1",
                String.class)).isNotNull();
    }

    @Test
    public void getOrder() {
        // act
        Order order = getTestOrder(1);

        orderRepository.save(order);
        OrderApi orderFromDb = testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/order/1", OrderApi.class);

        assertEquals(orderFromDb.getId(), 1);
        assertEquals(orderFromDb.getClientId(), 2);
        assertEquals(orderFromDb.getOrderDate(), null);
        assertEquals(orderFromDb.getProducts().size(), 1);
    }

    @Test
    public void getOrders() {
        // act
        orderRepository.save(getTestOrder(1));
        orderRepository.save(getTestOrder(2));

        ResponseEntity<List<OrderApi>> orderFromDb = testRestTemplate.exchange(HTTP_LOCALHOST + port + "/orders", HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderApi>>() {
        });

        // assert
        assertEquals(orderFromDb.getBody().size(), 2);
        OrderApi order = orderFromDb.getBody().get(0);
        assertEquals(order.getId(), 1);
        assertEquals(order.getClientId(), 2);
        assertEquals(order.getOrderDate(), null);
        assertEquals(order.getProducts().size(), 1);

        OrderApi secondOrder = orderFromDb.getBody().get(1);
        assertEquals(secondOrder.getId(), 2);
        assertEquals(secondOrder.getClientId(), 2);
        assertEquals(secondOrder.getOrderDate(), null);
        assertEquals(secondOrder.getProducts().size(), 1);
    }

    @Test
    public void addOrder() {
        // act
        OrderApi order = getTestOrderApi(1);
        testRestTemplate.postForObject(HTTP_LOCALHOST + port + "/orders", order, OrderApi.class);


        Optional<Order> orderFromDbOptional = orderRepository.findById(1L);
        Order orderFromDb = orderFromDbOptional.get();

        // assert
        assertEquals(orderFromDb.getId(), 1);
        assertEquals(orderFromDb.getClientId(), 2);
        assertEquals(orderFromDb.getOrderDate(), null);
        assertEquals(orderFromDb.getProducts().size(), 1);
    }

    private OrderApi getTestOrderApi(long id) {
        OrderApi order = new OrderApi();
        order.setId(id);
        List<String> productList = new ArrayList<>();
        productList.add("1");
        order.setProducts(productList);
        order.setClientId(2);
        order.setOrderDate(null);

        return order;
    }

    private Order getTestOrder(long id) {
        Order order = new Order();
        order.setId(id);
        List<String> productList = new ArrayList<>();
        productList.add("1");
        order.setProducts(productList);
        order.setClientId(2);
        order.setOrderDate(null);

        return order;
    }
}
