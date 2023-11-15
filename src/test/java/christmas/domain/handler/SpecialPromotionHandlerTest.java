package christmas.domain.handler;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpecialPromotionHandlerTest {
    @Test
    @DisplayName("[성공] 특별 할인 핸들러가 할인 날짜에 정상적으로 동작한다.")
    void testSpecialPromotionHandlerDiscountDate() {
        SpecialPromotionHandler specialPromotionHandler = new SpecialPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        Order order = Order.of(DecemberDate.from(3), orderItems);

        specialPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("[성공] 특별 할인 핸들러가 할인 날짜가 아닌 경우에는 할인이 적용되지 않는다.")
    void testSpecialPromotionHandlerNonDiscountDate() {
        SpecialPromotionHandler specialPromotionHandler = new SpecialPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        Order order = Order.of(DecemberDate.from(5), orderItems);

        specialPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isZero();
    }

    @Test
    @DisplayName("[성공] 특별 할인 핸들러가 여러 할인 날짜 중 하나에 정상적으로 동작한다.")
    void testSpecialPromotionHandlerMultipleDiscountDates() {
        SpecialPromotionHandler specialPromotionHandler = new SpecialPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        Order order = Order.of(DecemberDate.from(24), orderItems);

        specialPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("[성공] 특별 할인 핸들러가 할인 날짜에 여러 메뉴를 주문해도 정상적으로 동작한다.")
    void testSpecialPromotionHandlerWithMultipleMenuItems() {
        SpecialPromotionHandler specialPromotionHandler = new SpecialPromotionHandler();
        OrderItems orderItems = OrderItems.create()
                .addItem(MenuInfo.T_BONE_STEAK, 1)
                .addItem(MenuInfo.RED_WINE, 2);

        Order order = Order.of(DecemberDate.from(25), orderItems);

        specialPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(1000);
    }
}