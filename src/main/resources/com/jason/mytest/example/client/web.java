[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(webApiPath + webApiPackage_dir + "/" + className + webApiName+".java")}[#t  /]
package ${webApiPackage};

//TODO fixme
import ${coreBasePackage}.Result;

import ${dtoPackage}.${className}${dtoName};
import ${voPackage}.${className}${voName};

import java.util.List;

public interface ${className}${webApiName} {

    Result<Integer> saveForWeb(${className}${dtoName} dto);

    Result<Integer> editForWeb(${className}${dtoName} dto);

    Result<List<${className}${voName}>> listForWeb(${className}${dtoName} dto);

    Result<${className}${voName}> getForWeb(${className}${dtoName} dto);

//    Result<Integer> removeForWeb(${className}${dtoName} dto);
}

