package com.ggt.finalproject.service;


import com.ggt.finalproject.dto.LikeResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.entity.LikePost;
import com.ggt.finalproject.entity.NotificationType;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.LikePostRepository;
import com.ggt.finalproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    @Transactional
    public LikeResponseDto likePost(Long postid, User user) {
        // 게시글이 존재하는지 확인
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new CustomException(ErrorCode.NOTFOUND_POST)
        );

        if (likePostRepository.findByPostAndUser(post, user) == null) {
            LikePost likePost = new LikePost(post, user);
            likePostRepository.save(likePost);
            post.setLikePostSum(post.getLikePostSum() + 1);

            //해당 게시글로 이동하는 url
//            String Url = "https://tom-jelly.shop/api/post/"+post.getId();
            String Url = "/"+post.getCategory()+"List/"+post.getCategory()+"/"+"detail/"+post.getId();
            //댓글 생성 시 모집글 작성 유저에게 실시간 알림 전송 ,
            String content = post.getUser().getNickname()+"님! "+user.getNickname()+"님이 게시글에 좋아요을 눌렀습니다!";

            //본인의 게시글에 좋아요을 남길때는 알림을 보낼 필요가 없다.
            if(!Objects.equals(user.getId(), post.getUser().getId())) {
                notificationService.send(post.getUser(), NotificationType.REPLY, content, Url);
            }

            return LikeResponseDto.success("좋아요 추가", "true",post.getLikePostSum());
        } else {
            LikePost likePost = likePostRepository.findByPostAndUser(post, user);
            likePostRepository.delete(likePost);
            post.setLikePostSum(post.getLikePostSum() - 1);
            return LikeResponseDto.success("좋아요 취소","false",post.getLikePostSum());
        }
    }

}
