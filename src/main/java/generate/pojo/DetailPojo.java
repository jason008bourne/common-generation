package generate.pojo;

import generate.util.BeanUtil;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * 生成代码的具体配置
 * 具体含义见属性注释
 * 每个属性都提供默认值，但也都可以被覆盖
 */
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DetailPojo {

    private static final String mavenSrcPath = "src/main/java/";

    private static final String mavenResourcePath = "src/main/resources/";
    /**
     * idea工作目录
     */
    private String projectRoot = "/path/to/idea-workspace";

    /**
     * 工程名字，会和idea工作目录拼接成生成根路径
     */
    private String projectName = "/mytest-example";

    /**
     * 最终的文件生成根目录，由idea工作目录和工程名拼接而成
     */
    private String projectPath;

//---------------------------------------代码生成路径配置----------------------------------------------
    /**
     * 标准工程的maven core模块的路径(core模块的根路径)
     */
    private String corePath;

    /**
     * dao的路径(用mybatis也就是mapper.java的路径)
     */
    private String daoPath;

    /**
     * po的路径(和数据库映射的持久类的路径，有些地方叫Entity，有些地方叫DO，有些地方叫xxx)
     */
    private String poPath;

    /**
     * 自动生成的mybatis xml路径
     */
    private String dataSourceName;

    /**
     * 自动生成的mybatis xml路径
     */
    private String generateXmlPath;

    /**
     * 手工编辑的mybatis xml路径
     */
    private String manualXmlPath;

    /**
     * 自动生成的ddl建表语句路径
     */
    private String ddlPath;

    /**
     * core模块中的service路径
     */
    private String servicePath;

    /**
     * 标准工程的maven client模块路径(client模块的根路径)
     */
    private String clientPath ;

    /**
     * dto路径
     */
    private String dtoPath;

    /**
     * api路径(dubbo接口的路径)
     */
    private String apiPath;

    /**
     * vo路径(一般dto对应api，vo对应controller，但接入网关没有controller，所以vo也写在client模块)
     */
    private String voPath;

    /**
     * 传统工程没有这一项配置,接入网关,都是dubbo接口，需要和内部使用的dubbo接口分开)
     */
    private String webApiPath;

    /**
     * 标准工程的maven server模块路径(server模块的根路径)
     */
    private String serverPath;

    /**
     * controller路径(传统工程用controller，网关工程用下面的webImpl)
     */
    private String ctlPath;

    /**
     * dubbo api的实现类路径
     */
    private String implPath;

    /**
     * web api的实现类路径(接入网关工程才会用,传统工程就是controller,不会有这个)
     */
    private String webImplPath;

    /**
     * server模块的biz路径(一般建议这一层只作入参校验和对象转换，真正业务逻辑在core模块的service中实现)
     */
    private String bizPath;


//---------------------------------------java类的包路径配置----------------------------------------------
    /**
     * 所有Java代码的根package
     */
    private String rootPackage;

    /**
     * core模块Java代码的根package
     */
    private String corePackage;

    /**
     * core模块base代码的package
     */
    private String coreBasePackage;

    /**
     * dao代码的package(对应mybaties就是mapper.java)
     */
    private String daoPackage;

    /**
     * po代码的package(和数据库映射的持久类，有些地方叫Entity，有些地方叫DO，有些地方叫xxx)
     */
    private String poPackage;

    /**
     * service代码的package
     */
    private String servicePackage;

    /**
     * client模块java代码的根package
     */
    private String clientPackage;

    /**
     * client模块base代码的package
     */
    private String clientBasePackage;

    /**
     * dto代码的package
     */
    private String dtoPackage;

    /**
     * api代码的package
     */
    private String apiPackage;

    /**
     * vo代码的package
     */
    private String voPackage;

    /**
     * webApi代码的package
     */
    private String webApiPackage;

    /**
     * server模块java代码的根package
     */
    private String serverPackage;

    /**
     * server模块base代码的package
     */
    private String serverBasePackage;

    /**
     * webApi代码的package
     */
    private String ctlPackage;

    /**
     * dubbo api的实现类的package
     */
    private String implPackage;

    /**
     * web api的实现类的package(接入网关才会有)
     */
    private String webImplPackage;

    /**
     * biz的package(接入网关才会有)
     */
    private String bizPackage;

//---------------------------------------java类的类名后缀配置----------------------------------------------

    /**
     * dao类的后缀
     */
    private String daoName = "Mapper";

    /**
     * po类的后缀
     */
    private String poName = "DO";

    /**
     * service类的后缀
     */
    private String serviceName = "Service";

    /**
     * 自动生成的mybatis xml文件的后缀
     */
    private String generateXmlName = "Mapper";

    /**
     * 手工编辑的mybatis xml文件的后缀
     */
    private String manualXmlName;

    /**
     * dto类的后缀
     */
    private String dtoName = "DTO";

    /**
     * api类的后缀
     */
    private String apiName = "Api";

    /**
     * webApi类的后缀
     */
    private String webApiName = "Web";

    /**
     * vo类的后缀
     */
    private String voName = "VO";

    /**
     * controller类的后缀
     */
    private String ctlName = "Ctl";

    /**
     * api 实现类的后缀
     */
    private String implName = "ApiImpl";

    /**
     * Web api 实现类的后缀
     */
    private String webImplName = "WebImpl";

    /**
     * biz类的后缀
     */
    private String bizName = "Biz";

    /**
     * base的基础名
     */
    private String baseNamePrefix = "Base";

    /**
     * baseDao类名
     */
    private String baseDao;

    /**
     * basePo类名
     */
    private String basePo;

    /**
     * baseService类名
     */
    private String baseService;

    /**
     * baseDto类名
     */
    private String baseDto;

    /**
     * baseBiz类名
     */
    private String baseBiz;

//---------------------------------------工程外扩展属性----------------------------------------------
    /**
     * Model的主键id的类型
     */
    private String pkJavaType = "Long";

    /**
     * 上面但所有属性都是根据标准工程提供的，并且和标准freemarker 模版配套
     * 但是难免有些非标准工程，或者有自己但标准工程定义，需要额外属性
     * 任何塞到这个集合中的属性都可以在模版中访问到
     */
    private Properties otherProperty = new Properties();

    public String getProjectRoot() {
        if(StringUtils.isBlank(projectRoot)){
            throw new RuntimeException("projectRoot不能为空");
        }
        return projectRoot;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectPath() {
        if(StringUtils.isBlank(projectPath)){
            return getProjectRoot() + getProjectName();
        }
        return projectPath;
    }

    public String getCorePath() {
        if(StringUtils.isBlank(corePath)){
            return getProjectPath() + getProjectName()+ "-core/";
        }
        return corePath;
    }

    public String getDaoPath() {
        if(StringUtils.isBlank(daoPath)){
            return getCorePath()+ mavenSrcPath;
        }
        return daoPath;
    }

    public String getPoPath() {
        if(StringUtils.isBlank(poPath)){
            return getCorePath()+ mavenSrcPath;
        }
        return poPath;
    }

    public String getDataSourceName() {
        if(StringUtils.isBlank(dataSourceName)){
            throw new RuntimeException("dataSourceName不能为空");
        }
        return dataSourceName;
    }

    public String getGenerateXmlPath() {
        if(StringUtils.isBlank(generateXmlPath)){
            return getCorePath() + mavenResourcePath + "mapper/generate/" + getDataSourceName() ;
        }
        return generateXmlPath;
    }

    public String getManualXmlPath() {
        if(StringUtils.isBlank(manualXmlPath)){
            return getCorePath() + mavenResourcePath + "mapper/manual/" + getDataSourceName() ;
        }
        return manualXmlPath;
    }

    public String getDdlPath() {
        if(StringUtils.isBlank(ddlPath)){
            return getCorePath() + mavenResourcePath + "sql";
        }
        return ddlPath;
    }

    public String getServicePath() {
        if(StringUtils.isBlank(servicePath)){
            return getCorePath()+ mavenSrcPath;
        }
        return servicePath;
    }

    public String getClientPath() {
        if(StringUtils.isBlank(clientPath)){
            return getProjectPath()+ getProjectName() + "-client/";
        }
        return clientPath;
    }

    public String getDtoPath() {
        if(StringUtils.isBlank(dtoPath)){
            return getClientPath()+ mavenSrcPath;
        }
        return dtoPath;
    }

    public String getApiPath() {
        if(StringUtils.isBlank(apiPath)){
            return getClientPath()+ mavenSrcPath;
        }
        return apiPath;
    }

    public String getVoPath() {
        if(StringUtils.isBlank(voPath)){
            return getClientPath()+ mavenSrcPath;
        }
        return voPath;
    }

    public String getWebApiPath() {
        if(StringUtils.isBlank(webApiPath)){
            return getClientPath()+ mavenSrcPath;
        }
        return webApiPath;
    }

    public String getServerPath() {
        if(StringUtils.isBlank(serverPath)){
            return getProjectPath() + getProjectName() +"-server/";
        }
        return serverPath;
    }

    public String getCtlPath() {
        if(StringUtils.isBlank(ctlPath)){
            return getServerPath()+ mavenSrcPath;
        }
        return ctlPath;
    }

    public String getImplPath() {
        if(StringUtils.isBlank(implPath)){
            return getServerPath()+ mavenSrcPath;
        }
        return implPath;
    }

    public String getWebImplPath() {
        if(StringUtils.isBlank(webImplPath)){
            return getServerPath()+ mavenSrcPath;
        }
        return webImplPath;
    }

    public String getBizPath() {
        if(StringUtils.isBlank(bizPath)){
            return getServerPath()+ mavenSrcPath;
        }
        return bizPath;
    }

    public String getRootPackage() {
        if(StringUtils.isBlank(rootPackage)){
            throw new RuntimeException("rootPackage不能为空");
        }
        return rootPackage;
    }

    public String getCorePackage() {
        if(StringUtils.isBlank(corePackage)){
            return getRootPackage()+ ".core";
        }
        return corePackage;
    }

    public String getCoreBasePackage() {
        if(StringUtils.isBlank(coreBasePackage)){
            return getCorePackage()+ ".base";
        }
        return coreBasePackage;
    }

    public String getDaoPackage() {
        if(StringUtils.isBlank(daoPackage)){
            return getCorePackage()+ ".dao";
        }
        return daoPackage;
    }

    public String getPoPackage() {
        if(StringUtils.isBlank(poPackage)){
            return getCorePackage()+ ".dataobject";
        }
        return poPackage;
    }

    public String getServicePackage() {
        if(StringUtils.isBlank(servicePackage)){
            return getCorePackage()+ ".service";
        }
        return servicePackage;
    }

    public String getClientPackage() {
        if(StringUtils.isBlank(clientPackage)){
            return getRootPackage()+ ".client";
        }
        return clientPackage;
    }

    public String getClientBasePackage() {
        if(StringUtils.isBlank(clientBasePackage)){
            return getClientPackage()+ ".base";
        }
        return clientBasePackage;
    }

    public String getDtoPackage() {
        if(StringUtils.isBlank(dtoPackage)){
            return getClientPackage()+ ".dto";
        }
        return dtoPackage;
    }

    public String getApiPackage() {
        if(StringUtils.isBlank(apiPackage)){
            return getClientPackage()+ ".api";
        }
        return apiPackage;
    }

    public String getVoPackage() {
        if(StringUtils.isBlank(voPackage)){
            return getClientPackage()+ ".vo";
        }
        return voPackage;
    }

    public String getWebApiPackage() {
        if(StringUtils.isBlank(webApiPackage)){
            return getClientPackage()+ ".web";
        }
        return webApiPackage;
    }

    public String getServerPackage() {
        if(StringUtils.isBlank(serverPackage)){
            return getRootPackage()+ ".server";
        }
        return serverPackage;
    }

    public String getServerBasePackage() {
        if(StringUtils.isBlank(serverBasePackage)){
            return getServerPackage()+ ".base";
        }
        return serverBasePackage;
    }

    public String getCtlPackage() {
        if(StringUtils.isBlank(ctlPackage)){
            return getServerPackage()+ ".controller";
        }
        return ctlPackage;
    }

    public String getImplPackage() {
        if(StringUtils.isBlank(implPackage)){
            return getServerPackage()+ ".provider";
        }
        return implPackage;
    }

    public String getWebImplPackage() {
        if(StringUtils.isBlank(webImplPackage)){
            return getServerPackage()+ ".webimpl";
        }
        return webImplPackage;
    }

    public String getBizPackage() {
        if(StringUtils.isBlank(bizPackage)){
            return getServerPackage()+ ".biz";
        }
        return bizPackage;
    }

    public String getDaoName() {
        return daoName;
    }

    public String getPoName() {
        return poName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getGenerateXmlName() {
        return generateXmlName;
    }

    public String getManualXmlName() {
        if(StringUtils.isBlank(manualXmlName)){
            return getGenerateXmlName();
        }
        return manualXmlName;
    }

    public String getDtoName() {
        return dtoName;
    }

    public String getApiName() {
        return apiName;
    }

    public String getWebApiName() {
        return webApiName;
    }

    public String getVoName() {
        return voName;
    }

    public String getCtlName() {
        return ctlName;
    }

    public String getImplName() {
        return implName;
    }

    public String getWebImplName() {
        return webImplName;
    }

    public String getBizName() {
        return bizName;
    }

    public String getBaseNamePrefix() {
        return baseNamePrefix;
    }

    public String getBaseDao() {
        return getBaseNamePrefix() + getDaoName();
    }

    public String getBasePo() {
        return getBaseNamePrefix() + getPoName();
    }

    public String getBaseService() {
        return getBaseNamePrefix() + getServiceName();
    }

    public String getBaseDto() {
        return getBaseNamePrefix() + getDtoName();
    }

    public String getBaseBiz() {
        return getBaseNamePrefix() + getBizName();
    }

    public String getPkJavaType() {
        return pkJavaType;
    }

    public Properties getOtherProperty() {
        return otherProperty;
    }

    /**
     * 底层会调用这个merge过后的property塞到模版中
     * @return
     */
    public Properties toFinal(){
        Properties finalProperty = BeanUtil.copyBeanNotNull2Property(this,"otherProperty");
        if(!otherProperty.isEmpty()){
            finalProperty.putAll(otherProperty);
        }
        return finalProperty;
    }
}
