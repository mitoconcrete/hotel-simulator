package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

public class ReservationCancelInterface {
    public void startScanner(){
        System.out.println("예약번호를 입력해주세요 :");
    }

    public void showCancelReservationMessage(Reservation reservation){
        System.out.println(reservation.getId() + " / " + reservation.getReservationDate() + " / " + reservation.getRoom().getSize() + "평");
        System.out.println("정말로 예약을 취소하시겠습니까? (Y/N)");
    }

    public void showSuccessCancelReservationMessage(){
        System.out.println("예약이 성공적으로 취소되었습니다.");
    }

    public void showFailCancelReservationMessage(){
        System.out.println("예약에 실패하였습니다.");
        System.out.println("이전메뉴로 되돌아갑니다.");
    }

    public void showCommandErrorMessage(){
        System.out.println("잘못된 커멘드입니다.");
        System.out.println("이전메뉴로 되돌아갑니다.");
    }

    public void showInvalidReservationIdMessage(){
        System.out.println("예약번호에 해당되는 내역이 없습니다.");
    }
}
