package christmas.domain.handler;

import christmas.constants.MenuCategory;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;

public class WeekdayPromotionHandler extends PromotionHandler {
    private static final String PROMOTION_NAME = "평일 할인";
    private static final int DISCOUNT_PER_MENU = 2_023;

    @Override
    protected void process(Order order) {
        DecemberDate decemberDate = order.getDecemberDate();
        if (decemberDate.isWeekend()) {
            OrderItems orderItems = order.getOrderItems();
            int totalCount = orderItems.getTotalCountForCategory(MenuCategory.DESSERT);

            int discountAmount = totalCount * DISCOUNT_PER_MENU;
            order.addBenefits(this, discountAmount);
        }
    }

    @Override
    public String toString() {
        return PROMOTION_NAME;
    }
}
