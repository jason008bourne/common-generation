
[#assign className=table.className  /]
[#assign classNameLower = table.classNameFirstLower  /]

[#--
#t 指示FreeMarker去忽略标记中行的特定的空白
--]
${gg.setOverride(false)}[#t  /]
${gg.setOutputFile(serverPath + "src/test/java/" + serverPackage_dir + "/test/Test" + className + "Server.java")}[#t  /]
package ${serverPackage}.test;

import com.alibaba.dubbo.config.annotation.Reference;
import ${dtoPackage}.${className}${dtoName};
import ${apiPackage}.${className}${apiName};
import ${serverPackage}.BaseServerTest;
import org.junit.Test;
import org.junit.Assert;

public class Test${className}Server extends BaseServerTest {

    @Reference
    ${className}${apiName} ${classNameLower}${apiName};

[#if pkJavaType=='Long']
    static ${pkJavaType} pkId = 1l;
[#elseif pkJavaType=='Integer']
    static ${pkJavaType} pkId = 1;
[#else]
    static ${pkJavaType} pkId = "test111";
[/#if]

    @Test
    public void testList(){
        //这行会打印api接口地址,如果接入了网关，在接口前面加/gw/appName
        print("-------------------网关接口地址------------------------");
        print("/gw${projectName}${ctlUrlPrefix}/list");
        print("-------------------普通web接口地址------------------------");
        print("${ctlUrlPrefix}/list");

        //这行会执行单元测试，如果正常，会返回结果，并在控制台打印api返回结果
        printJSON(${classNameLower}${apiName}.listForApi(new ${className}${dtoName}()));
        //复制控制台打印的接口地址和参数，以及返回结果等信息，就可以去yapi写接口了
    }

    @Test
    public void testSave(){
        ${className}${dtoName} p = new ${className}${dtoName}();
    [#list table.columns as field]
        [#if field.asType=='String']
        p.set${field.columnNameFirstUpper}("字符串");
        [#elseif field.asType=='Long']
        p.set${field.columnNameFirstUpper}(1L);
        [#elseif field.asType=='Integer']
        p.set${field.columnNameFirstUpper}(1);
        [#elseif field.asType=='Boolean']
        p.set${field.columnNameFirstUpper}(false);
        [#elseif field.asType=='Enum']
        p.set${field.columnNameFirstUpper}(${field.simpleJavaType}.);
        [#elseif field.asType=='Date']
        p.set${field.columnNameFirstUpper}(new Date());
        [#elseif field.asType=='BigDecimal']
        p.set${field.columnNameFirstUpper}(new BigDecimal("1.22"));
        [#elseif field.asType=='Object']
        ${field.simpleJavaType} obj = new ${field.simpleJavaType}();
        p.set${field.columnNameFirstUpper}(obj);
        [#else]
        //TODO fixme
        p.set${field.columnNameFirstUpper}("字符串");
        [/#if]
    [/#list]
        //这行会打印api接口地址,如果接入了网关，在接口前面加/gw/appName
        print("-------------------网关接口地址------------------------");
        print("/gw${projectName}${ctlUrlPrefix}/save");
        print("-------------------普通web接口地址------------------------");
        print("${ctlUrlPrefix}/save");

        //这行会打印api接口参数
        printJSON(p);

        //这行会执行单元测试，如果正常，会返回结果，并在控制台打印api返回结果
//        printJSON(${classNameLower}${apiName}.saveForApi(p));
        //复制控制台打印的接口地址和参数，以及返回结果等信息，就可以去yapi写接口了
    }

    @Test
    public void testEdit()
    {
        ${className}${dtoName} p = new ${className}${dtoName}();
        p.setId(pkId);
    [#list table.columns as field]
        [#if field.asType=='String']
        p.set${field.columnNameFirstUpper}("字符串");
        [#elseif field.asType=='Long']
        p.set${field.columnNameFirstUpper}(1L);
        [#elseif field.asType=='Integer']
        p.set${field.columnNameFirstUpper}(1);
        [#elseif field.asType=='Boolean']
        p.set${field.columnNameFirstUpper}(false);
        [#elseif field.asType=='Enum']
        p.set${field.columnNameFirstUpper}(${field.simpleJavaType}.);
        [#elseif field.asType=='Date']
        p.set${field.columnNameFirstUpper}(new Date());
        [#elseif field.asType=='BigDecimal']
        p.set${field.columnNameFirstUpper}(new BigDecimal("1.22"));
        [#elseif field.asType=='Object']
        ${field.simpleJavaType} obj = new ${field.simpleJavaType}();
        p.set${field.columnNameFirstUpper}(obj);
        [#else]
        //TODO fixme
        p.set${field.columnNameFirstUpper}("字符串");
        [/#if]
    [/#list]
        //这行会打印api接口地址,如果接入了网关，在接口前面加/gw/appName
        print("-------------------网关接口地址------------------------");
        print("/gw${projectName}${ctlUrlPrefix}/edit");
        print("-------------------普通web接口地址------------------------");
        print("${ctlUrlPrefix}/edit");

        //这行会打印api接口参数
        printJSON(p);

        //这行会执行单元测试，如果正常，会返回结果，并在控制台打印api返回结果
//        printJSON(${classNameLower}${apiName}.editForApi(p));
        //复制控制台打印的接口地址和参数，以及返回结果等信息，就可以去yapi写接口了
    }

    @Test
    public void testRemove()
    {
        ${className}${dtoName} p = new ${className}${dtoName}();
        p.setId(pkId);
        //这行会打印api接口地址,如果接入了网关，在接口前面加/gw/appName
        print("-------------------网关接口地址------------------------");
        print("/gw${projectName}${ctlUrlPrefix}/remove");
        print("-------------------普通web接口地址------------------------");
        print("${ctlUrlPrefix}/remove");

        //这行会打印api接口参数
        printJSON(p);

        //这行会执行单元测试，如果正常，会返回结果，并在控制台打印api返回结果
//        printJSON(${classNameLower}${apiName}.removeForApi(p));
        //复制控制台打印的接口地址和参数，以及返回结果等信息，就可以去yapi写接口了
    }

    @Test
    public void testGet()
    {
        ${className}${dtoName} p = new ${className}${dtoName}();
        p.setId(pkId);
        //这行会打印api接口地址,如果接入了网关，在接口前面加/gw/appName
        print("-------------------网关接口地址------------------------");
        print("/gw${projectName}${ctlUrlPrefix}/get");
        print("-------------------普通web接口地址------------------------");
        print("${ctlUrlPrefix}/get");

        //这行会打印api接口参数
        printJSON(p);

        //这行会执行单元测试，如果正常，会返回结果，并在控制台打印api返回结果
        printJSON(${classNameLower}${apiName}.getForApi(p));
        //复制控制台打印的接口地址和参数，以及返回结果等信息，就可以去yapi写接口了
    }
}
