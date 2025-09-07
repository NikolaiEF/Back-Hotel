package com.codeHotelSoft.HotelSotfServer.services.customer.room;

import com.codeHotelSoft.HotelSotfServer.dto.RoomsResponseDto;

public interface RoomService {

    RoomsResponseDto getAvailableRooms(int pageNumber);

}
