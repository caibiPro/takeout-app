package com.mingqing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mingqing.common.utils.BaseContext;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.AddressBook;
import com.mingqing.service.AddressBookService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

  @Autowired
  private AddressBookService addressBookService;

  @GetMapping("/list")
  public Result<?> list(AddressBook addressBook) {
    log.info(addressBook.toString());
    Long userId = BaseContext.getLoginId();
    addressBook.setUserId(userId);

    LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId,
        addressBook.getUserId());
    queryWrapper.orderByDesc(AddressBook::getUpdateTime);

    List<AddressBook> addressBooks = addressBookService.list(queryWrapper);

    return Result.success(addressBooks);
  }

  /**
   * 新增地址簿
   */
  @PostMapping
  public Result<?> addAddress(@RequestBody AddressBook addressBook) {
    addressBook.setUserId(BaseContext.getLoginId());
    log.info("addressBook added: {}", addressBook);
    boolean saved = addressBookService.save(addressBook);
    if (!saved) {
      return Result.error("添加失败");
    }
    return Result.success("添加成功");
  }

  @PutMapping("/default")
  public Result<?> setDefaultAddress(@RequestBody AddressBook addressBook) {
    Long userId = BaseContext.getLoginId();
    addressBook.setUserId(userId);

    LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.eq(userId != null, AddressBook::getUserId, userId);
    updateWrapper.set(AddressBook::getIsDefault, 0);
    addressBookService.update(updateWrapper);

    addressBook.setIsDefault(1);
    addressBookService.updateById(addressBook);
    return Result.success(addressBook);
  }

  @GetMapping("/default")
  public Result<?> defaultAddress() {
    Long userId = BaseContext.getLoginId();
    LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();

    queryWrapper.eq(userId != null, AddressBook::getUserId, userId);
    queryWrapper.eq(AddressBook::getIsDefault, 1);

    AddressBook address = addressBookService.getOne(queryWrapper);
    return Result.success(address);
  }
}
