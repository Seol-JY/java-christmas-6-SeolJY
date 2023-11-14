package christmas.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("MenuCategory 테스트")
class MenuCategoryTest {

    @ParameterizedTest
    @EnumSource(MenuCategory.class)
    @DisplayName("[성공] 특정 카테고리에 해당 메뉴가 포함됨을 확인할 수 있다.")
    void testMenuBelongsToCategory(MenuCategory category) {
        MenuInfo sampleMenu = category.getMenuList().get(0);

        assertThat(category.containsMenu(sampleMenu)).isTrue();
    }

    @Test
    @DisplayName("[예외] 특정 카테고리에 해당 메뉴가 포함되지 않음을 확인할 수 있다.")
    void testMenuDoesNotBelongToCategory() {
        MenuCategory category = MenuCategory.DESSERT;

        MenuInfo nonExistentMenu = MenuInfo.RED_WINE;

        assertThat(category.containsMenu(nonExistentMenu)).isFalse();
    }
}
