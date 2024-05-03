package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {
    @Autowired
    private final PersonService personService;
    @RequestMapping(value = "/register",method ={RequestMethod.GET})
    public String displayRegisterPage(Model model){
        Person person=new Person();
        model.addAttribute("person",person);
        return "register.html";

    }
    @RequestMapping(value = "/createUser",method = {RequestMethod.POST})
    public String createUser(@Valid  @ModelAttribute Person person, Errors errors,Model model){

        if(errors.hasErrors()){

           return "register.html";
        }
        boolean isSaved=personService.createNewPerson(person);
        if(isSaved){
            return "redirect:/login?register=true";
        }
       else{
           return "register.html";
        }
    }

}
