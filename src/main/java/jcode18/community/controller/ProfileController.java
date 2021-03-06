package jcode18.community.controller;

import jcode18.community.dto.PageDTO;
import jcode18.community.model.Notification;
import jcode18.community.model.User;
import jcode18.community.service.NotificationService;
import jcode18.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "2") Integer size){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
       if("question".equals(action)){
           model.addAttribute("section","question");
           model.addAttribute("sectionName","我的提问");
           PageDTO pageDTO = questionService.listByUserId(user.getId(), page, size);
           model.addAttribute("pageDTO",pageDTO);
       }else if("response".equals(action)){

           PageDTO pageDTO =notificationService.List(user.getId(), page, size);
           model.addAttribute("pageDTO",pageDTO);
           model.addAttribute("section","response");
           model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
