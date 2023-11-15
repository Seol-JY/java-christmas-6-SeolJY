package christmas.domain;

import christmas.constants.ErrorMessage;
import christmas.constants.MenuCategory;
import christmas.constants.MenuInfo;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

public final class OrderItems {
    private static final int MAX_TOTAL_ITEM_COUNT = 20;
    private final EnumMap<MenuInfo, Integer> items;

    private OrderItems(EnumMap<MenuInfo, Integer> items) {
        this.items = new EnumMap<>(items);
    }

    public static OrderItems create() {
        return new OrderItems(new EnumMap<>(MenuInfo.class));
    }

    public OrderItems addItem(MenuInfo menu, int quantity) {
        validateDuplicate(menu);
        validateQuantity(quantity);
        validateTotalItemCount(quantity);

        EnumMap<MenuInfo, Integer> newItems = new EnumMap<>(items);
        newItems.put(menu, quantity);

        return new OrderItems(newItems);
    }

    public boolean containsOnlyBeverages() {
        return items.keySet().stream().allMatch(MenuCategory.BEVERAGE::containsMenu);
    }

    public Map<MenuInfo, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public int getTotalCountForCategory(MenuCategory category) {
        return items.entrySet().stream()
                .filter(entry -> category.containsMenu(entry.getKey()))
                .mapToInt(Entry::getValue)
                .sum();
    }

    private void validateDuplicate(MenuInfo menu) {
        if (items.containsKey(menu)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    private void validateTotalItemCount(int quantity) {
        if (getTotalItemCount() + quantity > MAX_TOTAL_ITEM_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    private int getTotalItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
}

