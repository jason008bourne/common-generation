[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(apiPath + apiPackage_dir + "/" + className + apiName+".java")}[#t  /]
package ${apiPackage};

//TODO fixme
import ${coreBasePackage}.Result;

import ${dtoPackage}.${className}${dtoName};

import java.util.List;

public interface ${className}${apiName} {

    Result<Integer> saveForApi(${className}${dtoName} dto);

    Result<Integer> editForApi(${className}${dtoName} dto);

    Result<List<${className}${dtoName}>> listForApi(${className}${dtoName} dto);

    Result<${className}${dtoName}> getForApi(${className}${dtoName} dto);

//    Result<Integer> removeForApi(${className}${dtoName} dto);
}

