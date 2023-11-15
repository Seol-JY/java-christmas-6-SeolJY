package christmas.domain.handler;

import christmas.domain.Order;

public abstract class PromotionHandler {
    private final int MIN_PROMOTION_AMOUNT = 10_000;
    protected PromotionHandler nextHandler = null;

    public PromotionHandler setNext(PromotionHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    public void run(Order order) {
        if (canApplyPromotion(order)) {
            process(order);
        }
        if (nextHandler != null) {
            nextHandler.run(order);
        }
    }

    protected abstract void process(Order order);

    private boolean canApplyPromotion(Order order) {
        return order.getOriginalTotalPrice() >= MIN_PROMOTION_AMOUNT;
    }

    @Override
    public abstract String toString();
}
