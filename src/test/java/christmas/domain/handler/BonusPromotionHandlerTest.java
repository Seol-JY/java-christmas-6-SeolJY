package christmas.domain.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BonusPromotionHandlerTest {
    @Test
    @DisplayName("[성공] 증정 이벤트 핸들러가 정상적으로 동작한다.")
    void testBonusPromotionHandler() {
        BonusPromotionHandler bonusPromotionHandler = new BonusPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 10);
        Order order = Order.of(DecemberDate.from(15), orderItems);

        bonusPromotionHandler.process(order);

        assertThat(order.getBonusItem().getItems()).containsExactly(Map.entry(MenuInfo.CHAMPAGNE, 1));
        assertEquals(order.getBenefitPrice(), MenuInfo.CHAMPAGNE.getPrice());
    }

    @Test
    @DisplayName("[성공] 최소 금액에 도달하지 못하면 증정 이벤트 핸들러가 동작하지 않는다.")
    void testBonusPromotionHandlerNotTriggered() {
        BonusPromotionHandler bonusPromotionHandler = new BonusPromotionHandler();
        OrderItems orderItems = OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1);
        Order order = Order.of(DecemberDate.from(15), orderItems);

        bonusPromotionHandler.process(order);

        assertThat(order.getBonusItem().getItems()).isEmpty();
        assertEquals(order.getBenefitPrice(), 0);
    }
}