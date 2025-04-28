package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

public interface UserService {

    User save (UserDTO userDTO);
    void InitDatabase();
}
