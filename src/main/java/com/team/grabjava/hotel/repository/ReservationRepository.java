package com.team.grabjava.hotel.repository;

import com.team.grabjava.hotel.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    // 예약 리스트
    List<Reservation> reservationList = new ArrayList<>();


    // 예약리스트 조회(getReservationList)
    public List<Reservation> getReservationList(){
        return reservationList;
    }

}
