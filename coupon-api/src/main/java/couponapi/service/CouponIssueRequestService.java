package couponapi.service;

import couponapi.controller.dto.CouponIssueRequestDto;
import couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;

    public void issueRequestV1(CouponIssueRequestDto request){
        couponIssueService.issue(request.getCouponId(), request.getUserId());
        log.info("쿠폰 발급 완료.");
    }
}
