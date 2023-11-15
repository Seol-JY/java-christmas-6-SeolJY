package christmas.domain.handler;

import christmas.domain.DecemberDate;
import christmas.domain.Order;

public class CristmasPromotionHandler extends PromotionHandler {
    private static final String PROMOTION_NAME = "크리스마스 디데이 할인";
    private static final int CHRISTMAS_DATE = 25;
    private static final int DISCOUNT_WEIGHT = 100;
    private static final int MAX_DISCOUNT = 3_400;

    @Override
    protected void process(Order order) {
        DecemberDate decemberDate = order.getDecemberDate();
        decemberDate.getDaysDifferenceIfEarlier(CHRISTMAS_DATE).ifPresent(
                dday -> {
                    int discountAmount = MAX_DISCOUNT - dday * DISCOUNT_WEIGHT;
                    order.addBenefits(this, discountAmount);
                }
        );
    }

    @Override
    public String toString() {
        return PROMOTION_NAME;
    }
}
