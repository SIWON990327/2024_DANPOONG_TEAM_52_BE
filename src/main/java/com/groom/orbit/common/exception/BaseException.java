package com.groom.orbit.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends Exception {

    private BaseResponseStatus baseResponseStatus;
}
