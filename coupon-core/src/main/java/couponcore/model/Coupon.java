package couponcore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends BaseEntity{

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
}