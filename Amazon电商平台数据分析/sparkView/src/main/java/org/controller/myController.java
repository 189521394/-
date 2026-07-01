package org.controller;

import org.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class myController {
    @Autowired
    private service service;
    
    @RequestMapping("/")
    public ModelAndView getMain(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/views/index.jsp");
        return modelAndView;
    }
    
    @RequestMapping("/top10")
    @ResponseBody
    public Object[] getTop10(){
        return service.getTop10();
    }
    
    @RequestMapping("/top3")
    @ResponseBody
    public Object[] getTop3(){
        return service.getTop3();
    }
    
    @RequestMapping("/jump")
    @ResponseBody
    public Object[] getJump(){
        return service.getConversion();
    }
}
