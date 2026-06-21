package org.controller;

import org.bean.DataBean;
import org.service.CityService;
import org.service.DegreeService;
import org.service.ExpService;
import org.service.JobNameService;
import org.service.SalaryAvgService;
import org.service.SalaryService;
import org.service.SkillService;
import org.service.WelfareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class myController {

    @Autowired
    private CityService cityService;

    @Autowired
    private DegreeService degreeService;

    @Autowired
    private ExpService expService;

    @Autowired
    private SalaryAvgService salaryAvgService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private JobNameService jobNameService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private WelfareService welfareService;

    @RequestMapping("/")
    public ModelAndView getBook() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/WEB-INF/views/index.jsp");
        return modelAndView;
    }

    @RequestMapping("/city")
    @ResponseBody
    public DataBean[] getCity() {
        return cityService.getCityCountAll();
    }

    @RequestMapping("/degree")
    @ResponseBody
    public DataBean[] getDegree() {
        return degreeService.getDegreeCountAll();
    }

    @RequestMapping("/experience")
    @ResponseBody
    public DataBean[] getExperience() {
        return expService.getExpCountAll();
    }

    @RequestMapping("/avgSalary")
    @ResponseBody
    public Object[] getAvgSalary() {
        return salaryAvgService.getSalaryAvgAll();
    }

    @RequestMapping("/salary")
    @ResponseBody
    public Object[] getSalary() {
        return salaryService.getSalaryRangeAll();
    }

    @RequestMapping("/jobName")
    @ResponseBody
    public DataBean[] getJobName() {
        return jobNameService.getJobNameAll();
    }

    @RequestMapping("/skills")
    @ResponseBody
    public DataBean[] getSkills() {
        return skillService.getSkillCountAll();
    }

    @RequestMapping("/welfare")
    @ResponseBody
    public DataBean[] getWelfare() {
        return welfareService.getWelfareCountAll();
    }
}
