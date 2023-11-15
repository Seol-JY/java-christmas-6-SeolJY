package christmas.promotion;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import christmas.utils.Parser;
import christmas.utils.RetryExecutor;
import christmas.view.InputView;
import java.util.List;
import java.util.function.Supplier;

public class ChristmasPromotion {
    public void run() {
        DecemberDate decemberDate = withRetry(this::processDecemberDate);
        Order order = withRetry(this::processOrder);

    }

    private DecemberDate processDecemberDate() {
        String userInput = InputView.readExpectedVisitDate();
        int date = Parser.parseDate(userInput);
        return DecemberDate.from(date);
    }

    private Order processOrder() {
        OrderItems orderItems = createOrderItems();
        return Order.from(orderItems);
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

    private <T> T withRetry(Supplier<T> function) {
        return RetryExecutor.execute(function, IllegalArgumentException.class);
    }
}
