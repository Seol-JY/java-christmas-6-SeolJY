package christmas.domain.handler;

import christmas.constants.Badge;
import christmas.domain.Order;

public class BadgePromotionHandler extends PromotionHandler {
    private static final String PROMOTION_NAME = "배지 제공";
    private static final int STAR_BADGE_THRESHOLD = 5_000;
    private static final int TREE_BADGE_THRESHOLD = 10_000;
    private static final int SANTA_BADGE_THRESHOLD = 20_000;

    @Override
    protected void process(Order order) {

        int benefitPrice = order.getBenefitPrice();

        if (benefitPrice >= SANTA_BADGE_THRESHOLD) {
            order.setBadge(Badge.SANTA);
            return;
        }
        if (benefitPrice >= TREE_BADGE_THRESHOLD) {
            order.setBadge(Badge.TREE);
            return;
        }
        if (benefitPrice >= STAR_BADGE_THRESHOLD) {
            order.setBadge(Badge.STAR);
        }
    }

    @Override
    public String toString() {
        return PROMOTION_NAME;
    }
}

