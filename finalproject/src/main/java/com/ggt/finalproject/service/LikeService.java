package com.ggt.finalproject.service;


import com.ggt.finalproject.dto.LikeResponseDto;
import com.ggt.finalproject.dto.MsgResponseDto;
import com.ggt.finalproject.entity.LikePost;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import com.ggt.finalproject.repository.LikePostRepository;
import com.ggt.finalproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikePostRepository likePostRepository;
    private final PostRepository postRepository;

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
            return LikeResponseDto.success("좋아요 추가", "true");
        } else {
            LikePost likePost = likePostRepository.findByPostAndUser(post, user);
            likePostRepository.delete(likePost);
            post.setLikePostSum(post.getLikePostSum() - 1);
            return LikeResponseDto.success("좋아요 취소","false");
        }
    }

}
