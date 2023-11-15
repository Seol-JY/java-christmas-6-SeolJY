package christmas.domain;

import christmas.constants.Badge;
import christmas.constants.ErrorMessage;
import christmas.constants.MenuInfo;
import christmas.domain.handler.BonusPromotionHandler;
import christmas.domain.handler.PromotionHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Order {
    private final DecemberDate decemberDate;
    private final OrderItems orderItems;
    private final int originalTotalPrice;

    private OrderItems bonusItem = OrderItems.create();

    private int benefitPrice;
    private int discountAmount;
    private final Map<PromotionHandler, Integer> benefits = new HashMap<>();

    private Badge badge;

    private Order(DecemberDate decemberDate, OrderItems orderItems) {
        validateOrderConditions(orderItems);
        this.decemberDate = decemberDate;
        this.orderItems = orderItems;

        originalTotalPrice = calculateOriginalPrice();
    }

    public static Order of(DecemberDate decemberDate, OrderItems orderItems) {
        return new Order(decemberDate, orderItems);
    }

    public void addBonusItem(MenuInfo bonusMenuItem, int quantity) {
        bonusItem = bonusItem.addItem(bonusMenuItem, quantity);
    }

    public void addBenefits(PromotionHandler promotionHandler, int benefitAmount) {
        if (benefits.containsKey(promotionHandler)) {
            throw new IllegalStateException(ErrorMessage.DUPLICATED_PROMOTION.getMessage());
        }
        if (benefitAmount == 0) {
            return;
        }

        benefits.put(promotionHandler, benefitAmount);
        benefitPrice += benefitAmount;

        if (!(promotionHandler instanceof BonusPromotionHandler)) {
            discountAmount += benefitAmount;
        }
    }

    public int calculateFinalPrice() {
        return originalTotalPrice - discountAmount;
    }

    public int getBenefitPrice() {
        return benefitPrice;
    }

    public DecemberDate getDecemberDate() {
        return decemberDate;
    }

    public int getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public Optional<Badge> getBadge() {
        return Optional.ofNullable(badge);
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public OrderItems getBonusItem() {
        return bonusItem;
    }

    public OrderItems getOrderItems() {
        return orderItems;
    }

    public Map<PromotionHandler, Integer> getBenefits() {
        return Collections.unmodifiableMap(benefits);
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
