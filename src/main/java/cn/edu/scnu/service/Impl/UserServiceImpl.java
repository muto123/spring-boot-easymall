package cn.edu.scnu.service.Impl;

import java.util.UUID;

import cn.edu.scnu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.edu.scnu.mapper.UserMapper;
import cn.edu.scnu.pojo.User;
import cn.edu.scnu.utils.MD5Util;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
	@Autowired
	private UserMapper userMapper;
	public User selectByUsername(String userName) {
		QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
		userQueryWrapper.eq("user_name", userName);
		return userMapper.selectOne(userQueryWrapper);
	}
	@Override
	public User queryUserJson(String userId) {
		return userMapper.selectById(userId);
	}
	@Override
	public void userSave(User user) {
		user.setUserId(UUID.randomUUID().toString());
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		userMapper.insert(user);
	}
}
