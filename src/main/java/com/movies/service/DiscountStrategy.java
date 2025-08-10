package com.movies.service;

import com.movies.repository.entities.Show;

public interface DiscountStrategy {
    double applyDiscount(double totalPrice, int ticketCount);
}
