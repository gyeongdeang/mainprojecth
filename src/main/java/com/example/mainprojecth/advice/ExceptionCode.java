package com.example.mainprojecth.advice;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),

    EMAIL_NOT_FOUND(404, "Email not found"),
    PASSWORD_NOT_FOUND(404, "Password wrong"),
    MEMBER_EXISTS(409, "Member exists");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}

