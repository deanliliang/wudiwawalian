package com.leyou.user.bean;

import com.leyou.constants.RegexPatterns;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.user.bean
 * @ClassName: User
 * @Author: Dean
 * @Description:
 * @Date: 2019/6/27 20:02
 * @Version: 1.0
 */
@Table(name = "tb_user")
@Data
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @Pattern(regexp = RegexPatterns.USERNAME_REGEX,message = "用户名格式不正确")
    private String username;
    @Length(min = 4,max = 20,message = "密码长度为4到20位")
    private String password;
    @Pattern(regexp = RegexPatterns.PHONE_REGEX,message = "手机号码格式不正确")
    private String phone;
    private Date createTime;
    private Date updateTime;
}
