package com.example.bibliotecagranvia.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiscelaneaController {
    @GetMapping("/building")
    public String getBuilding(){
        return "miscelaneous/building";
    }
    @GetMapping("/error")
    public String getError(){
        return "miscelaneous/error";
    }
    @GetMapping("/userTitleNotFoundBiblio")
    public String getUserTitleNotFoundBiblio(){
        return "miscelaneous/userTitleNotFoundB";
    }
    @GetMapping("/userTitleNotFoundUser")
    public String getUserTitleNotFoundUser(){
        return "miscelaneous/userTitleNotFoundU";
    }
}
