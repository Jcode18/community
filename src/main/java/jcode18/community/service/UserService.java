package jcode18.community.service;

import jcode18.community.mapper.UserMapper;
import jcode18.community.model.User;
import jcode18.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void creatOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
        List<User> users=userMapper.selectByExample(userExample);
        if(users.size()==0){
            user.setGmtcreate(System.currentTimeMillis());
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
        }else {
            User dbUser=users.get(0);
            User updateUser=new User();
            updateUser.setGmtmodified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatarurl(user.getAvatarurl());
            updateUser.setToken(user.getToken());
            UserExample example=new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
}
