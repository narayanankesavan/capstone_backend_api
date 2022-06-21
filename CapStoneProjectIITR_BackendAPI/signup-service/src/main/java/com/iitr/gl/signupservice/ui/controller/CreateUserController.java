package com.iitr.gl.signupservice.ui.controller;

import com.iitr.gl.signupservice.service.SignupService;
import com.iitr.gl.signupservice.shared.UserDto;
import com.iitr.gl.signupservice.ui.model.CreateUserRequestModel;
import com.iitr.gl.signupservice.ui.model.CreateUserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class CreateUserController {

    @Autowired
    SignupService signupService;

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetail) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetail, UserDto.class);

        UserDto createdUser = signupService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdUser, CreateUserResponseModel.class));

    }
}
