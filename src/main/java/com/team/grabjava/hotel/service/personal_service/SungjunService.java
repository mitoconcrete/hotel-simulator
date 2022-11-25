package com.team.grabjava.hotel.service.personal_service;

import com.team.grabjava.hotel.entity.Hotel;
import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.repository.HotelRepository;
import com.team.grabjava.hotel.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SungjunService {
    private HotelRepository hotelRepository = new HotelRepository();
    private ReservationRepository reservationRepository = new ReservationRepository();

    public List<Room> getRoomList(String date){
        List<Room> list = new ArrayList<>();

        for(int i=0;i<hotelRepository.getRoomList().size();i++){
            list.add(hotelRepository.getRoomList().get(i));
        }

        for(Room room : hotelRepository.getRoomList()) {
            for (Reservation reservation : reservationRepository.getReservationList()) {
                String reservationDate = reservation.getReservationDate().toString().substring(0, 10);
                if (reservationDate.equals(date) && reservation.getRoomNo()==room.getRoomNo()) {
                    list.remove(room);
                    break;
                }
            }
        }

        return list;
    }

    boolean checkDateFormat(String date) {
        if (date.length() == 10 && date.contains("-")) {
            date = date.replace("-", "");
            if (date.length() == 8) {
                for (int i = 0; i < 8; i++) {
                    if ((int) date.charAt(i) > 57 || (int) date.charAt(i) < 48) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Reservation getReservation(String reservationId){
        Reservation reservation = null;
        for (Reservation r : reservationRepository.getReservationList()) {
            if(r.getId().equals(reservationId)){
                reservation = r;
            }
        }
        return reservation;
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

}
