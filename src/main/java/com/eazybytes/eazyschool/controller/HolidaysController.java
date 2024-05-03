package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Holiday;
import com.eazybytes.eazyschool.repository.HolidayRepository;
import com.eazybytes.eazyschool.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HolidaysController {
    @Autowired
    private final HolidayRepository holidayRepository;


    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display,Model model) {
        if(display.equals("all") || display==null){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }
        else if(display.equals("festival")){
            model.addAttribute("festival",true);
        }
        else if(display.equals("federal")){
            model.addAttribute("federal",true);
        }
        else{
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }
        List<Holiday> holidays =holidayRepository.findAll();
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }

}
