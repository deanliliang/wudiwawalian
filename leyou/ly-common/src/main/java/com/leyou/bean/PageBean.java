package com.leyou.bean;

import lombok.Data;
import java.util.List;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.bean
 * @ClassName: PageBean
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/16 19:57
 * @Version: 1.0
 */

@Data
public class PageBean<T> {
    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageBean(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    public PageBean(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageBean() {
    }
}
