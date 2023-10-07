package com.mingqing.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.Employee;
import com.mingqing.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "员工接口")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @PostMapping("/login")
  @ApiOperation("登陆")
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
  @ApiOperation("退出登陆")
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

    boolean save = employeeService.save(employee);
    log.info("添加{}", save ? "成功" : "失败");
    return Result.success("添加员工成功");
  }

  @GetMapping("page")
  @ApiOperation("分页查询")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int"),
      @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "int"),
      @ApiImplicitParam(name = "name", value = "用户名", required = false, dataType = "String")
  })
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
