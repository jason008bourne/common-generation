[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(servicePath + servicePackage_dir + "/" + className + serviceName+".java")}[#t  /]
package ${servicePackage};

import ${coreBasePackage}.${baseService};
import ${daoPackage}.${dataSourceName}.${className}${daoName};
import ${poPackage}.${dataSourceName}.${className}${poName};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

//fixme TODO
import ${corePackage}.config.MybatisConfig;
import ${coreBasePackage}.BizUtil;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;

@Service
@Slf4j
public class ${className}${serviceName} extends ${baseService}<${className}${poName},${pkJavaType}> {

    @Autowired
    ${className}${daoName} ${classNameLower}${daoName};

    @PostConstruct
    public void init(){
        setBaseMapper(${classNameLower}${daoName});
    }

    @Transactional(MybatisConfig.BEAN_TRAN_MANAGE_NAME)
    public Integer save(${className}${poName} d) {
        /**
         * balabala 写你的逻辑
         */
        return insertSelective(d);
    }

    @Transactional(MybatisConfig.BEAN_TRAN_MANAGE_NAME)
    public Integer edit(${className}${poName} input) {
        ${className}${poName} d = getOneByPkId(input.getId());
        BizUtil.assertCondition(d == null,"id为" + input.getId()+"的实体不存在");
        /**
         * balabala 写你的逻辑
         */
        return updateSelective(d);
    }
}

