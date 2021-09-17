package com.orders.api.controller;

import com.orders.api.resource.OrderApi;
import com.orders.api.resource.OrderEvents;
import com.orders.api.resource.OrderStates;
import com.orders.api.resource.UpdateStateOrderApi;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class OrderStateControllerTest {

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
    public void getStateOrder_NoResults() throws Exception {
        mockMvc.perform(get("/orderState/order/", 1)
                .param("id", "1"))
                .andExpect(status().is4xxClientError());

        assertThat(this.testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/orderState/order/?id=1",
                String.class)).isNotNull();
    }

    @Test
    public void getInittialOrderState() {
        // act
        orderRepository.save(getTestOrder(1));

        String orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.SUBMITTED), orderState);
    }

    @Test
    public void updateOrderStateToPay() {
        // act
        orderRepository.save(getTestOrder(1));
        UpdateStateOrderApi updateStateOrderApi = getUpdateStateOrderApi();

        updateStateOrderApi.setOrderEvents(OrderEvents.PAY);
        updateStateOrder(updateStateOrderApi);

        String orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.PAID), orderState);
    }

    @Test
    public void updateOrderStateToFullFill() {
        // act
        orderRepository.save(getTestOrder(1));
        UpdateStateOrderApi updateStateOrderApi = getUpdateStateOrderApi();

        updateStateOrderApi.setOrderEvents(OrderEvents.FULFILL);
        updateStateOrder(updateStateOrderApi);
        String orderState = makeReguestToGetOrderState();
        assertNotEquals(String.valueOf(OrderStates.FULLFILLED), orderState);
        assertNotEquals(String.valueOf(OrderStates.PAID), orderState);
        assertEquals(String.valueOf(OrderStates.SUBMITTED), orderState);

        updateStateOrderApi.setOrderEvents(OrderEvents.PAY);
        updateStateOrder(updateStateOrderApi);
        orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.PAID), orderState);

        updateStateOrderApi.setOrderEvents(OrderEvents.FULFILL);
        updateStateOrder(updateStateOrderApi);
        orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.FULLFILLED), orderState);

    }

    @Test
    public void updateOrderStateToCancel() {
        // act
        orderRepository.save(getTestOrder(1));
        UpdateStateOrderApi updateStateOrderApi = getUpdateStateOrderApi();

        updateStateOrderApi.setOrderEvents(OrderEvents.CANCEL);
        updateStateOrder(updateStateOrderApi);

        String orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.CANCELLED), orderState);
    }

    @Test
    public void updateOrderStateToCancelFromPaid() {
        // act
        orderRepository.save(getTestOrder(1));
        UpdateStateOrderApi updateStateOrderApi = getUpdateStateOrderApi();

        updateStateOrderApi.setOrderEvents(OrderEvents.PAY);
        updateStateOrder(updateStateOrderApi);
        String orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.PAID), orderState);

        updateStateOrderApi.setOrderEvents(OrderEvents.CANCEL);
        updateStateOrder(updateStateOrderApi);
        orderState = makeReguestToGetOrderState();

        assertEquals(String.valueOf(OrderStates.CANCELLED), orderState);
    }

    private String makeReguestToGetOrderState() {
        return testRestTemplate.getForObject(HTTP_LOCALHOST + port + "/orderState/order/1", String.class);
    }

    private void updateStateOrder(UpdateStateOrderApi updateStateOrderApi) {
        testRestTemplate.postForObject(HTTP_LOCALHOST + port + "/updateStateOrder", updateStateOrderApi, UpdateStateOrderApi.class);
    }

    private UpdateStateOrderApi getUpdateStateOrderApi() {
        UpdateStateOrderApi updateStateOrderApi = new UpdateStateOrderApi();
        updateStateOrderApi.setOrderId(1L);
        updateStateOrderApi.setPaymentConfirmationNumber(String.valueOf(UUID.randomUUID()));
        return updateStateOrderApi;
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
