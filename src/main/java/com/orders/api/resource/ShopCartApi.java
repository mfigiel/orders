package com.orders.api.resource;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class ShopCartApi {

    private List<String> products;
}
