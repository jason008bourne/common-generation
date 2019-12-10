[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(daoPath + daoPackage_dir + "/" + dataSourceName + "/" + className + daoName+".java")}[#t  /]
package ${daoPackage}.${dataSourceName};

import ${coreBasePackage}.${baseDao};
import ${poPackage}.${dataSourceName}.${className}${poName};
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ${className}${daoName} extends ${baseDao}<${className}${poName}> {

}

