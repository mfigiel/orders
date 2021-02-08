package com.orders.api.resource;

import lombok.Data;

@Data
public class UpdateStateOrderApi {

    Long orderId;
    String orderState;
    String paymentConfirmationNumber;
}
