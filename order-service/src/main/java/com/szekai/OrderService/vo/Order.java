package com.szekai.OrderService.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order {
    private String name;
    private String cardType;
    private int discount;
    private int price;
}
