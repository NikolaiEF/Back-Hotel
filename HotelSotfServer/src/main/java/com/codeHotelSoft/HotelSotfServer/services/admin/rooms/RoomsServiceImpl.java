package com.codeHotelSoft.HotelSotfServer.services.admin.rooms;


import com.codeHotelSoft.HotelSotfServer.dto.RoomDto;
import com.codeHotelSoft.HotelSotfServer.dto.RoomsResponseDto;
import com.codeHotelSoft.HotelSotfServer.entity.Room;
import com.codeHotelSoft.HotelSotfServer.repository.RoomRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {


    private final RoomRepository roomRepository;


    public boolean postRoom(RoomDto roomDto) {
        try{
            Room room = new Room();


            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setPrice(roomDto.getPrice());
            room.setAvailable(true);


            roomRepository.save(room);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public RoomsResponseDto getAllRooms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomsResponseDto roomsResponseDto = new RoomsResponseDto();
        roomsResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponseDto.setTotalPages(roomPage.getTotalPages());
        roomsResponseDto.setRoomsDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomsResponseDto;


    }

    public RoomDto getRoomById(Long id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            return optionalRoom.get().getRoomDto();
        }else{
            throw new EntityNotFoundException("No se encontro la habitación");
        }
    }


    public boolean updateRoom(Long id, RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            Room existingRoom = optionalRoom.get();

            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());

            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

    public void deleteRoom(Long id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if(optionalRoom.isPresent()){
            roomRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("La habitación no existe");
        }
    }

}
