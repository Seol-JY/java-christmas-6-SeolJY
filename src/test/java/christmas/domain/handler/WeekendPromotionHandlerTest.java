package christmas.domain.handler;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekendPromotionHandlerTest {
    @Test
    @DisplayName("[성공] 주말 할인 핸들러가 주문이 주말인 경우 정상적으로 동작한다.")
    void testWeekendPromotionHandlerOnWeekend() {
        WeekendPromotionHandler weekendPromotionHandler = new WeekendPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        Order order = Order.of(DecemberDate.from(1), orderItems);

        weekendPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(2023);
    }

    @Test
    @DisplayName("[성공] 주말 할인 핸들러가 주문이 주말이지만 해당 카테고리에 주문이 없는 경우 할인이 적용되지 않는다.")
    void testWeekendPromotionHandlerWithNoItemsInCategory() {
        WeekendPromotionHandler weekendPromotionHandler = new WeekendPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.CHOCOLATE_CAKE, 1);
        Order order = Order.of(DecemberDate.from(3), orderItems);

        weekendPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isZero();
    }

    @Test
    @DisplayName("[성공] 주말 할인 핸들러가 여러 메뉴를 주문한 경우 정상적으로 동작한다.")
    void testWeekendPromotionHandlerWithMultipleMenuItems() {
        WeekendPromotionHandler weekendPromotionHandler = new WeekendPromotionHandler();
        OrderItems orderItems = OrderItems.create()
                .addItem(MenuInfo.T_BONE_STEAK, 2)
                .addItem(MenuInfo.BBQ_RIBS, 1);

        Order order = Order.of(DecemberDate.from(1), orderItems);

        weekendPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(6069);
    }

    @Test
    @DisplayName("[성공] 주말 할인 핸들러가 주문이 주말이지만 주문이 주메뉴 카테고리에 속하지 않는 경우 할인이 적용되지 않는다.")
    void testWeekendPromotionHandlerWithNoItemsInMainCategory() {
        WeekendPromotionHandler weekendPromotionHandler = new WeekendPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.CHOCOLATE_CAKE, 1);
        Order order = Order.of(DecemberDate.from(1), orderItems);

        weekendPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isZero();
    }
}