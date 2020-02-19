package jcode18.community.controller;

import jcode18.community.dto.NotificationDTO;
import jcode18.community.dto.PageDTO;
import jcode18.community.enums.NotificationEnum;
import jcode18.community.model.User;
import jcode18.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(id,user);

        if(NotificationEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
        ||NotificationEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
