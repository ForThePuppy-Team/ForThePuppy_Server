package com.example.demo.src.family.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFamilyAdd {
    private int familyIdx;
    private int userIdx;
    private String familyPassword;
}
