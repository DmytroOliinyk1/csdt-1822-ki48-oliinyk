package com.lpnu.virtual.library.core.user.controller;

import com.lpnu.virtual.library.core.user.model.UserDto;
import com.lpnu.virtual.library.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user-create";
    }

    @PostMapping("/create")
    public ModelAndView createUser(@ModelAttribute UserDto userDto, Model model) {
        userService.createUser(userDto);
        return new ModelAndView("redirect:/login");
    }
}
