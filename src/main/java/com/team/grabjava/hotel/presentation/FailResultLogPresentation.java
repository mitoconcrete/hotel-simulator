package com.team.grabjava.hotel.presentation;

public class FailResultLogPresentation {
    public void showFailReservationMessage(){
        System.out.println("입력하신 방번호를 다시 확인해주세요. 예약에 실패하였습니다.");
    }
    public void showFailCancelReservationMessage(){
        System.out.println("예약 취소에 실패하였습니다.");
    }
}
