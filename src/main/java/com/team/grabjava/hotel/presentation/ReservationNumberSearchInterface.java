package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

import java.util.List;

public class ReservationNumberSearchInterface {
    public void run(){
        System.out.println("이름과 전화번호를 입력해주세요.");
        System.out.println("(이름 전화번호) :");
    }

    public void existReservationNumber(List<Reservation> reservations){
        for(Reservation reservation : reservations){
            System.out.println(reservation);
        }
    }

    public void noExistReservationNumber(){
        System.out.println("예약번호가 존재하지 않습니다.");
    }
}
