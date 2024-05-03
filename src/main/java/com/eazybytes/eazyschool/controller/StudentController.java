package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.NarayanaClassRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("student")
public class StudentController {
     @Autowired
     private CoursesRepository coursesRepository;
    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession httpSession){
        Person person=(Person) httpSession.getAttribute("profile1");
        ModelAndView modelAndView=new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person",person);
        return modelAndView;
    }

}
