package com.example.projectBackEnd.service.impl;

import com.example.projectBackEnd.constant.CommonMsg;
import com.example.projectBackEnd.dto.ItemsDto;
import com.example.projectBackEnd.dto.UserDto;
import com.example.projectBackEnd.entity.Items;
import com.example.projectBackEnd.entity.User;
import com.example.projectBackEnd.repo.UserRepo;
import com.example.projectBackEnd.service.UserService;
import com.example.projectBackEnd.util.CommonValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    private User castUserDtoToEntity(UserDto userDto){
        User user=new User();
        user.setUserName(userDto.getUserName());
        user.setAddress(userDto.getAddress());
        user.setEmail(user.getEmail());
        user.setTel(user.getTel());
        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto castUserEntityToDto(User user){
        UserDto userDto=new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setId(String.valueOf(user.getId()));
        userDto.setAddress(user.getAddress());
        userDto.setTel(user.getTel());
        userDto.setImage(userDto.getImage());
        return userDto;
    }

    public boolean isUserNameExists(String userName) {
        Optional<User> user = userRepo.findByUserName(userName);
        return user.isPresent();
    }
    public boolean isEmailExists(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.isPresent();
    }
    private List<String> userValidation(UserDto userDto) {
        List<String> validationList = new ArrayList<>();
        if (CommonValidation.stringNullValidation(userDto.getUserName())) {
            validationList.add(CommonMsg.EMPTY_USERNAME);
        }
        if(isUserNameExists(userDto.getUserName())){
            validationList.add(CommonMsg.USERNAME_IS_ALREADY_EXITED);
        }
        if (CommonValidation.stringNullValidation(userDto.getAddress())) {
            validationList.add(CommonMsg.EMPTY_ADDRESS);
        }
        if (CommonValidation.stringNullValidation(userDto.getPassword())) {
            validationList.add(CommonMsg.EMPTY_PASSWORD);
        }
        if (CommonValidation.stringNullValidation(userDto.getEmail())) {
            validationList.add(CommonMsg.EMPTY_EMAIL);
        }
        if (CommonValidation.stringNullValidation(userDto.getTel())) {
            validationList.add(CommonMsg.EMPTY_CONTACT_NUMBER);
        }
        return validationList;
    }
}
