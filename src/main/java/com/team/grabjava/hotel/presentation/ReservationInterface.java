package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

public class ReservationInterface {
    public void startScanner(){
        System.out.println("오늘부터 일주일 내에 원하는 날짜를 다음의 포맷에 맞게 입력해주세요.(0000-00-00) :");
    }

    public void showOutOfDateRangeMessage(){
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().plusDays(6).toString();
        System.out.println("예약 범위가 아닙니다." + startDate + "~" + endDate + "내의 날짜를 입력해주세요. :");
    }

    public void showNoEmptyRoomMessage(){
        System.out.println("현재 비어있는 객실이 없습니다.");
    }

    public void showHasEmptyRoomMessage(List<Room> roomList){
        for(Room room : roomList){
            System.out.println(room.getRoomNo() + " / " + room.getSize()+"평" + " / " + room.getPrice() + "원");
        }
        System.out.println("예약을 원하시는 객실의 번호를 입력해주세요. :");
    }

    public void showNoMoneyReservationMessage(){
        System.out.println("금액이 부족하여, 예약에 실패했습니다.");
    }

    public void showSuccessReservationMessage(String reservationId){
        System.out.println("예약에 성공했습니다.");
        System.out.println("예약번호는 " + reservationId + " 입니다.");
    }

    public void showAlreadyReservationMessage(){
        System.out.println("이미 예약된 객실입니다. 예약에 실패했습니다.");
    }
}
