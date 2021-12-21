package com.lpnu.virtual.library.common.controller;

import com.lpnu.virtual.library.core.user.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VirtualLibraryController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("isAdmin", UserUtils.isAdmin());
        model.addAttribute("isAuthorized", UserUtils.isAuthorized());
        return "index";
    }

    @GetMapping()
    public ModelAndView index(Model model) {
        return new ModelAndView("redirect:/welcome");
    }
}
