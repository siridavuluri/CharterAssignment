package com.test.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Money {

    private String currency;
    private Double amount;

    public Money(Double amt) {
        this.amount = amt;
        this.currency = "$";
    }
}
