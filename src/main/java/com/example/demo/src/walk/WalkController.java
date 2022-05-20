package com.example.demo.src.walk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/walks")
public class WalkController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WalkProvider walkProvider;
    @Autowired
    private final WalkService walkService;
    @Autowired
    private final JwtService jwtService;

    public WalkController(WalkProvider walkProvider, WalkService walkService, JwtService jwtService) {
        this.walkProvider = walkProvider;
        this.walkService = walkService;
        this.jwtService = jwtService;
    }
}
