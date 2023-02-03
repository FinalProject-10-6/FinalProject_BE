package com.ggt.finalproject.dto;

import com.ggt.finalproject.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.ggt.finalproject.entity.User;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private User receiver;
    private NotificationType notificationType;
    private String content;
    private String url;


}
