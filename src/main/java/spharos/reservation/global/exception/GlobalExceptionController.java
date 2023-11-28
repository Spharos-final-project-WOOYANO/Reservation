package spharos.reservation.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spharos.reservation.global.common.response.ErrorResponse;
import spharos.reservation.reservations.presentation.ReservationController;

@Slf4j
@RestControllerAdvice("spharos.reservation.reservations.presentation")
public class GlobalExceptionController {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<?> customExHandle(CustomException e) {
        log.error("[exceptionHandle] ex", e);
        return ErrorResponse.toResponseEntity(e.getResponseCode());
    }

}