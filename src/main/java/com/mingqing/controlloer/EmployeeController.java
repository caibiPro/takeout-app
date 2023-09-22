package com.mingqing.controlloer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.Employee;
import com.mingqing.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/login")
	public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
		String passwordEncrypt = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Employee::getUsername, employee.getUsername());
		Employee loginEmployee = employeeService.getOne(queryWrapper);

		if (loginEmployee == null) {
			return Result.error("登陆失败");
		}
		if (!passwordEncrypt.equals(loginEmployee.getPassword())) {
			return Result.error("密码错误");
		}

		if (loginEmployee.getStatus() == 0) {
			return Result.error("账号被禁用");
		}

		request.getSession().setAttribute("employee", loginEmployee.getId());
		log.info("登陆成功");
		return Result.success(loginEmployee);
	}

	@PostMapping("/logout")
	public Result<String> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("employee");
		log.info("退出登陆成功");
		return Result.success("退出成功");
	}

}
