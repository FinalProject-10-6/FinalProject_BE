package com.ggt.finalproject.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamped {

    // 생성시간
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    // 황크시간
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;
}
