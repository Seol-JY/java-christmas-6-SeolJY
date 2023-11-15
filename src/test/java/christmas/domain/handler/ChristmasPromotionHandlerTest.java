package christmas.domain.handler;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChristmasPromotionHandlerTest {

    private ChristmasPromotionHandler christmasPromotionHandler;
    private Order order;

    @BeforeEach
    void setUp() {
        christmasPromotionHandler = new ChristmasPromotionHandler();
    }

    @Test
    @DisplayName("[성공] 크리스마스 디데이 할인 핸들러가 정상적으로 동작한다.")
    void testChristmasPromotionHandler() {
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        order = Order.of(DecemberDate.from(20), orderItems);

        christmasPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(2900);
    }

    @Test
    @DisplayName("[성공] 크리스마스 디데이가 지난 경우에는 할인이 적용되지 않는다.")
    void testChristmasPromotionHandlerPastChristmas() {
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        order = Order.of(DecemberDate.from(27), orderItems);

        christmasPromotionHandler.process(order);

        assertThat(order.getBenefitPrice()).isEqualTo(0);
    }
}
