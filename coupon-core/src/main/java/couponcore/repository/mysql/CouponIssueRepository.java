package couponcore.repository.mysql;

import com.querydsl.jpa.JPQLQueryFactory;
import couponcore.model.CouponIssue;
import couponcore.model.QCouponIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static couponcore.model.QCouponIssue.*;

// querydsl 적용
@Repository
@RequiredArgsConstructor
public class CouponIssueRepository {

    private final JPQLQueryFactory queryFactory;

    public CouponIssue findFirstCouponIssue(long couponId,long userId){
        return queryFactory.selectFrom(couponIssue)
                .where(couponIssue.couponId.eq(couponId))
                .where(couponIssue.userId.eq(userId))
                .fetchFirst();
    }
}
