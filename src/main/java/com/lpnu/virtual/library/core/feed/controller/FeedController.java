package com.lpnu.virtual.library.core.feed.controller;

import com.lpnu.virtual.library.core.feed.service.FeedService;
import com.lpnu.virtual.library.core.user.util.UserUtils;
import com.lpnu.virtual.library.util.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/asset/feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @GetMapping()
    public String getFeed(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String searchId,
            Model model) {
        model.addAttribute("result",
                feedService.startSearch(PaginationUtils.createPagination(page, searchId)));
        model.addAttribute("authorized", UserUtils.isAuthorized());
        return "all-asset";
    }
}
