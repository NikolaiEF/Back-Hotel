package com.codeHotelSoft.HotelSotfServer.dto;


import com.codeHotelSoft.HotelSotfServer.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

private String jwt;

private Long userId;

private UserRole userRole;
}
