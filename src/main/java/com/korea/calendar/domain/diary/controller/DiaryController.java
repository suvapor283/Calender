package com.korea.calendar.domain.diary.controller;

import com.korea.calendar.domain.diary.entity.Diary;
import com.korea.calendar.domain.diary.form.DiaryForm;
import com.korea.calendar.domain.diary.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {

        Page<Diary> paging = this.diaryService.getList(page);
        model.addAttribute("paging", paging);

        return "pages/diary/diary_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {

        Diary diary = this.diaryService.getDiary(id);
        model.addAttribute("diary", diary);

        return "pages/diary/diary_detail";
    }

    @GetMapping("/create")
    public String create(DiaryForm diaryForm) {

        return "pages/diary/diary_form";
    }

    @PostMapping("create")
    public String create(@Valid DiaryForm diaryForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "pages/diary/diary_form";
        }

        this.diaryService.create(diaryForm.getSubject(), diaryForm.getContent(), diaryForm.getSelectedDate());
        return "redirect:/diary/list";
    }
}