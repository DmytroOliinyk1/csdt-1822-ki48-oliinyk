package com.lpnu.virtual.library.common.utils;

import com.lpnu.virtual.library.common.model.SessionContext;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;

@UtilityClass
public class SessionUtils {
    public static SessionContext getSessionContextForUser() {
        return SessionContext.builder()
                .isAuthorized(UserUtils.isAuthorized())
                .isAdmin(UserUtils.isAdmin())
                .build();
    }

    public static void setContextForModel(Model model) {
        model.addAttribute("sc", getSessionContextForUser());

    }
}
