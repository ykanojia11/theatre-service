package com.movies.service.impl;

import com.movies.repository.entities.Show;
import com.movies.service.DiscountStrategy;

import java.time.LocalTime;

public class AfternoonShowDiscountStrategy implements DiscountStrategy {

    private final Show show;
    public AfternoonShowDiscountStrategy(Show show) {
        this.show = show;
    }
    @Override
    public double applyDiscount(double totalPrice, int ticketCount) {
        LocalTime showTime = show.getShowTime().toLocalTime();
        if (showTime.isAfter(LocalTime.NOON) && showTime.isBefore(LocalTime.of(17, 0))) {
            // 20% discount for afternoon shows
            return 0.2 * totalPrice;
        }
        return 0.0;
    }
}
