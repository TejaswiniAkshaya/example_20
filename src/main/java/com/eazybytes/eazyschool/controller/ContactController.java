package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Slf4j
public class ContactController {
    @Autowired
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService){
        this.contactService=contactService;
    }
    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        Contact contact=new Contact();
        model.addAttribute("contact",contact);
        return "contact.html";
    }
   /* @RequestMapping(value = "/saveMsg",method = POST)
    public String saveMessage(@RequestParam String name,@RequestParam String mobileNum,@RequestParam String email
    ,@RequestParam String subject,@RequestParam String message){
        log.info(name);
        log.info(message);
        return "redirect:/contact";
    }*/
    @RequestMapping(value = "/saveMsg",method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact,  Errors errors,Model model){
        if(errors.hasErrors()){

            log.error("Contact form Validation failed due to "+errors.toString());
            return "contact.html";
        }
        boolean f1= contactService.saveMessageDetails(contact);
       return "redirect:/contact";
    }
    /*@RequestMapping("/displayMessages")
    public String displayMessages(Model model){
       List<Contact> contactList=contactService.findMsgsWithOpenStatus();
       model.addAttribute("contactMsg",contactList);
       return "messages.html";
    }*/
    @RequestMapping(value = "/closeMsg",method = GET)
    public String close(@RequestParam(required = true) int id){
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";

    }
    @RequestMapping("/displayMessages/page/{pageNum}")
    public ModelAndView displayMessages(Model model, @PathVariable(name = "pageNum")int pageNum,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir")String sortDir){
        Page<Contact> msgPage=contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs=msgPage.getContent();
        ModelAndView modelAndView=new ModelAndView("messages.html");
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",msgPage.getTotalPages());
        model.addAttribute("totalMsgs",msgPage.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
      return modelAndView;

    }
}
