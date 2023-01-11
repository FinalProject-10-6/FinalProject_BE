package com.ggt.finalproject.controller;

import com.ggt.finalproject.dto.EmailCodeRequestDto;
import com.ggt.finalproject.dto.EmailDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"Email API"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class EmailController {

    private final EmailService emailService;

    @ApiOperation(value = "이메일 인증코드 발송")
    @PostMapping("/emailCheck")
    @ResponseBody
    public MsgResponseDto mailConfirm(@RequestBody @Valid EmailDto emailDto) throws Exception {
//        String code = emailService.sendSimpleMessage(emailDto);
//        log.info("인증코드 : " + code);
        return emailService.sendSimpleMessage(emailDto);
    }

    @ApiOperation(value = "인증코드 체크")
    @PostMapping("/emailCode")
    @ResponseBody
    public MsgResponseDto mailCheck(@RequestBody EmailCodeRequestDto emailCodeRequestDto){
        return emailService.mailCheck(emailCodeRequestDto);
    }
}
