package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificationDto {


    private Long id;

    private String content;

    private String url;

    private Boolean status;


    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getContent(),
                notification.getUrl(), notification.getIsRead());
    }
}
