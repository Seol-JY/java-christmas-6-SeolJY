package christmas.domain.handler;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekdayPromotionHandlerTest {
    @Test
    @DisplayName("[성공] 평일 할인 핸들러가 주문이 평일인 경우 정상적으로 동작한다.")
    void testWeekdayPromotionHandlerOnWeekday() {
        WeekdayPromotionHandler weekdayPromotionHandler = new WeekdayPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.CHOCOLATE_CAKE, 1);
        Order order = Order.of(DecemberDate.from(3), orderItems);

        weekdayPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(2023);
    }

    @Test
    @DisplayName("[성공] 평일 할인 핸들러가 주문이 주말인 경우 할인이 적용되지 않는다.")
    void testWeekdayPromotionHandlerOnWeekend() {
        WeekdayPromotionHandler weekdayPromotionHandler = new WeekdayPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.CHOCOLATE_CAKE, 1);
        Order order = Order.of(DecemberDate.from(1), orderItems);

        weekdayPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(0);
    }

    @Test
    @DisplayName("[성공] 평일 할인 핸들러가 여러 메뉴를 주문한 경우 정상적으로 동작한다.")
    void testWeekdayPromotionHandlerWithMultipleMenuItems() {
        WeekdayPromotionHandler weekdayPromotionHandler = new WeekdayPromotionHandler();
        OrderItems orderItems = OrderItems.create()
                .addItem(MenuInfo.CHOCOLATE_CAKE, 2)
                .addItem(MenuInfo.CAESAR_SALAD, 1);

        Order order = Order.of(DecemberDate.from(3), orderItems);

        weekdayPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(4046);
    }

    @Test
    @DisplayName("[성공] 평일 할인 핸들러가 주문이 평일이지만 해당 카테고리에 주문이 없는 경우 할인이 적용되지 않는다.")
    void testWeekdayPromotionHandlerWithNoItemsInCategory() {
        // Given
        WeekdayPromotionHandler weekdayPromotionHandler = new WeekdayPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.BBQ_RIBS, 1);
        Order order = Order.of(DecemberDate.from(3), orderItems);

        // When
        weekdayPromotionHandler.process(order);

        // Then
        assertThat(order.getBenefitPrice()).isZero();
    }
}