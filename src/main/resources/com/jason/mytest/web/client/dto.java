[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(dtoPath + dtoPackage_dir + "/" + className + dtoName+".java")}[#t  /]
package ${dtoPackage};

import ${clientBasePackage}.${baseDto};
import ${clientBasePackage}..EnumIntegerAble;
import lombok.*;

/**
 * ${(table.remarks)!}
 */
@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ${className}${dtoName} extends ${baseDto}<${pkJavaType}>{

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


	[#list table.columns as column]
	[#if column.asType=='Enum']
    @AllArgsConstructor
    @Getter
	public static enum ${column.enumClassName} implements EnumIntegerAble{
		[#list column.enumList as e]
		${e.enumAlias}(${e.enumKey},"${e.enumDesc}")[#if e_has_next],[#else];[/#if]
		[/#list]
        private final Integer code;
        private final String value;
	}
	[#elseif column.asType=='Object']
	[#else]
	[/#if]
	[/#list]
}



