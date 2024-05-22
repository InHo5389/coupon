package couponcore.model;

import couponcore.exception.CouponIssueException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CouponTest {

    @Test
    @DisplayName("발급 수량이 남아있다면 ture를 반환한다.")
    void availableIssueQuantity_1() {
        //given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .build();
        //when
        boolean result = coupon.availableIssueQuantity();
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("발급 수량이 남아 있지 않다면 false를 반환한다.")
    void availableIssueQuantity_2() {
        //given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .build();
        //when
        boolean result = coupon.availableIssueQuantity();
        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("발급 수량이 설정되어 있지 않다면 true를 반환한다.")
    void availableIssueQuantity_3() {
        //given
        Coupon coupon = Coupon.builder()
                .totalQuantity(null)
                .issuedQuantity(100)
                .build();
        //when
        boolean result = coupon.availableIssueQuantity();
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("발급 기한이 시작되지 않았다면 false를 반환한다..")
    void availableIssuedDate_1() {
        //given
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().plusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();
        //when
        boolean result = coupon.availableIssuedDate();
        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("발급 기한에 해당되면 true를 반환한다..")
    void availableIssuedDate_2() {
        //given
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();
        //when
        boolean result = coupon.availableIssuedDate();
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("발급 기한이 종료되면 false를 반환한다..")
    void availableIssuedDate_3() {
        //given
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().minusDays(1))
                .build();
        //when
        boolean result = coupon.availableIssuedDate();
        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("발급 수량과 발급 기간이 유효하다면 발급에 성공한다..")
    void issue_1() {
        //given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();
        //when
        coupon.issue();
        //then
        Assertions.assertThat(coupon.getIssuedQuantity()).isEqualTo(100);
    }

    @Test
    @DisplayName("발급 수량을 초과하면 예외를 반환한다")
    void issue_2() {
        //given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(101)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(2))
                .build();
        //when
        //then
        Assertions.assertThatThrownBy(coupon::issue)
                .isInstanceOf(CouponIssueException.class)
                .message().contains("[INVALID_COUPON_ISSUE_QUANTITY]");
    }

    @Test
    @DisplayName("발급 기한을 초과하면 예외를 반환한다")
    void issue_3() {
        //given
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().minusDays(1))
                .build();
        //when
        //then
        Assertions.assertThatThrownBy(coupon::issue)
                .isInstanceOf(CouponIssueException.class)
                .message().contains("[INVALID_COUPON_ISSUE_DATE]");
    }
}