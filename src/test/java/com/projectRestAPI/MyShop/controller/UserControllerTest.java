package com.projectRestAPI.MyShop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectRestAPI.MyShop.dto.request.RolesRequest;
import com.projectRestAPI.MyShop.dto.request.UserRequest;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.Roles;
import com.projectRestAPI.MyShop.model.UserImage;
import com.projectRestAPI.MyShop.model.Users;
import com.projectRestAPI.MyShop.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    private UserRequest userRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws ParseException {
        objectMapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = sdf.parse("2025/07/24");
        userRequest = UserRequest.builder()
                .id(1L)
                .fullName("Nguyễn Văn A")
                .birthday(birthday)
                .gender(true)
                .address("123 Đường ABC, Hà Nội")
                .email("nguyenvana@example.com")
                .phone("0123456789")
                .username("nguyenvana")
                .password("123456")
                .typeLogin(0)
                .roles(List.of(RolesRequest.builder().name("USER").build()))
                .build();
    }

    @Test
    public void testAddUser_Success() throws Exception {
        // Tạo JSON của userRequest
        String userRequestJson = objectMapper.writeValueAsString(userRequest);

        // Giả lập file ảnh
        MockMultipartFile file = new MockMultipartFile(
                "file",                  // tên tham số requestPart
                "avatar.jpg",            // tên file giả lập
                MediaType.IMAGE_JPEG_VALUE,
                "Fake image content".getBytes()
        );

        // Giả lập phần JSON request
        MockMultipartFile userJsonPart = new MockMultipartFile(
                "userRequest",          // tên requestPart
                "",
                MediaType.APPLICATION_JSON_VALUE,
                userRequestJson.getBytes()
        );

        ResponseObject responseObject = new ResponseObject(
                "200",
                "Đăng ký thành công. Vui lòng kiểm tra email để xác thực OTP.",
                1,
                userRequest.getEmail()
        );

        Mockito.when(usersService.addUser(Mockito.any(UserRequest.class), Mockito.any()))
                .thenReturn(ResponseEntity.ok(responseObject));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/add")
                        .file(userJsonPart)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Đăng ký thành công. Vui lòng kiểm tra email để xác thực OTP."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(userRequest.getEmail()));
    }
}
