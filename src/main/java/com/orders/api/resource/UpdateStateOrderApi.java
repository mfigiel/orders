package com.orders.api.resource;

import lombok.Data;

@Data
public class UpdateStateOrderApi {

    Long orderId;
    OrderEvents orderEvents;
    String paymentConfirmationNumber;
}
