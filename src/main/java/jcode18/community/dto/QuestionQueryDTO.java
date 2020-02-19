package jcode18.community.dto;

import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
