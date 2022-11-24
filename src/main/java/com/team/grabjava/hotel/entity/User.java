package com.team.grabjava.hotel.entity;

public class User {
    private String userName;        // 멤버변수
    private String userPhone;
    private int userAsset;

    public User(String userName, String userPhone, int userAsset) {     // 생성자
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAsset = userAsset;
    }

    public String getUserName() {   // 고객의 이름 getter
        return userName;
    }

    public String getUserPhone() {  // 고객의 전화번호 getter
        return userPhone;
    }

    public int getUserAsset() {     // 고객의 소지금 getter
        return userAsset;
    }

    public void setUserAsset(int userAsset) {       // 고객의 소지금을 업데이트할 때 사용하는 setter
        this.userAsset = userAsset;
    }
}
