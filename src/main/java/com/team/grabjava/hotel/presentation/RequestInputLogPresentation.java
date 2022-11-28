package com.team.grabjava.hotel.presentation;

public class RequestInputLogPresentation {
    public void showRequestUserNameMessage(){
        System.out.println("이름 :");
    }
    public void showRequestUserPhoneMessage(){
        System.out.println("전화번호 :");
    }
    public void showRequestUserAssetMessage(){
        System.out.println("소지금 :");
    }
    public void showRequestReservationIdMessage() { System.out.println("예약번호를 입력해주세요 :");}
    public void showRequestMenuNumberMessage(){
        System.out.println("원하시는 메뉴번호를 입력해 주세요 :");
    }
    public void showRequestDateTimeMessage(){
        System.out.println("오늘부터 일주일 내에 원하는 날짜를 다음의 포맷에 맞게 입력해주세요.(0000-00-00) :");
    }
    public void showRequestRoomNumberMessage(){System.out.println("예약을 원하시는 객실의 번호를 입력해주세요. :");}
    public void showRequestCancelCommandMessage(){ System.out.println("정말로 예약을 취소하시겠습니까? (Y/N)");}
}
