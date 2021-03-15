package org.upgrad.upstac.auth.models;

import lombok.Data;

@Data
public class LoginResponse {


    private String userName;
    private String message;
    private String token;

    public LoginResponse(){

    }
    public LoginResponse(String userName, String message, String token) {
        super();
        this.userName = userName;
        this.message = message;
        this.token = token;
    }
}
