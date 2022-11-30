package com.team.grabjava.hotel;

import com.team.grabjava.hotel.entity.Reservation;
import com.team.grabjava.hotel.entity.Room;
import com.team.grabjava.hotel.presentation.*;
import com.team.grabjava.hotel.service.HotelService;

import java.util.List;
import java.util.Scanner;

public class JavaHotelApplication {
    public static void main(String[] args) {
        // Interface
        SystemLogPresentation systemLogPresentation = new SystemLogPresentation();
        ApplicationLogPresentation applicationLogPresentation = new ApplicationLogPresentation();

        SimpleLogPresentation simpleLogPresentation = new SimpleLogPresentation();

        FailResultLogPresentation failResultLogPresentation = new FailResultLogPresentation();
        SuccessResultLogPresentation successResultLogPresentation = new SuccessResultLogPresentation();

        NotExistLogPresentation notExistLogPresentation = new NotExistLogPresentation();
        NotMatchLogPresentation notMatchLogPresentation = new NotMatchLogPresentation();

        RequestInputLogPresentation requestInputLogPresentation = new RequestInputLogPresentation();
        SelectableMenuLogPresentation selectableMenuLogPresentation = new SelectableMenuLogPresentation();
        GetServiceResponseLogPresentation getServiceResponseLogPresentation = new GetServiceResponseLogPresentation();



        // Service
        HotelService hotelService = new HotelService();

        // Scanner
        Scanner input = new Scanner(System.in);

        // Program
        returnToStartMenu : while(true){
            boolean isAdminMode = false;
            boolean isInStartMenu = true;

            // 시작메뉴 + (관리자메뉴)
            while (isInStartMenu){
                selectableMenuLogPresentation.showSelectableStartMenu();
                if(isAdminMode){
                    selectableMenuLogPresentation.showSelectableHiddenMenu();
                }
                requestInputLogPresentation.showRequestMenuNumberMessage();
                String startMenuSelectCommand = input.nextLine();
                switch (startMenuSelectCommand){
                    case "1":   // 메인 메뉴. (호텔입장하기)
                        isInStartMenu = false;
                        break;
                    case "2":   // 시스템 종료.
                        systemLogPresentation.showSystemExitMessage();
                        System.exit(0);
                        break;
                    case "3":  // 관리자모드1. 모든 예약내역 보여주기
                    case "4":  // 관리자모드2. 호텔 보유자산 보여주기
                        // 해당 커멘드는 관리자 모드에서만 동작하도록 만들기
                        if(isAdminMode){
                            isAdminMode = false;
                            switch (startMenuSelectCommand){
                                case "3":
                                    List<Reservation> reservationList = hotelService.getHotelReservationList();
                                    if(reservationList.size() == 0){
                                        notExistLogPresentation.showNotExistReservationMessage();
                                    }else{
                                        getServiceResponseLogPresentation.showGetHotelAllReservationList(reservationList);
                                    }
                                    continue;
                                case "4":
                                    int hotelCurrentAsset = hotelService.getHotelAsset();
                                    getServiceResponseLogPresentation.showGetHotelAsset(hotelCurrentAsset);
                                    continue;
                            }
                        }
                    default:
                            // 이외 커멘드가 들어오면, 커멘드로 관리자모드 진입여부를 판단하여, 다음사이클에서의 액션을 결정.
                            isAdminMode = hotelService.validateAdminPassword(startMenuSelectCommand);
                            if(!isAdminMode){
                                notMatchLogPresentation.showNotMatchCommandMessage();
                            }
                }
            }

            // 호텔 입장
            simpleLogPresentation.showSimpleRequestUserInfoMessage();

            // 유저 정보를 입력 : 이름, 휴대전화번호, 자산
            requestInputLogPresentation.showRequestUserNameMessage();
            String userName = input.nextLine();
            if(userName.equals("")){
                notMatchLogPresentation.showNotMatchStringFormatMessage();
                continue;
            }

            requestInputLogPresentation.showRequestUserPhoneMessage();
            String userPhone = input.nextLine();
            boolean isValidPhoneNumber = hotelService.validatePhoneNumber(userPhone);
            if(!isValidPhoneNumber) {
                notMatchLogPresentation.showNotMatchPhoneNumberFormatMessage();
                continue;
            }

            int userAsset;
            try {
                requestInputLogPresentation.showRequestUserAssetMessage();
                userAsset = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("잘못된 형식의 숫자입니다.");
                continue;
            }

            // 유저가 기존 디비에 있는지 여부를 판단하여, 업데이트 할 지, 새로 생성 할 지를 결정합니다.
            boolean hasUserInDB = hotelService.validateUserDataInDB(userName, userPhone);
            if(hasUserInDB){
                hotelService.putUserAsset(userName, userPhone, userAsset);
            }else{
                hotelService.postNewUser(userName, userPhone, userAsset);
            }

            // 메인메뉴 진입.
            while (true){
                selectableMenuLogPresentation.showSelectableMainMenu();
                requestInputLogPresentation.showRequestMenuNumberMessage();
                String selectInput = input.nextLine();
                switch (selectInput){
                    case "1":  // 예약
                        // 정규식을 이용한 날짜 포맷 검증 및 예약 범위 검증
                        requestInputLogPresentation.showRequestDateTimeMessage();
                        String reservationRequestDate = input.nextLine();
                        boolean isValidDate = hotelService.validateDateFormat(reservationRequestDate);
                        if(!isValidDate){
                            notMatchLogPresentation.showNotMatchDateTimeFormatMessage();
                            continue;
                        }

                        // 예약 가능한 방정보를 받아와 있는 경우에만 예약을 진행하기.
                        List<Room> roomList = hotelService.getBookableRoomList(reservationRequestDate);
                        if(roomList.size() == 0){
                            notExistLogPresentation.showNotExistEmptyRoomMessage();
                        }else{
                            getServiceResponseLogPresentation.showGetHotelRoomListMessage(roomList);
                            requestInputLogPresentation.showRequestRoomNumberMessage();
                            int selectedRoomNo;
                            try {
                                selectedRoomNo = Integer.parseInt(input.nextLine());
                            }catch(NumberFormatException e){
                                continue;
                            }
                            // 예약 요청 시, 소지금 여부를 체크하여 케이스에 맞는 액션을 취합니다.
                            String reservationResponse = hotelService.requestReservation(selectedRoomNo, userName, userPhone, reservationRequestDate);
                            switch (reservationResponse){
                                case "잔액부족":
                                    notExistLogPresentation.showNotExistRemainMessage();
                                    continue;
                                case "예약실패":
                                    failResultLogPresentation.showFailReservationMessage();
                                    continue;
                                default:  // 예약성공
                                    successResultLogPresentation.showSuccessReservationMessage(reservationResponse);
                                    break;
                            }
                            continue;
                        }
                    case "2":  // 예약번호 조회 : 유저이름, 유저전화번호를 이용
                        requestInputLogPresentation.showRequestUserNameMessage();
                        String searchUserName = input.nextLine();
                        if(searchUserName.equals("")){
                            notMatchLogPresentation.showNotMatchStringFormatMessage();
                            continue;
                        }

                        requestInputLogPresentation.showRequestUserPhoneMessage();
                        String searchUserPhone = input.nextLine();
                        isValidPhoneNumber = hotelService.validatePhoneNumber(searchUserPhone);
                        if(!isValidPhoneNumber) {
                            notMatchLogPresentation.showNotMatchPhoneNumberFormatMessage();
                            continue;
                        }

                        // 예약번호 리스트를 가져와 유무에 따라 각기 다른 화면을 보여주기
                        List<String> reservationIdList = hotelService.getReservationIdList(searchUserName, searchUserPhone);
                        if(reservationIdList.size() == 0){
                            notExistLogPresentation.showNotExistReservationIdMessage();
                        }else {
                            getServiceResponseLogPresentation.showGetUserAllReservationIdList(reservationIdList);
                        }
                        continue;
                    case "3":  // 예약내역 조회 : 예약번호를 이용
                        // 예약번호를 입력
                        requestInputLogPresentation.showRequestReservationIdMessage();
                        String reservationId = input.nextLine();

                        // 예약번호에 따라 내역이 있는지 유무를 판별하여, 그에 맞는 결과를 보여줍니다.
                        String reservationApiResponse = hotelService.getReservationContent(reservationId);
                        if(reservationApiResponse.equals("")){
                            notExistLogPresentation.showNotExistReservationMessage();
                        }else{
                            getServiceResponseLogPresentation.showGetUserReservationInfoMessage(reservationApiResponse);
                        }
                        continue;
                    case "4":  // 예약 취소 : 예약번호를 이용
                        // 예약번호를 입력받아
                        requestInputLogPresentation.showRequestReservationIdMessage();
                        reservationId = input.nextLine();

                        // 검증합니다.
                        reservationApiResponse = hotelService.getReservationContent(reservationId);
                        if(reservationApiResponse.equals("")){
                            notExistLogPresentation.showNotExistReservationMessage();
                            continue;
                        }else{
                            getServiceResponseLogPresentation.showGetUserReservationInfoMessage(reservationApiResponse);
                            requestInputLogPresentation.showRequestCancelCommandMessage();
                        }

                        // 삭제여부를 재확인합니다.
                        String cancelConfirmCommand = input.nextLine();
                        switch (cancelConfirmCommand){
                            case "Y":  // 삭제
                                boolean cancelReservationResponse =  hotelService.deleteReservationById(reservationId);
                                if (cancelReservationResponse){
                                    successResultLogPresentation.showSuccessCancelReservationMessage();
                                }else{
                                    failResultLogPresentation.showFailCancelReservationMessage();
                                }
                                break;
                            case "N": // 삭제취소
                                failResultLogPresentation.showFailCancelReservationMessage();
                                break;
                            default:
                                notMatchLogPresentation.showNotMatchCommandMessage();
                        }
                        continue;
                    case "5":  // 호텔 나가기
                        applicationLogPresentation.showApplicationExitMainMenuMessage();
                        continue returnToStartMenu;
                    default:   // 이외 커멘드 입력 시 처리
                        notMatchLogPresentation.showNotMatchMenuNumberMessage();
                }
            }
        }
    }
}


