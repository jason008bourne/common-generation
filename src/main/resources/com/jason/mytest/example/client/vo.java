[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(voPath + voPackage_dir + "/" + className + voName+".java")}[#t  /]
package ${voPackage};

import java.io.Serializable;
import lombok.*;

/**
 * ${(table.remarks)!}
 */
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ${className}${voName} implements Serializable {

	/**
 	 * 主键id
 	 */
	private ${pkJavaType} id;

	[#list table.columns as column]
	[#if column.asType=='Enum']
    /**
     * ${(column.remarks)!}
     */
    private Integer ${column.columnNameLower};
    [#elseif column.asType=='Object']
	[#else]
    /**
     * ${(column.remarks)!}
     */
    private ${column.simpleJavaType} ${column.columnNameLower};
	[/#if]
	[/#list]

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
}



