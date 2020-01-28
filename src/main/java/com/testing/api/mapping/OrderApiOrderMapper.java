package com.testing.api.mapping;

import com.testing.api.resource.OrderApi;
import com.testing.repository.entity.Order;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "OrderApiOrderMapper")
public interface OrderApiOrderMapper {
    OrderApi orderDtoToOrderApi(Optional<Order> source);
    Order orderApiToOrderDto(OrderApi source);
}

