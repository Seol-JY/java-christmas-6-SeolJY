package christmas.utils;

import christmas.constants.ErrorMessage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Parser {
    private static final String ORDER_ITEMS_DELIMITER = ",";
    private static final String ORDER_ITEM_DELIMITER = "-";
    private static final int SPLITTED_ORDER_ITEM_SIZE = 2;

    private Parser() {
    }

    public static int parseDate(String userInput) {
        return tryParseIntAsOptional(userInput).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.INVALID_DATE.getMessage()));
    }

    public static List<String> parseOrderItems(String userInput) {
        List<String> splitUserInput = List.of(userInput.split(ORDER_ITEMS_DELIMITER));

        return splitUserInput.stream()
                .map(String::trim)
                .toList();
    }

    public static String parseMenuAtOrderItem(String orderItem) {
        return parseOrderItem(orderItem).get(0);
    }

    public static int parseQuantityAtOrderItem(String orderItem) {
        return tryParseIntAsOptional(parseOrderItem(orderItem).get(1))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage()));
    }

    private static List<String> parseOrderItem(String userInput) {
        List<String> splitUserInput = Arrays.asList(userInput.split(ORDER_ITEM_DELIMITER));
        if (splitUserInput.size() != SPLITTED_ORDER_ITEM_SIZE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
        return splitUserInput.stream().map(String::trim).toList();
    }

    private static Optional<Integer> tryParseIntAsOptional(String userInput) {
        try {
            return Optional.of(Integer.parseInt(userInput));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
