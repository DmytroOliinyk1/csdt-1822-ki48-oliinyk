package com.lpnu.virtual.library.common.controller;

import com.lpnu.virtual.library.common.utils.SessionUtils;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class VirtualLibraryController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        SessionUtils.setContextForModel(model);
        return "index";
    }

    @GetMapping()
    public ModelAndView index(Model model) {
        return new ModelAndView("redirect:/welcome");
    }

    @GetMapping("do-logout")
    public ModelAndView logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("redirect:/welcome");
    }
}
