package com.team.grabjava.hotel.presentation;

public class FailResultLogPresentation {
    public void showFailReservationMessage(){
        System.out.println("이미 예약된 객실입니다. 예약에 실패하였습니다.");
    }
    public void showFailCancelReservationMessage(){
        System.out.println("예약 취소에 실패하였습니다.");
    }
}
