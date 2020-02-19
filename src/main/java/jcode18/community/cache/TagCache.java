package jcode18.community.cache;

import jcode18.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("js","php","css","html","node","java","python"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","struts","spring mvc","spring boot","yil","mybatis","vue"));
        tagDTOS.add(framework);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Linux","windows","docker","tomcat","unix"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","sql","h2","redis","oracle"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("工具");
        tool.setTags(Arrays.asList("git","maven","ide","svn","eclipse","vim","vs"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        String[] split= StringUtils.split(tags,",");
        List<TagDTO> tagDTOS=get();

        List<String> tagList=tagDTOS.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
        String invalid=Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
