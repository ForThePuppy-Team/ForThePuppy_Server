package com.example.demo.src.puppy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.puppy.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/puppies")
public class PuppyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PuppyProvider puppyProvider;
    @Autowired
    private final PuppyService puppyService;
    @Autowired
    private final JwtService jwtService;

    public PuppyController(PuppyProvider puppyProvider, PuppyService puppyService, JwtService jwtService) {
        this.puppyProvider = puppyProvider;
        this.puppyService = puppyService;
        this.jwtService = jwtService;
    }
}
