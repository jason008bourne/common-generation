package com.jason.mytest.example;

import lombok.*;

import java.util.Map;

/**
 * 后台类目
 */
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QqDTO {

    /**
     * 类目英文名称
     */
	private String englishName;
    /**
     * 类目中文名称
     */
	private String chineseName;
    /**
     * 父类目id
     */
	private Integer parentId;
    /**
     * 叶子节点
     */
	private Boolean leaf;
    /**
     * 有效类目
     */
	private Boolean valid;
    /**
     * 类目层级:FIRST(1,一级类目)|SECOND(2,二级类目)|THIRD(3,三级类目)
     */
    private Integer treeLevel;
}



