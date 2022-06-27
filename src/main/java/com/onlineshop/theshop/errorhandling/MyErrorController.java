package com.onlineshop.theshop.errorhandling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/error")
@Controller
public class MyErrorController {

    @GetMapping("/")
    public String anError(){
        return "/error/error";
    }
    @GetMapping("/not")
    public String noError(){
        return "/error/noerror";
    }

    @GetMapping("/400")
    public String badRequest(){
        return "/error/400";
    }
    @GetMapping("/401")
    public String unauthorized(){
        return "/error/401";
    }
    @GetMapping("/403")
    public String forbidden(){
        return "/error/403";
    }
    @GetMapping("/404")
    public String notFound(){
        return "/error/404";
    }
    @GetMapping("/500")
    public String internalServerError(){
        return "/error/500";
    }
}
