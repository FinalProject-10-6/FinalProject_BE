package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.EmailCodeRequestDto;
import com.ggt.finalproject.dto.EmailDto;
import com.ggt.finalproject.dto.FindPwRequestDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.entity.EmailCode;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.EmailCodeRepository;
import com.ggt.finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ggt.finalproject.entity.User;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@PropertySource("classpath:application.properties")
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailCodeRepository emailCodeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //인증번호 생성
//    private final String ePw = createKey();

    @Value("${spring.mail.username}")
    private String id;

//    public MimeMessage createMessage(String to)throws MessagingException, UnsupportedEncodingException {
//        log.info("보내는 대상 : "+ to);
//        log.info("인증 번호 : " + ePw);
//        MimeMessage  message = javaMailSender.createMimeMessage();
//
//        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
//        message.setSubject("꿀통 회원가입 인증 코드: "); //메일 제목
//
//        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
//        String msg="";
//        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
//        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
//        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
//        msg += ePw;
//        msg += "</td></tr></tbody></table></div>";
//
//        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
//        message.setFrom(new InternetAddress(id,"꿀통대장")); //보내는 사람의 메일 주소, 보내는 사람 이름
//
//        return message;
//    }

    // 인증코드 만들기
//    public static String createKey() {
//        StringBuffer key = new StringBuffer();
//        Random rnd = new Random();
//
//        for (int i = 0; i < 6; i++) { // 인증코드 6자리
//            key.append((rnd.nextInt(10)));
//        }
//        return key.toString();
//    }

    /*
        메일 발송
        sendSimpleMessage의 매개변수로 들어온 to는 인증번호를 받을 메일주소
        MimeMessage 객체 안에 내가 전송할 메일의 내용을 담아준다.
        bean으로 등록해둔 javaMailSender 객체를 사용하여 이메일 send
     */
    public MsgResponseDto sendSimpleMessage(EmailDto emailDto)throws MessagingException, UnsupportedEncodingException {

//        StringBuffer key = new StringBuffer();
//        Random rnd = new Random();
//
//        for (int i = 0; i < 6; i++) { // 인증코드 6자리
//            key.append((rnd.nextInt(10)));
//        }
//        String ePw = key.toString();
//인증코드 영어 + 숫자
        String ePw = "";
        for(int i = 0; i < 6 ; i++) {
            char a = (char) (Math.random() * 42 + 49);
            if (a >= 58 && a <= 64) {
                i--;
            } else {
                ePw += String.valueOf(a);
            }
        }
        ////////////////////////////
        log.info("보내는 대상 : "+ emailDto.getEmail());
        log.info("인증 번호 : " + ePw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, emailDto.getEmail()); // to 보내는 대상
        message.setSubject("꿀통 회원가입 인증 코드: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"꿀통대장")); //보내는 사람의 메일 주소, 보내는 사람 이름


//        EmailCode emailCode = new EmailCode(emailDto.getEmail(), ePw);
        //DB에 인증코드 저장ㅣ
        if(emailCodeRepository.existsByEmail(emailDto.getEmail())){
            EmailCode emailCode = emailCodeRepository.findByEmail(emailDto.getEmail());
            emailCode.update(emailDto.getEmail(),ePw);
            emailCodeRepository.save(emailCode);

        } else {
            EmailCode emailCode = new EmailCode(emailDto.getEmail(), ePw);
            emailCode = emailCodeRepository.save(emailCode);
        }


//        String to = emailDto.getEmail();
//        MimeMessage message = createMessage(to);
        try{
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return MsgResponseDto.success("이메일 전송 완료");
    }

    public MsgResponseDto mailCheck(EmailCodeRequestDto emailCodeRequestDto) {
        String email = emailCodeRequestDto.getEmail();
        String code = emailCodeRequestDto.getEmailCode();


        EmailCode emailCode = emailCodeRepository.findByEmail(email);

        LocalDateTime overTime = emailCode.getModifiedAt().plusSeconds(180); //인증번호 발송 기준 3분뒤 시간

        LocalDateTime currentTime = LocalDateTime.now(); //현재 시간

        Boolean over = currentTime.isAfter(overTime); //3분뒤 시간이 현재시간 이후인지 확인하는 메소드

        if(!emailCode.getEmailCode().matches(code)){ //이메일이 일치하지 않을 경우 에러
            throw new CustomException(ErrorCode.WRONG_EMAIL_CODE);
        } else if(over){ //코드는 일치하지만 시간이 오버 될 경우 에러
            throw new CustomException(ErrorCode.OVERTIME_EMAIL);
        } else {
                emailCodeRepository.deleteById(emailCode.getId());
                return MsgResponseDto.success("이메일 인증 완료");
            }
        }

    @Transactional
    public MsgResponseDto findPw(FindPwRequestDto requestDto) throws MessagingException, UnsupportedEncodingException {
        String loginId = requestDto.getLoginId();
        String email = requestDto.getEmail();

        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new CustomException(ErrorCode.WRONG_ID)
        );

        if(!user.getEmail().equals(email)){
            throw new CustomException(ErrorCode.WRONG_EMAIL);
        }

        //랜덤 패스워드 생성
        String nPw = "";
        for(int i = 0; i < 10 ; i++) {
            char a = (char) (Math.random() * 42 + 49);
            if (a >= 58 && a <= 64) {
                i--;
            } else {
                nPw += String.valueOf(a);
            }
        }

        //////////메일로 전송
        log.info("보내는 대상 : "+ requestDto.getEmail());
        log.info("임시 비밀번호 : " + nPw);
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, requestDto.getEmail()); // to 보내는 대상
        message.setSubject("꿀통 회원 임시 비밀번호: "); //메일 제목

        // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">임시 비밀번호 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 임시 비밀번호로 로그인 후 비밀번호를 재설정 해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += nPw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"꿀통대장")); //보내는 사람의 메일 주소, 보내는 사람 이름

        //임시 비밀번호 인코딩
        String secretPw = passwordEncoder.encode(nPw);

        //디비에 저장
        user.updatePw(secretPw);
        userRepository.save(user);

        try{
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return MsgResponseDto.success("임시비밀번호 전송 완료");

    }
}
