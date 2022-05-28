package com.example.demo.src.family;

import com.example.demo.src.family.FamilyProvider;
import com.example.demo.src.family.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.family.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/families")
public class FamilyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FamilyProvider familyProvider;
    @Autowired
    private final FamilyService familyService;
    @Autowired
    private final JwtService jwtService;

    public FamilyController(FamilyProvider familyProvider, FamilyService familyService, JwtService jwtService){
        this.familyProvider = familyProvider;
        this.familyService = familyService;
        this.jwtService = jwtService;
    }
}
