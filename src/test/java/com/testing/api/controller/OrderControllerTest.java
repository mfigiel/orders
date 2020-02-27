package com.testing.api.controller;

import com.testing.api.resource.OrderApi;
import com.testing.repository.OrderRepository;
import com.testing.repository.entity.Order;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void productGet() throws Exception {
        mockMvc
                .perform(get("/order", 1)
                        .param("id", "1"))
                .andExpect(status().is4xxClientError());

        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/order/?id=1",
                String.class)).isNotNull();
    }

    @Test
    public void findsTaskById_EmptyData() {
        try {
            var order = testRestTemplate.getForObject("http://localhost:" + port + "/order/1", OrderApi.class);

            // assert
            assertThat(false);
        } catch (Exception e) {
            assertThat(true);
        }
    }

    @Test
    public void findsTaskById_dataFill() {
        // act

        Order order = getTestOrder();

        orderRepository.save(order);

        var orderFromDb = testRestTemplate.getForObject("http://localhost:" + port + "/order/1", OrderApi.class);

        // assert
        assertThat(orderFromDb)
                .extracting(OrderApi::getId, OrderApi::getClientId, OrderApi::getOrderDate, OrderApi::getProducts)
                .containsExactly(1L, 2, null, order.getProducts());
    }

    @Test
    public void getOrders() {
        // act

        Order order = getTestOrder();

        orderRepository.save(order);
        orderRepository.save(order);

        List<OrderApi> orderFromDb = testRestTemplate.exchange("http://localhost:" + port + "/orders",  HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderApi>>() {
        });

        // assert
        assertThat(orderFromDb.size() == 2);

    }

    @Test
    public void addOrder() {
        // act
        OrderApi order = getTestOrderApi();
        testRestTemplate.postForObject("http://localhost:" + port + "/orders", order, OrderApi.class);


        Optional<Order> orderFromDb = orderRepository.findById(1L);

        // assert
        assertThat(orderFromDb.get() != null);
    }

    private OrderApi getTestOrderApi() {
        OrderApi order = new OrderApi();
        order.setId(1);
        List<String> productList = new ArrayList<>();
        productList.add("1");
        order.setProducts(productList);
        order.setClientId(2);
        order.setOrderDate(null);

        return order;
    }

    private Order getTestOrder() {
        Order order = new Order();
        order.setId(1);
        List<String> productList = new ArrayList<>();
        productList.add("1");
        order.setProducts(productList);
        order.setClientId(2);
        order.setOrderDate(null);

        return order;
    }
}
