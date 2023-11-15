package christmas.domain.handler;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.Badge;
import christmas.constants.MenuInfo;
import christmas.domain.DecemberDate;
import christmas.domain.Order;
import christmas.domain.OrderItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadgePromotionHandlerTest {
    private BadgePromotionHandler badgePromotionHandler;
    private Order order;

    @BeforeEach
    void setUp() {
        badgePromotionHandler = new BadgePromotionHandler();
        order = Order.of(DecemberDate.from(1), OrderItems.create().addItem(MenuInfo.T_BONE_STEAK, 1));
    }

    @Test
    @DisplayName("[성공] 주문 금액이 SANTA_BADGE_THRESHOLD 이상이면 SANTA 배지가 설정된다.")
    void testBadgePromotionHandlerWithSantaBadge() {
        order.addBenefits(new DummyPromotionHandler(), 20_000);

        badgePromotionHandler.process(order);

        assertThat(order.getBadge()).contains(Badge.SANTA);
    }

    @Test
    @DisplayName("[성공] 주문 금액이 TREE_BADGE_THRESHOLD 이상이면 TREE 배지가 설정된다.")
    void testBadgePromotionHandlerWithTreeBadge() {
        order.addBenefits(new DummyPromotionHandler(), 10_000);

        badgePromotionHandler.process(order);

        assertThat(order.getBadge()).contains(Badge.TREE);
    }

    @Test
    @DisplayName("[성공] 주문 금액이 STAR_BADGE_THRESHOLD 이상이면 STAR 배지가 설정된다.")
    void testBadgePromotionHandlerWithStarBadge() {
        order.addBenefits(new DummyPromotionHandler(), 5_000);

        badgePromotionHandler.process(order);

        assertThat(order.getBadge()).contains(Badge.STAR);
    }

    @Test
    @DisplayName("[성공] 주문 금액이 모든 배지 기준에 미달하는 경우 배지가 설정되지 않는다.")
    void testBadgePromotionHandlerWithNoBadge() {
        order.addBenefits(new DummyPromotionHandler(), 4_000);

        badgePromotionHandler.process(order);

        assertThat(order.getBadge()).isEmpty();
    }

    private static class DummyPromotionHandler extends PromotionHandler {
        @Override
        protected void process(Order order) {
            order.addBenefits(this, 1);
        }

        @Override
        public String toString() {
            return null;
        }
    }
}