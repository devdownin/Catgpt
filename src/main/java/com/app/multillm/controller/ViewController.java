package com.app.multillm.controller;

import com.app.multillm.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final HistoryService historyService;

    @GetMapping("/")
    public String index() {
        return "chat";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("conversations", historyService.getAllConversations());
        return "history";
    }
}
