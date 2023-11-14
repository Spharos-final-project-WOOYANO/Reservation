package shparos.reservation.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import shparos.reservation.global.common.response.ResponseCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

}
