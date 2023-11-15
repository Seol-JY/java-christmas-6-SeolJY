package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import christmas.constants.MenuCategory;
import christmas.constants.MenuInfo;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("OrderItems 테스트")
class OrderItemsTest {
    private static final int MAX_TOTAL_ITEM_COUNT = 20;
    private OrderItems orderItems;

    @BeforeEach
    void setUp() {
        orderItems = OrderItems.create();
    }

    @Test
    @DisplayName("[성공] 아이템이 정상적으로 추가 된다.")
    void testAddItem() {
        MenuInfo menuInfo = MenuInfo.TAPAS;
        int quantity = 3;

        OrderItems updatedOrderItems = orderItems.addItem(menuInfo, quantity);

        assertThat(updatedOrderItems.getItems()).containsExactly(Map.entry(menuInfo, quantity));
    }

    @Test
    @DisplayName("[예외] 중복된 메뉴가 추가되면 예외가 발생한다.")
    void testAddDuplicateItem() {
        MenuInfo menuInfo = MenuInfo.TAPAS;
        int quantity = 3;

        OrderItems updatedOrderItems = orderItems.addItem(menuInfo, quantity);

        assertThrows(IllegalArgumentException.class, () -> updatedOrderItems.addItem(menuInfo, quantity));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, 0, -10})
    @DisplayName("[예외] 유효하지 않은 수량 (0 이하)으로 아이템을 추가하는 경우 예외가 발생한다.")
    void testAddItemWithNegativeQuantity(int quantity) {
        MenuInfo menuInfo = MenuInfo.TAPAS;

        assertThrows(IllegalArgumentException.class, () -> orderItems.addItem(menuInfo, quantity));
    }

    @Test
    @DisplayName("[예외] 메뉴를 20개 초과하여 주문하는 경우 예외가 발생한다.")
    void testAddItemWithExceededMaxItemCount() {
        MenuInfo menuInfo = MenuInfo.TAPAS;
        int maxItemCount = MAX_TOTAL_ITEM_COUNT;

        OrderItems updatedOrderItems = orderItems.addItem(menuInfo, maxItemCount - 1);

        assertThrows(IllegalArgumentException.class, () -> updatedOrderItems.addItem(MenuInfo.RED_WINE, 2));
    }

    @Test
    @DisplayName("[성공] 음료만 주문한 경우에, containsOnlyBeverages가 참이다.")
    void testContainsOnlyBeverages() {
        orderItems = OrderItems.create()
                .addItem(MenuInfo.ZERO_COLA, 2)
                .addItem(MenuInfo.RED_WINE, 1);

        boolean result = orderItems.containsOnlyBeverages();

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[성공] 음료 이외의 메뉴도 주문한 경우에, containsOnlyBeverages가 거짓이다.")
    void testContainsNonBeverages() {
        orderItems = OrderItems.create()
                .addItem(MenuInfo.ZERO_COLA, 2)
                .addItem(MenuInfo.TAPAS, 1);

        boolean result = orderItems.containsOnlyBeverages();

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[성공] 특정 카테고리에 속하는 메뉴들의 총 수량이 정확하게 반환된다.")
    void testGetTotalCountForCategory() {
        MenuInfo beverageMenu = MenuInfo.ZERO_COLA;
        MenuInfo beverageMenu2 = MenuInfo.CHAMPAGNE;
        MenuInfo nonBeverageMenu = MenuInfo.TAPAS;

        orderItems = OrderItems.create()
                .addItem(beverageMenu, 2)
                .addItem(beverageMenu2, 4)
                .addItem(nonBeverageMenu, 10);

        int totalBeverageCount = orderItems.getTotalCountForCategory(MenuCategory.BEVERAGE);
        System.out.println(totalBeverageCount);
        int totalNonBeverageCount = orderItems.getTotalCountForCategory(MenuCategory.MAIN);

        // Assert
        assertThat(totalBeverageCount).isEqualTo(6);
        assertThat(totalNonBeverageCount).isEqualTo(0);
    }
}

