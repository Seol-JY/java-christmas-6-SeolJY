package christmas.constants;

import java.util.Arrays;
import java.util.List;

public enum MenuCategory {
    APPETIZER(Arrays.asList(
            MenuInfo.MUSHROOM_SOUP,
            MenuInfo.TAPAS,
            MenuInfo.CAESAR_SALAD
    )),
    MAIN(Arrays.asList(
            MenuInfo.T_BONE_STEAK,
            MenuInfo.BBQ_RIBS,
            MenuInfo.SEAFOOD_PASTA,
            MenuInfo.CHRISTMAS_PASTA
    )),
    DESSERT(Arrays.asList(
            MenuInfo.CHOCOLATE_CAKE,
            MenuInfo.ICE_CREAM
    )),
    BEVERAGE(Arrays.asList(
            MenuInfo.ZERO_COLA,
            MenuInfo.RED_WINE,
            MenuInfo.CHAMPAGNE
    ));

    private final List<MenuInfo> menuList;

    MenuCategory(List<MenuInfo> menuList) {
        this.menuList = menuList;
    }

    public boolean containsMenu(MenuInfo menu) {
        return menuList.contains(menu);
    }

    public List<MenuInfo> getMenuList() {
        return menuList;
    }
}
