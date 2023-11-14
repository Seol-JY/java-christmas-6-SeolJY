package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("DecemberDate 테스트")
class DecemberDateTest {
    private DecemberDate date;

    @BeforeEach
    void setUp() {
        date = DecemberDate.from(15); // Default date for tests
    }

    @Test
    @DisplayName("[예외] 유효하지 않은 날짜를 생성하려 하면 예외가 발생한다.")
    void testInvalidDateCreation() {
        assertThrows(IllegalArgumentException.class, () -> DecemberDate.from(35));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("[성공] 해당 날짜가 주말인 경우에 true를 반환한다.")
    void testIsWeekend(int day) {
        date = DecemberDate.from(day);

        assertThat(date.isWeekend()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("[예외] 해당 날짜가 평일이 아닌 경우에 false를 반환한다.")
    void testIsWeekday(int day) {
        date = DecemberDate.from(day);

        assertThat(date.isWeekday()).isFalse();
    }

    @Test
    @DisplayName("[성공] 동일한 날짜인 경우에 true를 반환한다.")
    void testIsSameDateTrue() {
        assertThat(date.isSameDate(15)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 25, 30})
    @DisplayName("[성공] 동일하지 않은 날짜인 경우에 false를 반환한다.")
    void testIsSameDateFalse(int otherDate) {
        assertThat(date.isSameDate(otherDate)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"10, 25, 15", "20, 25, 5"})
    @DisplayName("[성공] 이전 날짜와의 일수 차이가 정상적으로 나타난다.")
    void testGetDaysDifferenceIfEarlier(int earlierDate, int laterDate, int expectedDifference) {
        date = DecemberDate.from(earlierDate);
        assertThat(date.getDaysDifferenceIfEarlier(laterDate)).isPresent().contains(expectedDifference);
    }
}