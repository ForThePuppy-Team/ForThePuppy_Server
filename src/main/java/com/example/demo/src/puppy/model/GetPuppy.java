package com.example.demo.src.puppy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPuppy {
    private int puppyIdx;
    private String puppyName;
    private String puppyPhoto;
    private String puppyGender;
    private String breed;
    private String birth;
    private int neutering;
    private int Vaccination;
    private String character;
}
