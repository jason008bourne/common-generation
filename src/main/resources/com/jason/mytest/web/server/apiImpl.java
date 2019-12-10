[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(implPath + implPackage_dir + "/" + className + implName+".java")}[#t  /]
package ${implPackage};

import com.alibaba.dubbo.config.annotation.Service;
import ${dtoPackage}.${className}${dtoName};
import ${apiPackage}.${className}${apiName};
import ${bizPackage}.${className}${bizName};
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

//TODO fixme
import ${coreBasePackage}.Result;
import ${coreBasePackage}.BizUtil;

import java.util.List;

@Service(protocol = "dubbo")
@Slf4j
public class ${className}${implName} implements ${className}${apiName} {

    @Autowired
    ${className}${bizName} ${classNameLower}${bizName};

    @Override
    public Result<List<${className}${dtoName}>> listForApi(${className}${dtoName} dto) {
        try {
            return Result.of(${classNameLower}${bizName}.list(dto));
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"list error",e);
        }
    }

    @Override
    public Result<Integer> saveForApi(${className}${dtoName} dto){
        try {
            return Result.of(${classNameLower}${bizName}.save(dto));
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"save error",e);
        }
    }

    @Override
    public Result<Integer> editForApi(${className}${dtoName} dto) {
        try {
            return Result.of(${classNameLower}${bizName}.edit(dto));
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"edit error",e);
        }
    }

    @Override
    public Result<${className}${dtoName}> getForApi(${className}${dtoName} dto) {
        try {
            return Result.of(${classNameLower}${bizName}.get(dto));
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"get error",e);
        }
    }

//    @Override
//    public Result<Integer> removeForApi(${className}${dtoName} dto){
//        try {
//            return Result.of(${classNameLower}${bizName}.remove(dto));
//        } catch (Exception e) {
//            return BizUtil.handleBizError(log,"remove error",e);
//        }
//    }
}

