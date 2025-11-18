package com.tania.response;

import com.tania.dto.OrderHistory;
import com.tania.model.Cart;
import com.tania.model.Order;
import com.tania.model.Product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private String functionName;
    private Cart userCart;
    private OrderHistory orderHistory;
    private Product product;
}
