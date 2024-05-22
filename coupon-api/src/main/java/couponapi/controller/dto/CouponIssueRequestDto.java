package couponapi.controller.dto;

import lombok.Getter;

@Getter
public class CouponIssueRequestDto {
    private long userId;
    private long couponId;
}
