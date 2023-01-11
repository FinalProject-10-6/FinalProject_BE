package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.EmailCodeRequestDto;
import com.ggt.finalproject.dto.EmailDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/emailCheck")
    @ResponseBody
    public String mailConfirm(@RequestBody @Valid EmailDto emailDto) throws Exception {
        String code = emailService.sendSimpleMessage(emailDto);
        log.info("인증코드 : " + code);
        return code;
    }

    @PostMapping("/emailCode")
    @ResponseBody
    public MsgResponseDto mailCheck(@RequestBody EmailCodeRequestDto emailCodeRequestDto){
        return emailService.mailCheck(emailCodeRequestDto);
    }
}
