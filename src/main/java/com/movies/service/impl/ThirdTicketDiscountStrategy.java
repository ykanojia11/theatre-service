package com.movies.service.impl;

import com.movies.repository.entities.Show;
import com.movies.service.DiscountStrategy;

public class ThirdTicketDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalPrice, int ticketCount) {
        if (ticketCount >= 3) {
            // 50% discount on the third ticket
            return 0.5 * (totalPrice / ticketCount);
        }
        return 0.0;
    }
}
