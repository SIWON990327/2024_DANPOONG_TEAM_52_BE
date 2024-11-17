package com.groom.orbit.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {
    SUCCESS(HttpStatus.CREATED, "요청에 성공하였습니다."),
    NO_PAGE(HttpStatus.BAD_REQUEST, "존재하지 않는 페이지 입니다"),
    NO_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저 입니다"),
    ;

    private HttpStatus httpStatus;
    private String message;

}
