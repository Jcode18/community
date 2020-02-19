package jcode18.community.controller;

import jcode18.community.dto.QuestionDTO;
import jcode18.community.dto.TransCommentDTO;
import jcode18.community.enums.CommentTypeEnum;
import jcode18.community.service.CommentService;
import jcode18.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model){
        QuestionDTO questionDTO= questionService.getById(id);
        List<QuestionDTO> relatedQuestions=questionService.selectRelated(questionDTO);
        List<TransCommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionDTO);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

}
