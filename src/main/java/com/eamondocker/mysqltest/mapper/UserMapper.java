package com.eamondocker.mysqltest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eamondocker.mysqltest.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author:YiMing
 * @create:2021/2/4,20:23
 * @version:1.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
