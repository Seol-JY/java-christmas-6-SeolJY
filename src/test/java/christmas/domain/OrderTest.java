package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import christmas.constants.Badge;
import christmas.constants.ErrorMessage;
import christmas.constants.MenuInfo;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Order 테스트")
class OrderTest {
    private OrderItems orderItems;

    @BeforeEach
    void setUp() {
        orderItems = OrderItems.create()
                .addItem(MenuInfo.CAESAR_SALAD, 2)
                .addItem(MenuInfo.T_BONE_STEAK, 1)
                .addItem(MenuInfo.RED_WINE, 1);
    }

    @Test
    @DisplayName("[성공] 주문 생성 시 유효한 주문이 생성된다.")
    void testValidOrderCreation() {
        assertDoesNotThrow(() -> Order.from(orderItems));
    }

    @Test
    @DisplayName("[예외] 음료만 주문 시 예외가 발생한다.")
    void testInvalidOrderCreationWithOnlyBeverages() {
        OrderItems beverageOrderItems = OrderItems.create()
                .addItem(MenuInfo.RED_WINE, 1)
                .addItem(MenuInfo.ZERO_COLA, 2);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Order.from(beverageOrderItems));
        assertThat(exception.getMessage()).isEqualTo(ErrorMessage.INVALID_ORDER.getMessage());
    }

    @Test
    @DisplayName("[성공] 증정 메뉴가 정상적으로 추가된다.")
    void testAddBonusItem() {
        Order order = Order.from(orderItems);
        MenuInfo bonusItem = MenuInfo.ICE_CREAM;
        int quantity = 2;

        order.addBonusItem(bonusItem, quantity);

        assertThat(order.getBonusItem().getItems())
                .containsExactly(Map.entry(bonusItem, quantity));
    }

    @Test
    @DisplayName("[성공] 뱃지가 정상적으로 설정된다.")
    void testSetBadge() {
        Order order = Order.from(orderItems);
        Badge badge = Badge.STAR;

        order.setBadge(badge);

        assertThat(order.getBadge()).isEqualTo(badge);
    }
}
