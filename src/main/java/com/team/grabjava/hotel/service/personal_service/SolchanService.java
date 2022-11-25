package com.team.grabjava.hotel.service.personal_service;

import com.team.grabjava.hotel.repository.UserRepository;


public class SolchanService {
    // 1. 인자를 받아서, DB에서 List를 전달받아서, 해당되는 유저를 찾는다.(이미있는지 없는지) - getUserList
    // 2. 이미있다면, 이미있는 유저를 리턴해주고
    // 3. 없다면, 새로운 유저를 만들어달라고, DB에게 요청한다

    UserRepository userRepository = new UserRepository();

    // 사용자가 입력한 값(이름, 전화번호)을 db와 확인하는 매소드
    public boolean isFindUserService(String userName, String userPhone) {
        for (User user : userRepository.getUserList()) {
            if (userName.equals(user.getUserName()) && userPhone.equals(user.getUserPhone())) {
                return true;
            }
        }

        return false;
    }

    // 새로운 유저를 생성하는 매소드
    // 매개변수로 받은 userName, userPhone, userAsset 을 기존 유저리스트에 추가한다.
    public void createUserService(String userName, String userPhone, int userAsset) {
        userRepository.createUser(userName, userPhone, userAsset);
    }

    //  유저정보 : 소지금 업데이트 매소드
    //  매개변수르 이름과 전화번호를 받아 둘다 일치하는 유저db에 소지금을 업데이트시킨다.
    public void updateUserAssetService(String userName, String userPhone, String userAsset) {
        for (User user : userRepository.getUserList()) {
            // 이름, 전화번호가 모두 일치하는 유저리스트가 있다면
            if (userName.equals(user.getUserName() && userPhone.equals(user.getUserPhone))) {
                // 유저리스트 의 소지금을 변경하는 매서드를 실행해라.
                userRepository.setUserAsset(user, userAsset);
            }
        }
    }
}


