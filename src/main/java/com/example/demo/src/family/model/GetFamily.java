package com.example.demo.src.family.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFamily {
    private int familyIdx;
    private String createDate;
    private int userIdx;
    private List<familyMember> familyMemberList;
}
