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
    FoodWorldCup findByPostId(Long postId);
    Page<FoodWorldCup> findAllByNumOrderByPointDesc(Pageable pageable, String num);
}
