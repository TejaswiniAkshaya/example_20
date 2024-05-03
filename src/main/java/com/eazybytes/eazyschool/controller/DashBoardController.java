package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequiredArgsConstructor
public class DashBoardController {
    @Autowired
    PersonRepository personRepository;
    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session){
       Person person=personRepository.readByEmail(authentication.getName());
       model.addAttribute("username",person.getName());
       model.addAttribute("roles",authentication.getAuthorities().toString());
       if(person.getNarayanaClass()!=null && person.getNarayanaClass().getName()!=null){
           model.addAttribute("enrolledClass",person.getNarayanaClass().getName());
       }
       session.setAttribute("profile1",person);
       return "dashboard.html";
    }
}
