package com.cbiko.ecommerce.dto.user;

public class SignInResponseDto {
    private String status;
    private int userId;

    public SignInResponseDto(String status, int userId, String message) {
        this.status = status;
        this.userId = userId;
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignInResponseDto(String status, int userId) {
        this.status = status;
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
