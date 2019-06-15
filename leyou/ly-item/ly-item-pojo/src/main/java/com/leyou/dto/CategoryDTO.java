package com.leyou.dto;

import lombok.Data;

/**
 * @ProjectName: leyou
 * @Package: com.leyou.dto
 * @ClassName: CategoryDTO
 * @Author: Dean
 * @Description: ${description}
 * @Date: 2019/6/14 21:09
 * @Version: 1.0
 */
@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;
}
