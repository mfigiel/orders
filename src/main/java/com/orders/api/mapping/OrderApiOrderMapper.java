package com.orders.api.mapping;

import com.orders.api.resource.OrderApi;
import com.orders.repository.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderApiOrderMapper {
    OrderApi orderDtoToOrderApi(Order source);
    Order orderApiToOrderDto(OrderApi source);
}

