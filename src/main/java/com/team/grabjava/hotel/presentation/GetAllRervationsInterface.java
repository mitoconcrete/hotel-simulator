package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

import java.util.List;

public class GetAllRervationsInterface {
    public void startScanner(){
        System.out.println("관리자 비밀번호를 입력하세요. :");
    }

    public void showAllReservationsMessage(List<Reservation> reservations){
        for(Reservation reservation: reservations){
            System.out.println(reservation.getReservationId() + " / " + reservation.getRoom().getRoomNo() + " / " + reservation.getUserName() + " / " + reservation.getUserPhone() + " / " + reservation.getReservationDate());
        }
    }

    public void showNotExistReservationsMessage(){
        System.out.println("예약내역이 없습니다.");
    }
}
