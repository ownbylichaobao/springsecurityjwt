package com.lichaobao.springsecurityjwt.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
public class MyPasswordEncoder implements PasswordEncoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyPasswordEncoder.class);

    /**
     * 自定义密码加密（出于示例，本代码没有对密码进行加密，直接返回原密码）
     * @param charSequence 需要加密的密码
     * @return 加密后的密码
     */
    @Override
    public String encode(CharSequence charSequence) {
        LOGGER.info("now encode password :{}",charSequence.toString());
        return charSequence.toString();
    }

    /**
     * 比较加密后的密码与数据库中的密码是否匹配
     * @param charSequence 用户登陆传来的密码
     * @param s 数据库中存储的密码
     * @return true 匹配 false 不匹配
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        LOGGER.info("matchs charSequence :{} and password :{}",charSequence,s);
        return encode(charSequence).equals(s);
    }
}
