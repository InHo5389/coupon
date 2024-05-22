package couponcore.exception;

import lombok.Getter;

@Getter
public class CouponIssueException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public CouponIssueException(ErrorCode errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "[%s] %s".formatted(errorCode,message);
    }
}
