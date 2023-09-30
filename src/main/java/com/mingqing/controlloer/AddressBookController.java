package com.mingqing.controlloer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.mingqing.common.utils.BaseContext;
import com.mingqing.common.utils.Result;
import com.mingqing.entity.AddressBook;
import com.mingqing.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
		queryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
		queryWrapper.orderByDesc(AddressBook::getUpdateTime);

		List<AddressBook> addressBooks = addressBookService.list(queryWrapper);

		return Result.success(addressBooks);
	}

}
