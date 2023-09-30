package com.mingqing.controlloer;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mingqing.common.utils.MailUtils;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.User;
import com.mingqing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/sendMsg")
	public Result<?> sendMsg(@RequestBody User user, HttpSession session) throws MessagingException {
		String phone = user.getPhone();
		if (!StringUtils.isEmpty(phone)) {
			String code = MailUtils.achieveCode();
			MailUtils.sendTestMail(phone, code);

			session.setAttribute(phone, code);
			return Result.success("验证码发送成功");
		}
		return Result.error("验证码发送失败");
	}

	@PostMapping("/login")
	public Result<?> login(@RequestBody Map<String, String> map, HttpSession session) {
		String phone = map.get("phone");
		String code = map.get("code");
		String codeInSession = (String) session.getAttribute(phone);

		// 验证码检验
		if (!StringUtils.isEmpty(code) && code.equals(codeInSession)) {
			// 查询用户是否已注册
			LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(User::getPhone, phone);
			User user = userService.getOne(queryWrapper);
			if (user == null) {
				user = new User();
				user.setPhone(phone);
				user.setName("用户" + UUID.randomUUID());
				userService.save(user);
			}
			session.setAttribute("user", user.getId());
			return Result.success(user);
		}
		return Result.error("登陆失败");
	}

}
