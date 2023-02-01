package com.ggt.finalproject.scheduler;

import com.ggt.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final PostService postService;


    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 1 * * * *")
    public void worldcupNum() throws InterruptedException {
        log.info("음식월드컵 다음회차 변경");
        postService.worldcupNum();
    }
}