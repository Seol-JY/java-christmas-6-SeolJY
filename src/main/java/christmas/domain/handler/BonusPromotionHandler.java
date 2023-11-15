package christmas.domain.handler;

import christmas.constants.MenuInfo;
import christmas.domain.Order;

public class BonusPromotionHandler extends PromotionHandler {
    private static final String PROMOTION_NAME = "증정 이벤트";
    private static final int MIN_GIFT_EVENT_AMOUNT = 120_000;
    private static final int GIFT_QUANTITY = 1;

    @Override
    protected void process(Order order) {
        if (order.getOriginalTotalPrice() >= MIN_GIFT_EVENT_AMOUNT) {
            MenuInfo bonus = MenuInfo.CHAMPAGNE;

            order.addBonusItem(bonus, GIFT_QUANTITY);
            order.addBenefits(this, bonus.getPrice() * GIFT_QUANTITY);
        }
    }

    @Override
    public String toString() {
        return PROMOTION_NAME;
    }

}