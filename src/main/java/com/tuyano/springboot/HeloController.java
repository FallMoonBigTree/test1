package com.tuyano.springboot;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tuyano.springboot.repositories.MyDataRepository;

@Controller
public class HeloController {
	
@Autowired
MyDataRepository repository;

@RequestMapping(value ="/",method=RequestMethod.GET)
public ModelAndView index(
           @ModelAttribute("formModel")MyData mydata,
               ModelAndView mav){
       mav.setViewName("index");
       mav.addObject("msg","this is sample content.");
       mav.addObject("formModel",mydata);
       Iterable<MyData>list = repository.findAll();
       mav.addObject("datalist",list);
       return mav;
}

@RequestMapping(value="/",method=RequestMethod.POST)
@Transactional(readOnly=false)
public ModelAndView form(
               @ModelAttribute("formModel")
               @Validated MyData mydata,
               BindingResult result,
               ModelAndView mav){
ModelAndView res=null;
if(!result.hasErrors()){
        repository.saveAndFlush(mydata);
        res= new ModelAndView("redirect:/");
}else{
       mav.setViewName("index");
       mav.addObject("msg","sorry,error is occured...");
       Iterable<MyData>list = repository.findAll();
       mav.addObject("datalist",list);
       res= mav;
}
return res;
}

@PostConstruct
public void init() {
 MyData d1 = new MyData();
 d1.setName("tuyano");
 d1.setAge(123);
 d1.setMail("syoda@tuyano.com");
 d1.setMemo("this is my data!");
 repository.saveAndFlush(d1);
 MyData d2 = new MyData();
 d2.setName("hanako");
 d2.setAge(15);
 d2.setMail("hanako@flower");
 d2.setMemo("my girl friend.");
 repository.saveAndFlush(d2);
 MyData d3 = new MyData();
 d3.setName("sachiko");
 d3.setAge(37);
 d3.setMail("sachico@happy");
 d3.setMemo("my work friend...");
 repository.saveAndFlush(d3);
}
 
 @RequestMapping(value ="/edit/{id}",method=RequestMethod.GET)
 public ModelAndView edit(@ModelAttribute MyData mydata,
                @PathVariable int id,ModelAndView mav){
        mav.setViewName("edit");
        mav.addObject("title","edit mydata.");
        Optional<MyData>data = repository.findById((long)id);
        mav.addObject("formModel",data.get());
        return mav;
 }

 @RequestMapping(value="/edit",method=RequestMethod.POST)
 @Transactional(readOnly=false)
 public ModelAndView updata(@ModelAttribute MyData mydata,
                ModelAndView mav){
         repository.saveAndFlush(mydata);
         return new ModelAndView("redirect:/");
}
 @RequestMapping(value ="/delete/{id}",method=RequestMethod.GET)
 public ModelAndView delete(@PathVariable int id,
                ModelAndView mav){
        mav.setViewName("delete");
        mav.addObject("title","delete mydata.");
        Optional<MyData>data = repository.findById((long)id);
        mav.addObject("formModel",data.get());
        return mav;
 }

 @RequestMapping(value="/delete",method=RequestMethod.POST)
 @Transactional(readOnly=false)
 public ModelAndView remove(@RequestParam long id,
                ModelAndView mav){
         repository.deleteById(id);
         return new ModelAndView("redirect:/");
 }
}