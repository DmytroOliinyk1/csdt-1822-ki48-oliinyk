package com.lpnu.virtual.library.core.asset.controller;

import com.lpnu.virtual.library.core.asset.service.AssetSubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetSubscribeController {

    public final AssetSubscribeService assetSubscribeService;

    @GetMapping("/subscribe")
    public RedirectView subscribe(@RequestParam Long id, @RequestParam String prevUrl ,Model model) {
        assetSubscribeService.subscribe(id);
        return new RedirectView(prevUrl);
    }

    @GetMapping("/unsubscribe")
    public RedirectView  unsubscribe(@RequestParam Long id, @RequestParam String prevUrl, Model model) {
        assetSubscribeService.unsubscribe(id);
        return new RedirectView(prevUrl);
    }
}
