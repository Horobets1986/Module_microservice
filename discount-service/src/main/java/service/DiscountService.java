package service;


import factory.DiscountCalculator;
import factory.FixedDiscountCalculator;
import factory.HolidayDiscountCalculator;


import java.time.LocalDate;

public class DiscountService {
    private DiscountCalculator discountCalculator;
    private LocalDate localDate = LocalDate.now();
    public double getPrice() {
        if(localDate.getDayOfMonth() % 2 == 0){
            discountCalculator = new HolidayDiscountCalculator();
            return discountCalculator.calculateDiscount();
        } else {
            discountCalculator = new FixedDiscountCalculator();
            return discountCalculator.calculateDiscount();
        }
    }
}
