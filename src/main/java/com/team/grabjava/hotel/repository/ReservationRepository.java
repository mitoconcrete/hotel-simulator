package com.team.grabjava.hotel.repository;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservationRepository {
    // 예약 리스트
    List<Reservation> reservationList = new ArrayList<>();


    // 예약리스트 조회(getReservationList)
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    // 예약을 생성해서 예약리스로 넣는다.(createReservation)
    public Reservation createReservation(Room room, String userName, String userPhone, String reservationDate) {
        // 생성자 생성과 동시에  reservationList에 넘갔다.
        Reservation reservation = new Reservation(room, userName, userPhone, reservationDate);
        reservationList.add(reservation);
        return reservation;
    }


}