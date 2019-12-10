[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(bizPath + bizPackage_dir + "/" + className + bizName+".java")}[#t  /]
package ${bizPackage};

//TODO fixme
import ${coreBasePackage}.BizUtil;

import ${serverBasePackage}.${baseBiz};
import ${servicePackage}.${className}${serviceName};
import ${dtoPackage}.${className}${dtoName};
import ${voPackage}.${className}${voName};
import ${poPackage}.${dataSourceName}.${className}${poName};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;

import java.util.List;

@Service
@Slf4j
public class ${className}${bizName} extends ${baseBiz}<${className}${dtoName},${className}${poName},${pkJavaType}> {

    @Autowired
    ${className}${serviceName} ${classNameLower}${serviceName};

    @PostConstruct
    public void init(){
        setBaseService(${classNameLower}${serviceName});
    }

//------建议无论是web还是dubbo，参数校验都在这里做，收敛。做完校验，传到core中多service作具体业务逻辑实现------------
    public List<${className}${dtoName}> list(${className}${dtoName} dto) {
        BizUtil.debugJson(log,dto);
        return listByWhere(dto);
    }

    public Integer save(${className}${dtoName} dto) {
        BizUtil.debugJson(log,dto);
        //TODO fixme  字段为空的判断按照需求保留校验逻辑
    [#list table.columns as field]
        [#if field.asType=='String']
        BizUtil.assertNotEmptyStr(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Long']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Integer']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Boolean']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Enum']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Date']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='BigDecimal']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Object']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#else]
        //模版所不认识的类型 TODO fixme
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [/#if]
    [/#list]
        return ${classNameLower}${serviceName}.save(convertDtoToPo(dto));
    }

    public Integer edit(${className}${dtoName} dto) {
        BizUtil.debugJson(log,dto);
        //TODO fixme  字段为空的判断按照需求保留校验逻辑
        BizUtil.assertNotNull(dto.getId(),"id");
    [#list table.columns as field]
        [#if field.asType=='String']
        BizUtil.assertNotEmptyStr(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Long']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Integer']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Boolean']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Enum']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Date']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='BigDecimal']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#elseif field.asType=='Object']
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [#else]
        //模版所不认识的类型 TODO fixme
        BizUtil.assertNotNull(dto.get${field.columnNameFirstUpper}(),"字段中文名");
        [/#if]
    [/#list]
        return ${classNameLower}${serviceName}.edit(convertDtoToPo(dto));
    }

    public Integer remove(${className}${dtoName} dto) {
        BizUtil.debugJson(log,dto);
        BizUtil.assertNotNull(dto.getId(),"id");
        return ${classNameLower}${serviceName}.removeByPkId(dto.getId());
    }

    public ${className}${dtoName} get(${className}${dtoName} dto) {
        BizUtil.debugJson(log,dto);
        return getOneByWhere(dto);
    }
}

