package christmas.domain;

import christmas.constants.Badge;
import christmas.constants.ErrorMessage;
import christmas.constants.MenuInfo;
import christmas.domain.handler.PromotionHandler;
import java.util.Map;

public class Order {
    private final OrderItems orderItems;
    private final int originalTotalPrice;

    private OrderItems bonusItem = OrderItems.create();

    private int benefitPrice;
    private Map<PromotionHandler, Integer> benefits;

    private Badge badge;

    private Order(OrderItems orderItems) {
        validateOrderConditions(orderItems);
        this.orderItems = orderItems;
        originalTotalPrice = calculateOriginalPrice();
    }

    public static Order from(OrderItems orderItems) {
        return new Order(orderItems);
    }

    public void addBonusItem(MenuInfo bonusMenuItem, int quantity) {
        bonusItem = bonusItem.addItem(bonusMenuItem, quantity);
    }

    public void addBenefits(PromotionHandler promotionHandler, int benefitAmount) {
        if (benefits.containsKey(promotionHandler)) {
            throw new IllegalStateException(ErrorMessage.DUPLICATED_PROMOTION.getMessage());
        }

        benefits.put(promotionHandler, benefitAmount);
        benefitPrice += benefitAmount;
    }

    public int calculateFinalPrice() {
        return originalTotalPrice - benefitPrice;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public OrderItems getBonusItem() {
        return bonusItem;
    }

    private int calculateOriginalPrice() {
        return orderItems.getItems().entrySet().stream()
                .mapToInt(entry -> {
                    MenuInfo menu = entry.getKey();
                    int quantity = entry.getValue();
                    return menu.getPrice() * quantity;
                })
                .sum();
    }

    private void validateOrderConditions(OrderItems orderItems) {
        if (orderItems.containsOnlyBeverages()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }
}
