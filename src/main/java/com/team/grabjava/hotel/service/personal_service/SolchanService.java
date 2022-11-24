package com.team.grabjava.hotel.service.personal_service;

public class SolchanService {
    private String userName;
    private String userPhone;
    private int userAsset;

    public SolchanService(String userName, String userPhone, int userAsset){
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAsset = userAsset;
    }

    // 클라이언트가 입력한 유저 정보를 확인하여 맞으면 DB로 보내고 틀리면 오류값을 클라이언트로 보내는 메서드?
    public boolean inputUserData(){
        // front 정보 입력 요청 -> 클라이언트 정보 입력 -> 입력한 정보는 여기로 와서 조건을 거쳐 다음 절차로 진행?
        // 이름이 비었거나 , 소지금을 음수로 입력했다면
        if(userName == " " || userAsset <= 0){
            return false;
        }else{
            return true;
        }
    }
}
