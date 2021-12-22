package com.lpnu.virtual.library.core.admin.controller;

import com.lpnu.virtual.library.core.admin.model.FieldCreateDto;
import com.lpnu.virtual.library.core.admin.service.AdminActionService;
import com.lpnu.virtual.library.metadata.field.service.FieldManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminActionService service;
    private final FieldManagerService fieldManagerService;

    @GetMapping("/field/create")
    public String createFieldGet(Model model) {
        model.addAttribute("dto", new FieldCreateDto());
        return "field-create";
    }

    @PostMapping("/field/create")
    public String createFieldPost(@ModelAttribute FieldCreateDto dto, Model model) {
        String result = service.createField(dto);
        model.addAttribute("msg", result);
        return "field-create";
    }

    @GetMapping("/field/config")
    public String getFieldConfig(Model model) {
        model.addAttribute("config", fieldManagerService.getAllFieldsConfig());
        return "field-config";
    }
}
