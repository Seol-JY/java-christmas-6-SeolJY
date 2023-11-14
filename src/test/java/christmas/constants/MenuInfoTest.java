package christmas.constants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("MenuInfo 테스트")
class MenuInfoTest {
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드", "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타", "초코케이크", "아이스크림", "제로콜라",
            "레드와인", "샴페인"})
    @DisplayName("[성공] 메뉴 이름으로 MenuInfo를 가져올 수 있다.")
    void getMenuInfoByName(String menuName) {
        MenuInfo menuInfo = MenuInfo.getMenuInfoByName(menuName);

        assertThat(menuInfo.getName()).isEqualTo(menuName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"InvalidMenu", "InvalidName", "InvalidItem"})
    @DisplayName("[예외] 존재하지 않는 메뉴 이름으로 MenuInfo를 가져오려고 하면 예외가 발생한다.")
    void getMenuInfoByInvalidName(String invalidMenuName) {
        assertThrows(IllegalArgumentException.class, () -> MenuInfo.getMenuInfoByName(invalidMenuName));
    }
}

