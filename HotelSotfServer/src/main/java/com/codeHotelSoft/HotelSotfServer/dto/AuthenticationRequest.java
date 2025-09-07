package com.codeHotelSoft.HotelSotfServer.dto;


import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;
}
