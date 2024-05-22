package couponapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CouponIssueResponseDto {
    private boolean isSuccess;
    private String comment;
}
