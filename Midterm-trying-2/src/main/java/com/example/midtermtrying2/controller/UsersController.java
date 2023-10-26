package com.example.midtermtrying2.controller;

import com.example.midtermtrying2.model.UsersModel;
import com.example.midtermtrying2.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class UsersController {
    private final UsersService usersService;
    private  UsersModel usersModel;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }
    @GetMapping("/manage-account")
    public String getManageAccountPage(){
        return "manage-account-page";
    }
    @GetMapping("/library")
    public String getLibraryPage(){
        return "library-page";
    }
    @GetMapping("/inside")
    public String getInsidePage(Model model)
    {
        return "inside_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel){
        System.out.println("register request: " + usersModel);
        UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        return registeredUser == null ? "register_page" : "login_page";
    }
    @GetMapping("/")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }



    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model){
        System.out.println("login request: " + usersModel);
        UsersModel authenticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if(authenticated != null){
            this.usersModel = authenticated;
            model.addAttribute("userLogin", authenticated.getLogin());
            return "inside_page";
        }else return "login_page";
    }

    @PostMapping("/change")
    public String change(@ModelAttribute UsersModel usersModel, Model model){
        if(usersModel.getLogin() != null){
            model.addAttribute("changeName",this.usersModel );
            System.out.println("Put request: " + this.usersModel);
            String response = changeName(usersModel.getLogin());
            return response;
        }
        else return getManageAccountPage();
    }
    @PutMapping
    public String changeName(String name){
        this.usersModel.setLogin(name);
        usersService.changeName(this.usersModel);
        return getManageAccountPage();
    }





    @DeleteMapping("/deleted-successfully")
    public String deleting(){
        usersService.deleteById(this.usersModel.getId());
        return "login_page";
    }


    @PostMapping("/delete")
    public String deleteByID(Model model){
        if(usersModel.getId() != null){
            model.addAttribute("deleting", this.usersModel);

            System.out.println("Delete request: " + this.usersModel);
            String response = deleting();
            return response;
        }
        return "manage-account-page";
    }




}
