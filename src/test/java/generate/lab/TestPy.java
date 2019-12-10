package generate.lab;

import generate.GeneratorMain;
import generate.pojo.DetailPojo;
import generate.pojo.MainPojo;
import org.junit.Before;
import org.junit.Test;

/**
 * 不要操作这个类
 * 纯粹实验性质，如果稳定了，会提升为标准
 */
public class TestPy {


    String tplPath = "py/flask";

    //可以把要生成的DO类写在test/java下，方便查找（框架本身并不要求一定要这样，可以是任意路径）
    String javaSrcPathRoot = GeneratorMain.USER_DIR + "/src/test/java/com/jason/mytest/example";

    DetailPojo detailPojo = new DetailPojo();

    @Before
    public void before(){
        /**
         * 如果你的项目是标准工程，那么只需要配置这四项，其他都不用配置
         * 如果不是标准工程，代码路径比较混乱，则打开DetailPojo这个配置类
         * 看具体参数含义，每一项都是可以覆盖的。配置成符合你需求pojo
         */

        //你idea的workspace根目录
        detailPojo.setProjectRoot("/d/bfbackup/workspace-backup");

        //注意带"/",和projectRoot一起会拼接成工程的完整根路径
        detailPojo.setProjectName("/pydemo");

        //多数据源时候配置，不设置认为主数据源
//        detailPojo.setDataSourceName("");
//        detailPojo.setDataSourceName("ds_test2");
    }

    @Test
    public void testGenerateFromDB() throws Exception {
        MainPojo mainPojo = new MainPojo().setTableName("user").setCtlUrlPrefix("/user");
        GeneratorMain.generateFromDB(tplPath,mainPojo,detailPojo);
    }

    @Test
    public void testGenerateFromJavaSrc() throws Exception {
        MainPojo mainPojo = null;

        mainPojo = new MainPojo().setTableName("test_qq").setCtlUrlPrefix("/qq");
        mainPojo.setJavaSrcPath(javaSrcPathRoot + "/QqDTO.java");
        GeneratorMain.generateFromJavaSrcFile(tplPath,mainPojo,detailPojo);
    }
}