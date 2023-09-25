package com.mingqing.controlloer;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.Employee;
import com.mingqing.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

	@PostMapping
	public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
		log.info("新增的员工信息：{}", employee);

		employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
		Long empId = (Long) request.getSession().getAttribute("employee");

		employee.setCreateUser(empId);
		employee.setUpdateUser(empId);

		boolean save = employeeService.save(employee);
		log.info("添加{}", save ? "成功" : "失败");
		return Result.success("添加员工成功");
	}

	@GetMapping("page")
	public Result<?> page(int page, int pageSize, String name) {
		log.info("page={},pageSize={},name={}", page, pageSize, name);

		Page<Employee> pageInfo = new Page<>(page, pageSize);
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
		queryWrapper.orderByDesc(Employee::getUpdateTime);

		employeeService.page(pageInfo, queryWrapper);
		return Result.success(pageInfo);
	}

	@PutMapping
	public Result<?> update(HttpServletRequest request, @RequestBody Employee employee) {
		log.info("修改的员工信息:{}", employee);

		Long loginUserID = (Long) request.getSession().getAttribute("employee");

		employee.setUpdateUser(loginUserID);
		boolean update = employeeService.updateById(employee);

		if (!update) {
			return Result.error("修改失败");
		}
		return Result.success("修改成功");
	}
	
	@GetMapping("/{id}")
	public Result<?> getById(@PathVariable Long id) {
		log.info("根据id查询员工信息...");
		Employee employee = employeeService.getById(id);
		if (employee != null) {
			return Result.success(employee);
		}
		return Result.error("未查询到该员工信息");
	}

	@GetMapping("/all")
	public Result<?> all() {
		List<Employee> list = employeeService.list();
		list.forEach((one) -> {
			log.info("{}", one);
		});

		return Result.success(list);
	}

}
