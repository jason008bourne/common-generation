package generate.pojo;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;

@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class MainPojo {
    /**
     * 数据库表的名字
     */
    private String tableName;

    /**
     * web controller的requestMapping中url
     */
    private String ctlUrlPrefix;

    /**
     * java pojo类的绝对路径（pojo类和字段都必须要有标准java注释）
     */
    private String javaSrcPath;

    public String getTableName() {
        if(StringUtils.isBlank(tableName)){
            throw new RuntimeException("tableName不能为空");
        }
        return tableName;
    }

    public String getCtlUrlPrefix() {
        if(StringUtils.isBlank(ctlUrlPrefix)){
            throw new RuntimeException("controller前缀不能为空");
        }
        return ctlUrlPrefix;
    }

    public String getJavaSrcPath() {
        return javaSrcPath;
    }
}
