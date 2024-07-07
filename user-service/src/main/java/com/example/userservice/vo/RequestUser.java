package com.example.userservice.vo;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class RequestUser {

    @NonNull
    private String email;
    private String name;
    private String pwd;

}
