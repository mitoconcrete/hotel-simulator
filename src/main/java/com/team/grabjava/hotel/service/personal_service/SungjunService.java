package com.team.grabjava.hotel.service.personal_service;

import com.team.grabjava.hotel.entity.Hotel;
import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.repository.HotelRepository;
import com.team.grabjava.hotel.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SungjunService {
    private HotelRepository hotelRepository = new HotelRepository();
    private ReservationRepository reservationRepository = new ReservationRepository();

    public List<Room> getReservationableRoomList(String date){
        List<Room> list = new ArrayList<>();

        for(int i=0;i<hotelRepository.getRoomList().size();i++){
            list.add(hotelRepository.getRoomList().get(i));
        }

        for(Room room : hotelRepository.getRoomList()) {
            for (Reservation reservation : reservationRepository.getReservationList()) {
                String reservationDate = reservation.getReservationDate().substring(0, 10);
                if (reservationDate.equals(date) && reservation.getRoomNo()==room.getRoomNo()) {
                    list.remove(room);
                    break;
                }
            }
        }

        return list;
    }

    public boolean checkDateFormat(String date) {
        return Pattern.matches("^20\\d{2}-(?:0[0-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9]|3[0-1])$", date);
    }

    public String getReservationContent(String reservationId) {
        String reservationContent = "";
        for (Reservation r : reservationRepository.getReservationList()) {
            if (r.getId().equals(reservationId)) {
                reservationContent += r.getId() + " " + r.getReservationDate() + " " + r.getRoom().getRoomSize();
            }
        }
        return reservationContent;
    }

    public boolean requestReservationCancel(String reservationId){
        for (Reservation r : reservationRepository.getReservationList()) {
            if(r.getId().equals(reservationId)){
                reservationRepository.deleteReservation(r);
                return true;
            }
        }
        return false;
    }

    public boolean phoneNumberValidation(String phoneNumber){
        return Pattern.matches("^01(?:0|1|[6-9])-\\d{4}-\\d{4}$", phoneNumber);
    }

}
