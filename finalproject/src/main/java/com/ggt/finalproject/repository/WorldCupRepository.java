package com.ggt.finalproject.repository;

import com.ggt.finalproject.entity.FoodWorldCup;
import com.ggt.finalproject.entity.Post;
import com.ggt.finalproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldCupRepository extends JpaRepository<FoodWorldCup, Long> {
    boolean existsByPostIdAndNum(Long postId, String num);
    // num 월드컵 회차에 postId가 있는지 확인함

    boolean existsByNum(String num);
    // num 월드컵 회차가 있는지 확인함

    FoodWorldCup findByPostId(Long postId);
    // postId를 찾아옴

    Page<FoodWorldCup> findAllByNumOrderByPointDesc(Pageable pageable, String num);
    // num 회차에 포인트 높은 순으로 모두 가져옴

    List<FoodWorldCup> findByNumOrderByPointDesc(String num);
    // num 회차에 포인트 높은 순으로 모두 가져옴

    List<FoodWorldCup> findTop2ByNumOrderByPointDesc(String defalut);

}
