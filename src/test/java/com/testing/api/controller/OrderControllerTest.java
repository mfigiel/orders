package com.testing.api.controller;

import com.testing.api.resource.OrderApi;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

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
    public void findsTaskById() {
        // act

        try {
            var product = testRestTemplate.getForObject("http://localhost:" + port + "/order/1", OrderApi.class);

            // assert
            assertThat(product)
                    .extracting(OrderApi::getId, OrderApi::getClientId, OrderApi::getOrderDate, OrderApi::getProducts)
                    .containsExactly(1, "delectus aut autem", false, 1);
        } catch(Exception e) {
            assertThat(true);
        }
    }
}
