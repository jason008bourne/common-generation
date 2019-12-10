[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(true)}[#t  /]
${gg.setOutputFile(poPath + poPackage_dir + "/" + dataSourceName + "/" + className + poName+".java")}[#t  /]
package ${poPackage}.${dataSourceName};

import ${coreBasePackage}.${basePo};
import lombok.*;

/**
 * ${(table.remarks)!}
 */
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ${className}${poName} extends ${basePo}<${pkJavaType}>{

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
}



