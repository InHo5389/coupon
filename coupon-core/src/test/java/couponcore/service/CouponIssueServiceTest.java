package couponcore.service;

import couponcore.TestConfig;
import couponcore.exception.CouponIssueException;
import couponcore.model.Coupon;
import couponcore.model.CouponIssue;
import couponcore.model.CouponType;
import couponcore.repository.mysql.CouponIssueJpaRepository;
import couponcore.repository.mysql.CouponIssueRepository;
import couponcore.repository.mysql.CouponJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class CouponIssueServiceTest extends TestConfig {

    @Autowired
    private CouponIssueService couponIssueService;

    @Autowired
    private CouponIssueJpaRepository couponIssueJpaRepository;

    @Autowired
    private CouponIssueRepository couponIssueRepository;

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @BeforeEach
    public void clean(){
        couponJpaRepository.deleteAllInBatch();
        couponIssueJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("쿠폰 발급 내역이 존재한다면 예외를 반환한다.")
    void saveCouponIssue_1(){
        //given
        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(1L)
                .userId(1L)
                .build();
        couponIssueJpaRepository.save(couponIssue);
        //when
        //then
        assertThatThrownBy(()->couponIssueService.saveCouponIssue(1L,1L))
                .isInstanceOf(CouponIssueException.class)
                .message().contains("DUPLICATED_COUPON_ISSUE");
    }

    @Test
    @DisplayName("쿠폰 발급 내역이 존재하지 않는 경우 쿠폰을 발급한다..")
    void saveCouponIssue_2(){
        //given
        //when
        CouponIssue couponIssue = couponIssueService.saveCouponIssue(1L, 1L);
        CouponIssue result = couponIssueJpaRepository.save(couponIssue);
        //then
        assertThat(couponIssueJpaRepository.findById(result.getId()).isPresent()).isTrue();
    }

    @Test
    @DisplayName("발급 수량, 기한 ,중복 발급 문제가 없다면 쿠폰을 발급한다.")
    void issue_1(){
        //given
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠펀")
                .totalQuantity(100)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        //when
        couponIssueService.issue(coupon.getId(),userId);
        //then
        Coupon couponResult = couponJpaRepository.findById(coupon.getId()).get();
        assertThat(couponResult.getIssuedQuantity()).isEqualTo(1);

        CouponIssue couponIssue = couponIssueRepository.findFirstCouponIssue(coupon.getId(), userId);
        assertThat(couponIssue).isNotNull();
    }

    @Test
    @DisplayName("발급 수량에 문제가 있다면 예외를 반환한다.")
    void issue_2(){
        //given
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠펀")
                .totalQuantity(100)
                .issuedQuantity(100)
                .dateIssueStart(LocalDateTime.now().minusDays(1))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        //when
        //then
        Assertions.assertThatThrownBy(()->couponIssueService.issue(coupon.getId(),userId))
                .isInstanceOf(CouponIssueException.class)
                .message().contains("[INVALID_COUPON_ISSUE_QUANTITY]");
    }

    @Test
    @DisplayName("발급 기한 문제가 있다면 예외를 반환한다.")
    void issue_3(){
        //given
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠펀")
                .totalQuantity(100)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().minusDays(1))
                .build();
        couponJpaRepository.save(coupon);
        //when
        //then
        Assertions.assertThatThrownBy(()->couponIssueService.issue(coupon.getId(),userId))
                .isInstanceOf(CouponIssueException.class)
                .message().contains("[INVALID_COUPON_ISSUE_DATE]");
    }

    @Test
    @DisplayName("이미 발급된 쿠폰이라면 예외를 반환한다.")
    void issue_4(){
        //given
        long userId = 1;
        Coupon coupon = Coupon.builder()
                .couponType(CouponType.FIRST_COME_FIRST_SERVED)
                .title("선착순 테스트 쿠펀")
                .totalQuantity(100)
                .issuedQuantity(0)
                .dateIssueStart(LocalDateTime.now().minusDays(2))
                .dateIssueEnd(LocalDateTime.now().plusDays(1))
                .build();
        couponJpaRepository.save(coupon);

        CouponIssue couponIssue = CouponIssue.builder()
                .couponId(coupon.getId())
                .userId(userId)
                .build();
        couponIssueJpaRepository.save(couponIssue);
        //when
        //then
        Assertions.assertThatThrownBy(()->couponIssueService.issue(coupon.getId(),userId))
                .isInstanceOf(CouponIssueException.class)
                .message().contains("[DUPLICATED_COUPON_ISSUE]");
    }

    @Test
    @DisplayName("쿠폰이 존재하지 않으면 예외를 반환한다.")
    void issue_5(){
        //given
        long userId = 1;
        long couponId = 1;
        //when
        //then
        Assertions.assertThatThrownBy(()->couponIssueService.issue(couponId,userId))
                .isInstanceOf(CouponIssueException.class)
                .message().contains("[COUPON_NOT_EXIST]");
    }


}