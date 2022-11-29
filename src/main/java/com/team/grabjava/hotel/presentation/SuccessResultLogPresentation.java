package com.team.grabjava.hotel.presentation;

public class SuccessResultLogPresentation {
    public void showSuccessReservationMessage(String reservationId){
        System.out.println("예약에 성공했습니다.");
        System.out.println("예약번호는 " + reservationId + " 입니다.");
    }
    public void showSuccessCancelReservationMessage(){
        System.out.println("예약이 성공적으로 취소되었습니다.");
    }
}
