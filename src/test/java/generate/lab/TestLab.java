package generate.lab;

import generate.GeneratorMain;
import generate.pojo.DetailPojo;
import generate.pojo.MainPojo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 不要操作这个类
 * 纯粹实验性质，如果稳定了，会提升为标准
 */
public class TestLab {


//    String tplPath = "com/jason/mytest/example";

    //没有接入网关的传统web工程从"com/jason/mytest/web" 下拷贝修改，或者直接用这套模版
    String tplPath = "com/jason/mytest/web";

    //可以把要生成的DO类写在test/java下，方便查找（框架本身并不要求一定要这样，可以是任意路径）
    String javaSrcPathRoot = GeneratorMain.USER_DIR + "/src/test/java/com/jason/mytest/example";

    DetailPojo detailPojo = new DetailPojo();

    String poPath = "/d/bfbackup/workspace-backup/kof-gd/kof-gd-core/src/main/java/com/jason/kof/gd/core/dataobject";

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
        detailPojo.setProjectName("/kof-gd");

        //影响生成mybatis xml的路径
        detailPojo.setDataSourceName("gd");

        //你项目代码的根包
        detailPojo.setRootPackage("com.jason.kof.gd");
    }

    @Test
    public void testGenerateFromDB() throws Exception {
        MainPojo mainPojo = new MainPojo().setTableName("user").setCtlUrlPrefix("/user");
        GeneratorMain.generateFromDB(tplPath,mainPojo,detailPojo);
    }

    @Test
    public void testGenerateFromJavaSrc() throws Exception {
        MainPojo mainPojo = null;

        mainPojo = new MainPojo().setTableName("user").setCtlUrlPrefix("/user");
        mainPojo.setJavaSrcPath(poPath + "User.java");
        GeneratorMain.generateFromJavaSrcFile(tplPath,mainPojo,detailPojo);
    }

    @Test
    public void testGenerateFromJavaSrcBatch() throws Exception {
        List<MainPojo> list = new ArrayList<MainPojo>();
        MainPojo mainPojo = null;

        mainPojo = new MainPojo().setTableName("user").setCtlUrlPrefix("/user");
        mainPojo.setJavaSrcPath(poPath + "User.java");
        list.add(mainPojo);

        GeneratorMain.generateFromJavaSrcFile(tplPath,list,detailPojo);
    }
}