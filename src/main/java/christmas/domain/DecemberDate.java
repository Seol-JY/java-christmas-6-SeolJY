package christmas.domain;

import christmas.constants.ErrorMessage;
import java.util.Optional;

public class DecemberDate {
    private static final int MIN_DATE = 1;
    private static final int MAX_DATE = 31;
    private static final int DAYS_IN_WEEK = 7;
    private static final int FIRST_DATE_OF_FRIDAY = 1;
    private static final int FIRST_DATE_OF_SATURDAY = 2;

    private final int date;

    private DecemberDate(int date) {
        validateDate(date);
        this.date = date;
    }

    public static DecemberDate from(int date) {
        return new DecemberDate(date);
    }

    public boolean isWeekend() {
        int dayOfWeekRemainder = date % DAYS_IN_WEEK;
        return dayOfWeekRemainder == FIRST_DATE_OF_FRIDAY || dayOfWeekRemainder == FIRST_DATE_OF_SATURDAY;
    }

    public boolean isWeekday() {
        return !isWeekend(); // 주말이 아니면 평일
    }

    public boolean isSameDate(int otherDate) {
        return this.date == otherDate;
    }

    public Optional<Integer> getDaysDifferenceIfEarlier(int otherDate) {
        if (this.date < otherDate) {
            return Optional.of(otherDate - this.date);
        } else {
            return Optional.empty();
        }
    }

    private static void validateDate(int date) {
        if (date < MIN_DATE || date > MAX_DATE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DATE.getMessage());
        }
    }
}
