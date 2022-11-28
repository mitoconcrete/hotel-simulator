package com.team.grabjava.hotel.presentation;

import java.time.LocalDate;

public class NotMatchLogPresentation {
    public void showNotMatchPasswordMessage(){
        System.out.println("비밀번호가 일치하지 않습니다.");
        System.out.println("메인메뉴로 돌아갑니다.");
    }

    public void showNotMatchPhoneNumberFormatMessage(){
        System.out.println("전화번호 입력을 잘못하셨습니다. 01X-XXXX-XXXX 형식으로 입력해주세요");
    }

    public void showNotMatchDateTimeFormatMessage(){
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().plusDays(6).toString();
        System.out.println("예약 범위가 아닙니다." + startDate + "~" + endDate + "내의 날짜를 입력해주세요. :");
    }

    public void showNotMatchCommandMessage(){
        System.out.println("잘못된 커멘드입니다.");
    }

    public void showNotMatchMenuNumberMessage(){
        System.out.println("1-5 내의 숫자를 입력해주세요.");
    }

    public void showNotMatchStringFormatMessage(){
        System.out.println("공백이 불가합니다.");
    }
}
