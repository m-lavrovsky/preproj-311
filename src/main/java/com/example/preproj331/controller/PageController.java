package com.example.preproj331.controller;

import com.example.preproj331.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.preproj331.model.User;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class PageController {
    private final UserRepository userRepository;

    /*private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();*/

    @Autowired
    public PageController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userRepository.save (new User("Иван","Иванов",36));
        this.userRepository.save (new User("Сан","Саныч",50));
        this.userRepository.save (new User("Фёдор","Пупкин",42));
        this.userRepository.save (new User("Семён","Семёныч",23));
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model/*,  @RequestParam(required = false) int adderror*/) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring привет MVC application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        model.addAttribute("userlist",userRepository.findAll());
        /*if (adderror == 1) {
            model.addAttribute("adderror","ошибка добавления нового юзера - пустое имя и/или фамилия");
        }*/
        return "index";
    }

    @GetMapping(value = "/delete")
    public String deletePage(Model model, @RequestParam(required = false) long uid) {
        //Long id = checkValidId(uid);
        if (userRepository.existsById(uid)) {
            User user = userRepository.findById(uid).get();
            userRepository.deleteById(uid);
            model.addAttribute("userDeleted",user);
            model.addAttribute("descr"," удалён");
            return "/delete";
        }
        else {
            model.addAttribute("descr","Ничего не получится: Id = "+uid+" юзера для удаления некорректен");
            return "/wrong-id";
        }
    }

   // страница редактирования пользователя
    @GetMapping(value = "/edit", produces = "text/html; charset=utf-8")
    public String editPage(Model model, @RequestParam(required = false) long uid) {
        try {
            if (userRepository.existsById(uid)) {
                model.addAttribute("descr","Редактирование пользователя");
                model.addAttribute("user", userRepository.findById(uid).get());
                return "/edit";
            }
        }
        catch (IllegalStateException e) {
            model.addAttribute("descr","Id = '"+uid+"' юзера для редактирования некорректен");
            return "/wrong-id";
        }
        return "/wrong-id";
    }

   // страница добавления нового пользователя
    @GetMapping(value = "/add", produces = "text/html; charset=utf-8")
    public String addPage(Model model) {
        User user = new User((long) -1,"","",1);
        model.addAttribute("descr","Добавление нового пользователя");
        model.addAttribute("user", user);
        return "/edit";
    }

    // приём данных редактирования юзера
    @PostMapping()
    public String editUser(@Valid @ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/";
    }
}