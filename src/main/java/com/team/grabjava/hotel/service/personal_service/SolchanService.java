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


}

