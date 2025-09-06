package com.retailease.billingsystem.service;

import java.util.List;

import com.retailease.billingsystem.io.UserRequest;
import com.retailease.billingsystem.io.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}

