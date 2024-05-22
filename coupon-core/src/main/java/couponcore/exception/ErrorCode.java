package couponcore.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_COUPON_ISSUE_QUANTITY("발급 가능한 수량을 초과합니다."),
    INVALID_COUPON_ISSUE_DATE("발급 기한이 아닙니다."),
    COUPON_NOT_EXIST("해당 쿠폰이 없습니다."),
    DUPLICATED_COUPON_ISSUE("이미 발급된 쿠폰입니다.");

    private final String message;
}
