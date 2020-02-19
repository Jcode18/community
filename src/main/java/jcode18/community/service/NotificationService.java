package jcode18.community.service;

import jcode18.community.dto.NotificationDTO;
import jcode18.community.dto.PageDTO;
import jcode18.community.dto.QuestionDTO;
import jcode18.community.enums.NotificationEnum;
import jcode18.community.enums.NotificationStatusEnum;
import jcode18.community.exception.CustomizeErrorCode;
import jcode18.community.exception.CustomizeException;
import jcode18.community.mapper.NotificationMapper;
import jcode18.community.mapper.UserMapper;
import jcode18.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PageDTO List(Long userId, Integer page, Integer size) {

        Integer totalPage;
        PageDTO<NotificationDTO> pageDTO=new PageDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size +1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageDTO.setNeed(totalPage,page);
        Integer offset=size*(page-1);
        NotificationExample example1 = new NotificationExample();
        example1.createCriteria().andReceiverEqualTo(userId);
        example1.setOrderByClause("gmt_create desc");
        List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds(example1,new RowBounds(offset,size));

        if(notifications.size()==0){
            return pageDTO;
        }

        List<NotificationDTO> notificationDTOS=new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        pageDTO.setData(notificationDTOS);
        return pageDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification=notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }
}
