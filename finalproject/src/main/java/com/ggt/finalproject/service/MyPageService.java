package com.ggt.finalproject.service;

import com.ggt.finalproject.dto.*;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.ScrapPost;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.PostRepository;
import com.ggt.finalproject.repository.ScrapPostRepository;
import com.ggt.finalproject.repository.UserRepository;
import com.ggt.finalproject.security.UserDetailsImpl;
import com.ggt.finalproject.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;

    private final AWSS3Service awss3Service;

    private final PasswordEncoder passwordEncoder;

    // 마이페이지 포스트 가져오기위해 의존성 주입
    private final PostRepository postRepository;
    private final ScrapPostRepository scrapPostRepository;



    @Transactional
    public ResponseEntity<?> getMyPage(User user) {
        return ResponseEntity.ok(new MyPageDto(user));
    }


    @Transactional
    public MyPageResponseDto updateMyPage(List<MultipartFile> multipartFileList, String nickname, User user) throws IOException {

        // 프로필 사진 업로드
        String profileImg = null;


        if (!multipartFileList.get(0).isEmpty()){
            //프로필 사진만 바꿀경우 닉네임은 그대로 유지, 아닐경우 중복검사
            if(nickname.equals("")){
                nickname=user.getNickname();
            } else {
                Optional<User> found = userRepository.findByNickname(nickname);
                if(found.isPresent()) {
                    throw new CustomException(ErrorCode.NOT_CHECK_NICKNAME);
                }
            }
            profileImg = awss3Service.upload(multipartFileList.get(0), "profile");
            MyPageDto myPageDto = new MyPageDto(nickname, profileImg);
            user.updateMyPage(myPageDto);
            userRepository.save(user);
//            return ResponseEntity.ok(new MyPageDto(user));
            return new MyPageResponseDto(user.getNickname(), user.getProfileImg(),"정보 수정완료!", HttpStatus.OK.value());
        }

        //프로필 사진만 바꿀경우 닉네임은 그대로 유지
        if(nickname.equals("")){
            nickname=user.getNickname();
        } else {
            Optional<User> found = userRepository.findByNickname(nickname);
            if(found.isPresent()) {
                throw new CustomException(ErrorCode.NOT_CHECK_NICKNAME);
            }
        }
        profileImg = user.getProfileImg();
        MyPageDto myPageDto = new MyPageDto(nickname, profileImg);
        user.updateMyPage(myPageDto);
        userRepository.save(user);
//        return ResponseEntity.ok(new MyPageDto(user));
        return new MyPageResponseDto(user.getNickname(), user.getProfileImg(),"정보 수정완료!", HttpStatus.OK.value());
    }


    @Transactional
    public MsgResponseDto deleteUser (String loginId, User user) {
//        userRepository.findByLoginId(loginId).orElseThrow(
//                () -> new CustomException(ErrorCode.WRONG_ID)
//        );
        if (user.getLoginId().equals(loginId)) {
            user.softDelete();
            userRepository.save(user);
        } else {
            throw new CustomException(ErrorCode.WRONG_ID);
        }
        return MsgResponseDto.success("그동안 서비스를 이용해 주셔서 감사합니다.");
    }


    @Transactional
    public MsgResponseDto checkPW(PasswordRequestDto requestDto, UserDetailsImpl userDetails) {
        String password = requestDto.getPassword();

        if (!passwordEncoder.matches(password, userDetails.getUser().getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        } else {
            return MsgResponseDto.success("비밀번호가 확인되었습니다.");
        }
    }


    @Transactional
    public MsgResponseDto changePW(PasswordRequestDto requestDto, User user){

        String password = requestDto.getPassword();

        String secretPw = passwordEncoder.encode(password);
        user.changePassword(secretPw);
        userRepository.save(user);
        return MsgResponseDto.success("변경 성공");
    }




    @Transactional
    public MsgResponseDto socialSetting (String nickname, User user){

//        MyPageDto myPageDto = new MyPageDto(nickname, user.getLoginId(), user.getPassword());
////        User user = new User(nickname, loginId, password, email);
//        user.socialUpdate(myPageDto);
//        SocialSetResponseDto socialSetResponseDto = new SocialSetResponseDto(nickname, user.getLoginId(), user.getEmail());
        Optional<User> found = userRepository.findByNicknameAndUserStatus(nickname, true);
        if(found.isPresent()) {
            throw new CustomException(ErrorCode.NOT_CHECK_NICKNAME);

        }
        user.socialUpdate(nickname);
        userRepository.save(user);
        return MsgResponseDto.success("정보 업데이트 완료");
    }

    //  상정 마이페이지 내 포스트 가져오기
    @Transactional
    public List<MyPostRepsonseDto> myPost (User user, int pageNum) {
        Long myPostCount = postRepository.countByUserAndPostStatus(user, true);
        Long myScrapCount = scrapPostRepository.countByUser(user);
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<Post> posts = postRepository.findAllByUserAndPostStatusOrderByCreatedAtDesc(pageable, user, true);
        List<MyPostRepsonseDto> myPostList = new ArrayList<>();
        for(Post post : posts) {
            myPostList.add(new MyPostRepsonseDto(post, myPostCount, myScrapCount));
        }
        return myPostList;
    }

    // 상정 마이페이지 내 스크랩 가져오기
    @Transactional
    public List<MyPostRepsonseDto> myScrap (User user, int pageNum) {
        Long myPostCount = postRepository.countByUserAndPostStatus(user, true);
        Long myScrapCount = scrapPostRepository.countByUser(user);
        Pageable pageable = PageRequest.of(pageNum, 10);
        Page<ScrapPost> posts = scrapPostRepository.findAllByUser(pageable, user);
        List<MyPostRepsonseDto> myScrapList = new ArrayList<>();
        for(ScrapPost scrapPost : posts) {
            myScrapList.add(new MyPostRepsonseDto(scrapPost, myPostCount, myScrapCount));
        }
        return myScrapList;
    }

    // 상정 마이페이지 내 게시글, 스크랩 총 갯수 반환하기
    @Transactional
    public MypageCountDto mypageCount (User user) {
        Long myScrap = scrapPostRepository.countByUser(user);
        Long myPost = postRepository.countByUserAndPostStatus(user, true);
        MypageCountDto mypageCountDto = new MypageCountDto(myPost, myScrap);
        System.out.println(mypageCountDto);
        return mypageCountDto;
    }
}
