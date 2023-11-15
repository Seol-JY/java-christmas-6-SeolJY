package christmas.domain;

import christmas.constants.ErrorMessage;
import java.util.Objects;
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
        return !isWeekend();
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

    public int getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DecemberDate that = (DecemberDate) o;
        return date == that.date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
