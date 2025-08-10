package com.movies.service.impl;

import com.movies.repository.entities.Show;
import com.movies.service.DiscountStrategy;

import java.util.List;

public class DiscountStrategyFactory {
    public static DiscountStrategy getStrategy(Show show, List<String> seatNumbers) {
        // Example: apply both discounts if eligible
        DiscountStrategy thirdTicket = new ThirdTicketDiscountStrategy();
        DiscountStrategy afternoonShow = new AfternoonShowDiscountStrategy(show);
        return (totalPrice, ticketCount) -> {
            double discount = 0.0;
            discount += thirdTicket.applyDiscount(totalPrice, ticketCount);
            discount += afternoonShow.applyDiscount(totalPrice, ticketCount);
            return discount;
        };
    }
}
