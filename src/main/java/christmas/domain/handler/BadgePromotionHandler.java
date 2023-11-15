package christmas.domain.handler;

import christmas.domain.Order;

public class BadgePromotionHandler extends PromotionHandler {
    private static final String PROMOTION_NAME = "배지 제공";
    private static final int STAR_BADGE_THRESHOLD = 5_000;
    private static final int TREE_BADGE_THRESHOLD = 10_000;
    private static final int SANTA_BADGE_THRESHOLD = 20_000;

    @Override
    protected void process(Order order) {
        order.getBenefitPrice();
    }
//    총혜택 금액에 따라 다른 이벤트 배지를 부여합니다. 이 배지는 2024 새해 이벤트에서 활용할 예정입니다. 배지에 따라 새해 이벤트 참여 시, 각각 다른 새해 선물을 증정할 예정입니다.
//5천 원 이상: 별
//1만 원 이상: 트리
//2만 원 이상: 산타

    @Override
    public String toString() {
        return PROMOTION_NAME;
    }

}
