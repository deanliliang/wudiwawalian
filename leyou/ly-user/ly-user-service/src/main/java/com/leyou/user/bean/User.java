package com.leyou.user.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
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
    private String username;
    private String password;
    private String phone;
    private Date createTime;
    private Date updateTime;
}
