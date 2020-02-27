package com.testing.api.mapping;

import com.testing.api.resource.OrderApi;
import com.testing.repository.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "OrderApiOrderMapper")
public interface OrderApiOrderMapper {
    OrderApi orderDtoToOrderApi(Order source);
    Order orderApiToOrderDto(OrderApi source);
}

