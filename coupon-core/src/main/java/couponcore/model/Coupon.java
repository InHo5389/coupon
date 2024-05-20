package couponcore.model;

import couponcore.exception.CouponIssueException;
import couponcore.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static couponcore.exception.ErrorCode.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private CouponType couponType;

    private Integer totalQuantity;

    @Column(nullable = false)
    private int issuedQuantity;

    @Column(nullable = false)
    private int discountAmount;

    @Column(nullable = false)
    private int minAvailableAmount;

    @Column(nullable = false)
    private LocalDateTime dateIssueStart;

    @Column(nullable = false)
    private LocalDateTime dateIssueEnd;

    public boolean availableIssueQuantity() {
        if (totalQuantity == null) {
            return true;
        }
        return totalQuantity > issuedQuantity;
    }

    public boolean availableIssuedDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }

    public void issue() {
        if (!availableIssueQuantity()){
            throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY
                    ,"발급 가능한 수량을 초과합니다. total : %s, issued: %s".formatted(totalQuantity,issuedQuantity));
        }
        if (!availableIssuedDate()){
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE
                    ,"발급 기한이 아닙니다. request : %s, issuedStart: %s, issuedEnd: %s".formatted(LocalDateTime.now(),dateIssueStart,dateIssueEnd));
        }
        issuedQuantity++;
    }
}
