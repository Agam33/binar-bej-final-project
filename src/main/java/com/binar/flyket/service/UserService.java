package com.binar.flyket.service;

import com.binar.flyket.dto.model.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserDTO findById(String userId);

    UserDTO findByEmail(String email);

    UserDTO register(UserDTO userDTO);

    UserDTO updateProfile(String email, UserDTO userDTO);

    UserDTO deleteByEmail(String email);

    Boolean uploadImage(String email, MultipartFile file) throws IOException;
}
