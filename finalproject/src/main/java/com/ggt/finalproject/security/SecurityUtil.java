package com.ggt.finalproject.security;

import com.ggt.finalproject.entity.User;
import com.ggt.finalproject.exception.CustomException;
import com.ggt.finalproject.exception.ErrorCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
public class SecurityUtil {

    public static User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOTFOUND_AUTHENTICATION);
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetailsImpl springSecurityUser = (UserDetailsImpl) authentication.getPrincipal();
            return springSecurityUser.getUser();
        } else {
            return null;
        }
    }

}
