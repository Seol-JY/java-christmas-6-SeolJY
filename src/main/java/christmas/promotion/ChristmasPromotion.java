package christmas.promotion;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import christmas.domain.handler.BadgePromotionHandler;
import christmas.domain.handler.BonusPromotionHandler;
import christmas.domain.handler.ChristmasPromotionHandler;
import christmas.domain.handler.PromotionHandler;
import christmas.domain.handler.SpecialPromotionHandler;
import christmas.domain.handler.WeekdayPromotionHandler;
import christmas.domain.handler.WeekendPromotionHandler;
import christmas.utils.Parser;
import christmas.utils.RetryExecutor;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class ChristmasPromotion {
    public void run() {
        OutputView.printEventPlannerMessage();

        DecemberDate decemberDate = withRetry(this::processDecemberDate);
        Order order = withRetry(() -> processOrder(decemberDate));
        applyDiscount(order);

        processResult(decemberDate, order);
    }

    private DecemberDate processDecemberDate() {
        String userInput = InputView.readExpectedVisitDate();
        int date = Parser.parseDate(userInput);
        return DecemberDate.from(date);
    }

    private Order processOrder(DecemberDate decemberDate) {
        OrderItems orderItems = createOrderItems();
        return Order.of(decemberDate, orderItems);
    }

    private OrderItems createOrderItems() {
        String userInput = InputView.readMenuOrder();
        List<String> splitUserInput = Parser.parseOrderItems(userInput);

        OrderItems orderItems = OrderItems.create();

        for (String userInputPart : splitUserInput) {
            String menuName = Parser.parseMenuAtOrderItem(userInputPart);
            int quantity = Parser.parseQuantityAtOrderItem(userInputPart);

            MenuInfo menuInfo = MenuInfo.getMenuInfoByName(menuName);
            orderItems = orderItems.addItem(menuInfo, quantity);
        }

        return orderItems;
    }

    public void applyDiscount(Order order) {
        PromotionHandler bonusPromotionHandler = new BonusPromotionHandler();

        bonusPromotionHandler
                .setNext(new WeekendPromotionHandler())
                .setNext(new WeekdayPromotionHandler())
                .setNext(new ChristmasPromotionHandler())
                .setNext(new SpecialPromotionHandler())
                .setNext(new BadgePromotionHandler());

        bonusPromotionHandler.run(order);
    }

    private static void processResult(DecemberDate decemberDate, Order order) {
        OutputView.printEventPreviewMessage(decemberDate);
        OutputView.printOrderMenu(order);
        OutputView.printInitialToalAmount(order);
        OutputView.printGiftMenu(order);
        OutputView.printBenefitDetails(order);
        OutputView.printTotalBenefitAmount(order);
        OutputView.printExpectedPaymentAmount(order);
        OutputView.printEventBedge(order);
    }

    private <T> T withRetry(Supplier<T> function) {
        return RetryExecutor.execute(function, IllegalArgumentException.class);
    }
}
