package couponapi.service;

import couponapi.controller.dto.CouponIssueRequestDto;
import couponcore.component.DistributeLockExecutor;
import couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final DistributeLockExecutor distributeLockExecutor;

    // executor의 runnable을 넘겨서 executor의 안쪽에서 이슈메서드가 실행되게 할거임
    public void issueRequestV1(CouponIssueRequestDto request) {
        couponIssueService.issue(request.getCouponId(), request.getUserId());
        log.info("쿠폰 발급 완료.");
    }
}
