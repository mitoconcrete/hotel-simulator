package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

import java.util.List;

public class ReservationNumberSearchInterface {
    public void startScanner(){
        System.out.println("이름과 전화번호를 입력해주세요. :");
    }

    public void showExistReservationIdListMessage(List<String> reservationIdList){
        for(String reservationId : reservationIdList){
            System.out.println(reservationId);
        }
    }

    public void showNotExistReservationIdMessage(){
        System.out.println("예약번호가 존재하지 않습니다.");
    }
}
