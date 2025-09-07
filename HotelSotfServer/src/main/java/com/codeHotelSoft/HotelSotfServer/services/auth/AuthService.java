package com.codeHotelSoft.HotelSotfServer.services.auth;

import com.codeHotelSoft.HotelSotfServer.dto.SignupRequest;
import com.codeHotelSoft.HotelSotfServer.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

}
