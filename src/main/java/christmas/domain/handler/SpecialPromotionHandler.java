package christmas.domain.handler;

import christmas.domain.DecemberDate;
import christmas.domain.Order;
import java.util.List;

public class SpecialPromotionHandler extends PromotionHandler {
    private static final String PROMOTION_NAME = "특별 할인";
    private static final int DISCOUNT_AMOUNT = 1_000;
    private static final List<DecemberDate> discountDecemberDates = List.of(
            DecemberDate.from(3),
            DecemberDate.from(10),
            DecemberDate.from(17),
            DecemberDate.from(24),
            DecemberDate.from(25),
            DecemberDate.from(31)
    );

    @Override
    protected void process(Order order) {
        DecemberDate decemberDate = order.getDecemberDate();

        boolean isDiscountDate = discountDecemberDates.stream()
                .anyMatch(date -> date.equals(decemberDate));

        if (isDiscountDate) {
            order.addBenefits(this, DISCOUNT_AMOUNT);
        }
    }

    @Override
    public String toString() {
        return PROMOTION_NAME;
    }
}
