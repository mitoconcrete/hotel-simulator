package com.team.grabjava.hotel.presentation;

import com.team.grabjava.hotel.entity.Reservation;

public class UserInfoInterface {
    public void startScanner(){
        System.out.println("유저정보를 입력해주세요.");
    }

    public void showInputUserNameMessage(){
        System.out.println("이름 :");
    }

    public void showInputUserPhoneMessage(){
        System.out.println("전화번호 :");
    }

    public void showInputUserAssetMessage(){
        System.out.println("소지금 :");
    }
}
