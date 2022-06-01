package com.example.demo.src.matching;

import com.example.demo.src.matching.MatchingProvider;
import com.example.demo.src.matching.MatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.matching.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/matching")
public class MatchingController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MatchingProvider matchingProvider;
    @Autowired
    private final MatchingService matchingService;
    @Autowired
    private final JwtService jwtService;

    public MatchingController(MatchingProvider matchingProvider, MatchingService matchingService, JwtService jwtService) {
        this.matchingProvider = matchingProvider;
        this.matchingService = matchingService;
        this.jwtService = jwtService;
    }
}
