package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

import java.util.List;

public class GetAllRervationsInterface {

    public void showPasswordNotMatchMessage(){
        System.out.println("비밀번호가 일치하지 않습니다.");
        System.out.println("메인메뉴로 돌아갑니다.");
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
