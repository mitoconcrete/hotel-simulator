package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

public class ReservationSearchInterface {
    public void startScanner(){
        System.out.println("예약번호를 입력해주세요 :");
    }

    public void showReservationInfoMessage(Reservation reservation){
        System.out.println("예약내역");
        System.out.println(reservation.getReservationId() + " / " + reservation.getReservationDate() + " / " + reservation.getRoom().getSize() + "평");
    }

    public void showNotExistReservationMessage(){
        System.out.println("예약번호에 해당되는 내역이 없습니다.");
    }
}
