package factory;

public class HolidayDiscountCalculator implements DiscountCalculator{
    @Override
    public double calculateDiscount() {
        return 0.1;
    }
}
