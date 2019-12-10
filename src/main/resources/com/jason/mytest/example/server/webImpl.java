[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(webImplPath + webImplPackage_dir + "/" + className + webImplName+".java")}[#t  /]
package ${webImplPackage};

import com.alibaba.dubbo.config.annotation.Service;
import ${dtoPackage}.${className}${dtoName};
import ${webApiPackage}.${className}${webApiName};
import ${bizPackage}.${className}${bizName};
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

//TODO fixme
import ${coreBasePackage}.Result;
import ${coreBasePackage}.BizUtil;
import ${coreBasePackage}.BeanUtil;
import ${serverPackage}.aop.auth.PermCheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ${voPackage}.${className}${voName};

import org.apache.commons.collections.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Service(protocol = "dubbo",version = "1.0.1")
@Slf4j
public class ${className}${webImplName} implements ${className}${webApiName} {

    @Autowired
    ${className}${bizName} ${classNameLower}${bizName};

    @Override
//    @GetMapping(value = "${ctlUrlPrefix}/list")
    @PostMapping(value = "${ctlUrlPrefix}/list")
    @PermCheck(requirePerm = "fixme")
    public Result<List<${className}${voName}>> listForWeb(${className}${dtoName} dto) {
        try {
            List<${className}${dtoName}> dtoList = ${classNameLower}${bizName}.list(dto);
            if (CollectionUtils.isEmpty(dtoList)) {
                return Result.success();
            }
            List<${className}${voName}> voList = new ArrayList<${className}${voName}>(dtoList.size());
            for (${className}${dtoName} d : dtoList) {
                ${className}${voName} vo = new ${className}${voName}();
                BeanUtil.copyBeanNotNull2Bean(d,vo);
                voList.add(vo);
            }
            return Result.of(voList);
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"list error",e);
        }
    }

    @Override
    @PermCheck(requirePerm = "fixme")
    @PostMapping(value = "${ctlUrlPrefix}/save")
    public Result<Integer> saveForWeb(${className}${dtoName} dto){
        try {
            return Result.of(${classNameLower}${bizName}.save(dto));
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"save error",e);
        }
    }

    @Override
    @PostMapping(value = "${ctlUrlPrefix}/edit")
    @PermCheck(requirePerm = "fixme")
    public Result<Integer> editForWeb(${className}${dtoName} dto) {
        try {
            return Result.of(${classNameLower}${bizName}.edit(dto));
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"edit error",e);
        }
    }

    @Override
//    @GetMapping(value = "${ctlUrlPrefix}/get")
    @PostMapping(value = "${ctlUrlPrefix}/get")
    @PermCheck(requirePerm = "fixme")
    public Result<${className}${voName}> getForWeb(${className}${dtoName} dto) {
        try {
            ${className}${dtoName} d = ${classNameLower}${bizName}.get(dto);
            if(d == null){
                return Result.success();
            }
            ${className}${voName} vo = new ${className}${voName}();
            BeanUtil.copyBeanNotNull2Bean(d,vo);
            return Result.of(vo);
        } catch (Exception e) {
            return BizUtil.handleBizError(log,"get error",e);
        }
    }

//    @Override
//    @PermCheck(requirePerm = "fixme")
//    @PostMapping(value = "${ctlUrlPrefix}/remove")
//    public Result<Integer> removeForWeb(${className}${dtoName} dto){
//        try {
//            return Result.of(${classNameLower}${bizName}.remove(dto));
//        } catch (Exception e) {
//            return BizUtil.handleBizError(log,"remove error",e);
//        }
//    }
}

