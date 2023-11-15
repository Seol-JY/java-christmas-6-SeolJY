package christmas.view;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import christmas.domain.handler.PromotionHandler;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String DECEMBER_EVENT_PLANNER_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String DECEMBER_EVENT_PREVIEW_FORMAT = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_MENU_TITLE = LINE_SEPARATOR + "<주문 메뉴>";
    private static final String INITIAL_TOTAL_AMOUNT_TITLE = LINE_SEPARATOR + "<할인 전 총주문 금액>";
    private static final String GIFT_MENU_TITLE = LINE_SEPARATOR + "<증정 메뉴>";
    private static final String BENEFIT_DETAILS_TITLE = LINE_SEPARATOR + "<혜택 내역>";
    private static final String TOTAL_BENEFIT_AMOUNT_TITLE = LINE_SEPARATOR + "<총혜택 금액>";
    private static final String EXPECTED_PAYMENT_AMOUNT_TITLE = LINE_SEPARATOR + "<할인 후 예상 결제 금액>";
    private static final String DECEMBER_EVENT_BADGE_TITLE = LINE_SEPARATOR + "<12월 이벤트 배지>";

    private static final String NONE = "없음";
    private static final String EMPTY_STRING = "";

    private static final String MENU_COUNT_FORMAT = "%s %d개";
    private static final String WON_FORMAT = "%s%,d원";
    private static final String BENEFIT_DETAILS_FORMAT = "%s: -%,d원";
    private static final String MINUS_SYMBOL = "-";

    private OutputView() {
    }

    public static void printEventPlannerMessage() {
        System.out.println(DECEMBER_EVENT_PLANNER_MESSAGE);
    }

    public static void printEventPreviewMessage(DecemberDate decemberDate) {
        String output = String.format(DECEMBER_EVENT_PREVIEW_FORMAT, decemberDate.getDate());
        System.out.println(output);
    }

    public static void printOrderMenu(Order order) {
        System.out.println(ORDER_MENU_TITLE);

        OrderItems orderItems = order.getOrderItems();
        Map<MenuInfo, Integer> items = orderItems.getItems();

        for (Entry<MenuInfo, Integer> entry : items.entrySet()) {
            String output = String.format(MENU_COUNT_FORMAT, entry.getKey().getName(), entry.getValue());
            System.out.println(output);
        }
    }

    public static void printInitialToalAmount(Order order) {
        System.out.println(INITIAL_TOTAL_AMOUNT_TITLE);

        String output = String.format(WON_FORMAT, EMPTY_STRING, order.getOriginalTotalPrice());
        System.out.println(output);
    }

    public static void printGiftMenu(Order order) {
        System.out.println(GIFT_MENU_TITLE);

        OrderItems orderItems = order.getBonusItem();
        Map<MenuInfo, Integer> items = orderItems.getItems();

        if (printNoneIfTrue(items.isEmpty())) {
            return;
        }

        for (Entry<MenuInfo, Integer> entry : items.entrySet()) {
            String output = String.format(MENU_COUNT_FORMAT, entry.getKey().getName(), entry.getValue());
            System.out.println(output);
        }
    }

    public static void printBenefitDetails(Order order) {
        System.out.println(BENEFIT_DETAILS_TITLE);

        Map<PromotionHandler, Integer> benefits = order.getBenefits();

        if (printNoneIfTrue(benefits.isEmpty())) {
            return;
        }

        for (Entry<PromotionHandler, Integer> entry : benefits.entrySet()) {
            String output = String.format(BENEFIT_DETAILS_FORMAT, entry.getKey().toString(), entry.getValue());
            System.out.println(output);
        }
    }

    public static void printTotalBenefitAmount(Order order) {
        System.out.println(TOTAL_BENEFIT_AMOUNT_TITLE);

        int benefitPrice = order.getBenefitPrice();

        String symbol = MINUS_SYMBOL;
        if (benefitPrice == 0) {
            symbol = EMPTY_STRING;
        }

        String output = String.format(WON_FORMAT, symbol, benefitPrice);
        System.out.println(output);
    }

    public static void printExpectedPaymentAmount(Order order) {
        System.out.println(EXPECTED_PAYMENT_AMOUNT_TITLE);

        String output = String.format(WON_FORMAT, EMPTY_STRING, order.calculateFinalPrice());
        System.out.println(output);
    }

    public static void printEventBedge(Order order) {
        System.out.println(DECEMBER_EVENT_BADGE_TITLE);
        order.getBadge().ifPresentOrElse(
                badge -> System.out.println(badge.getName()),
                () -> System.out.println(NONE)
        );
    }

    private static boolean printNoneIfTrue(boolean items) {
        if (items) {
            System.out.println(NONE);
            return true;
        }
        return false;
    }
}
