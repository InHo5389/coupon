package couponapi.controller;

import couponapi.controller.dto.CouponIssueRequestDto;
import couponapi.controller.dto.CouponIssueResponseDto;
import couponapi.service.CouponIssueRequestService;
import couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponIssueController {

    private final CouponIssueRequestService couponIssueRequestService;

    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issueV1(@RequestBody CouponIssueRequestDto body){
        couponIssueRequestService.issueRequestV1(body);
        return new CouponIssueResponseDto(true,null);
    }
}
