package factory;

public class FixedDiscountCalculator implements DiscountCalculator{
    @Override
    public double calculateDiscount() {
        return 0.5;
    }
}
