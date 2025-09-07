package com.codeHotelSoft.HotelSotfServer.dto;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class RoomsResponseDto {

    private List<RoomDto> roomsDtoList;


    private Integer totalPages;


    private Integer pageNumber;



}
