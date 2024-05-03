package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.NarayanaClass;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.NarayanaClassRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private Set<Person> personSet=new HashSet<>();
    @Autowired
    private NarayanaClassRepository narayanaClassRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CoursesRepository coursesRepository;
   @RequestMapping("/displayClasses")
   public ModelAndView displayClasses(Model model){
       List<NarayanaClass> narayanaClasses=narayanaClassRepository.findAll();
       ModelAndView modelAndView=new ModelAndView("classes.html");
       modelAndView.addObject("narayanaClasses",narayanaClasses);
       modelAndView.addObject("narayanaClass",new NarayanaClass());
       return modelAndView;
   }
   @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("narayanaClass") NarayanaClass narayanaClass,
                                    Errors errors){
       narayanaClassRepository.save(narayanaClass);
       return new ModelAndView("redirect:/admin/displayClasses");
   }
   @GetMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam(required = true) int id){
       NarayanaClass narayanaClass=narayanaClassRepository.getReferenceById(id);
       for(Person person1:narayanaClass.getPersonSet()){
           person1.setNarayanaClass(null);
           personRepository.save(person1);
       }
       narayanaClassRepository.deleteById(id);
       ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayClasses");
       return modelAndView;

   }
   @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam(required = true) int classId, HttpSession session, @RequestParam(required = false,value ="error") String error){
       NarayanaClass narayanaClass=narayanaClassRepository.getReferenceById(classId);
       String errorMessage=null;
       ModelAndView modelAndView=new ModelAndView("students.html");
       modelAndView.addObject("narayanaClass",narayanaClass);
       modelAndView.addObject("person",new Person());
       session.setAttribute("narayanaClass",narayanaClass);
       if(error!=null){
           errorMessage = "Invalid Email entered!";
           modelAndView.addObject("errorMessage",errorMessage);
       }
       return modelAndView;
   }

   @PostMapping("/addStudent")
   public ModelAndView addStudent(Model model,@ModelAttribute("person") Person person,HttpSession httpSession){
       ModelAndView modelAndView=new ModelAndView();

       NarayanaClass narayanaClass= (NarayanaClass) httpSession.getAttribute("narayanaClass");
       int id=narayanaClass.getClassId();

    Person person1=personRepository.readByEmail(person.getEmail());

    if(person1==null || !(person1.getPersonId()>0)){
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+id+"&error=true");
        return modelAndView;
    }
        person1.setNarayanaClass(narayanaClass);
        personRepository.save(person1);
        narayanaClass.getPersonSet().add(person1);
        NarayanaClass narayanaClass1=narayanaClassRepository.save(narayanaClass);
        httpSession.setAttribute("narayanaClass",narayanaClass1);
       modelAndView.setViewName("redirect:/admin/displayStudents?classId="+id);
       return modelAndView;



   }
   @GetMapping ("/deleteStudent")
    public ModelAndView deleteStudent(Model model,@RequestParam int personId,HttpSession session){

       NarayanaClass narayanaClass= (NarayanaClass) session.getAttribute("narayanaClass");
       int id=narayanaClass.getClassId();
       Person person1=personRepository.getReferenceById(personId);
       person1.setNarayanaClass(null);
       personRepository.save(person1);
       narayanaClass.getPersonSet().remove(person1);
       NarayanaClass narayanaClass1=narayanaClassRepository.save(narayanaClass);
       session.setAttribute("narayanaClass",narayanaClass1);
       ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayStudents?classId="+id);
       return modelAndView;

   }
   @GetMapping("/displayCourses")
    public ModelAndView displayCourses(){
       //List<Courses> courses = coursesRepository.findByOrderByName();
       List<Courses> courses=coursesRepository.findAll(Sort.by("name").descending());

       Courses course1=new Courses();
       ModelAndView modelAndView=new ModelAndView("courses_secure.html");
       modelAndView.addObject("courses",courses);
       modelAndView.addObject("course1",course1);
       return modelAndView;
   }
   @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model,@ModelAttribute("course1") Courses courses){
       ModelAndView modelAndView=new ModelAndView();
       coursesRepository.save(courses);
       modelAndView.setViewName("redirect:/admin/displayCourses");
       return modelAndView;
   }
   @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model,@RequestParam int id,HttpSession httpSession,@RequestParam(required = false) String error){
        String errorMessage=null;
       ModelAndView modelAndView=new ModelAndView("course_students.html");
       Courses courses= coursesRepository.getReferenceById(id);
       modelAndView.addObject("courses",courses);
       modelAndView.addObject("person",new Person());
       httpSession.setAttribute("courses",courses);
       if(error!=null){
           errorMessage = "Invalid Email entered!";
           modelAndView.addObject("errorMessage",errorMessage);
       }
       return modelAndView;

   }
   @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse( @ModelAttribute("person") Person person,HttpSession httpSession){
       Person person1=personRepository.readByEmail(person.getEmail());
       ModelAndView modelAndView=new ModelAndView();
       Courses courses=(Courses)httpSession.getAttribute("courses");
       int id=courses.getCourseId();
       if(person1==null || !(person1.getPersonId()>0)){
           modelAndView.setViewName("redirect:/admin/viewStudents?id="+id+"&error=true");
         return modelAndView;
       }

       person1.getCourses().add(courses);
       courses.getPersons().add(person1);
       personRepository.save(person1);
       coursesRepository.save(courses);
       httpSession.setAttribute("courses",courses);
       modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
       return modelAndView;
   }
   @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam(value = "personId") int id,HttpSession session){
       Person person1=personRepository.getReferenceById(id);
       Courses courses=(Courses) session.getAttribute("courses");
       courses.getPersons().remove(person1);
       coursesRepository.save(courses);
       person1.getCourses().remove(courses);
       personRepository.save(person1);
       session.setAttribute("courses",courses);
       ModelAndView modelAndView=new ModelAndView();
       modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
       return modelAndView;

   }





}
