package generate;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import generate.pojo.DetailPojo;
import generate.pojo.MainPojo;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorMain {

    private static final Set<String> columnIgnore = new HashSet<String>();

    private static final Set<String> POJO_NAMES = new HashSet<String>();

    static {
        Collections.addAll(columnIgnore,"id", "create_time", "update_time");
        Collections.addAll(POJO_NAMES,"Pojo","Do","Dto","Po","Entity","Vo");
    }

    public static final String USER_DIR = System.getProperty("user.dir").replace("\\", "/");

    private static final String templateRootPath = USER_DIR + "/src/main/resources/";

    public static void generateFromDB(String templatePath,MainPojo pojo,DetailPojo detail)
            throws Exception {
        GeneratorFacade g = new GeneratorFacade();
        g.getGenerator().setTemplateRootDir(templateRootPath + templatePath);
        g.getGenerator().setOutRootDir(USER_DIR);
        detail.getOtherProperty().setProperty("ctlUrlPrefix",pojo.getCtlUrlPrefix());
        GeneratorProperties.putAll(detail.toFinal());
        g.generateByTableWithIgnore(pojo.getTableName(),columnIgnore);
    }

    public static void generateFromJavaSrcFile(String templatePath,MainPojo pojo,DetailPojo detail)
            throws Exception {
        if(StringUtils.isBlank(pojo.getJavaSrcPath())){
            throw new RuntimeException("java源文件路径不能为空");
        }
        Table table = genTable(pojo.getJavaSrcPath());
        table.setSqlName(pojo.getTableName());
        detail.getOtherProperty().setProperty("ctlUrlPrefix",pojo.getCtlUrlPrefix());
        generateCustom(templatePath,table,detail.toFinal());
    }

    public static void generateFromJavaSrcFile(String templatePath, List<MainPojo> pojoList,DetailPojo detail)
            throws Exception {
        for (MainPojo pojo : pojoList){
            generateFromJavaSrcFile(templatePath,pojo,detail);
        }
    }

    public static void generateCustom(String templatePath, Table table,Properties param)
            throws Exception {
        GeneratorFacade g = new GeneratorFacade();
        g.getGenerator().setTemplateRootDir(templateRootPath + templatePath);
        g.getGenerator().setOutRootDir(USER_DIR);
        GeneratorProperties.putAll(param);
        g.generateByTableWithIgnore(table,columnIgnore);
    }

    //查找 字段上  /**/注释的部分
    private static Pattern descriptionPattern = Pattern.compile("\\/\\*\\*.*?\\*\\/", Pattern.MULTILINE
            | Pattern.DOTALL);// 正则表达式
    // 找到所有注释开始的字段
    //  /**.*;
    private static Pattern outPattern = Pattern.compile("\\/\\*\\*.*?(\\;|\\{)", Pattern.MULTILINE | Pattern.DOTALL);// 正则表达式

    // 原始的郑则表达  "\\b\\w+(?=\\s*\\;)"  解释：\b匹配单词开始,\w+匹配字母，数字或下划线（也就是匹配变量名）
    // (?=\\s*\\;) 意思是后面跟着空白，换行，制表符等一个或多个（主要怕有些不规范的人变量名后面打几个空格）
    // 仍然匹配不了变量赋初始值的情况（即后面有"="的情况）

    private static Pattern fieldPattern = Pattern.compile("(?<=(private|public|protected)\\s{0,})(\\w+)\\s+(\\w+)",
            Pattern.MULTILINE | Pattern.DOTALL);// 正则表达式

    /**
     * Pattern.MULTILINE 让 ^ 匹配每行的行首，$ 匹配每行的行尾 没有Pattern.MULTILINE ^ 整个字符串的开始 $
     * 匹配整个字符串的行尾
     *
     * 所以除非要在行首尾，或者整个文本的收尾匹配字符，否则这两个操作符基本用不到 如果这两个操作符不用，那么Pattern.MULTILINE设置与否都没有意义
     *
     * @author Administrator
     *
     */
    private static Table genTable(String javaSrcFilePath) {
        Table table = new Table();
        //原始文件可能带通用POJO后缀，我们只需要ClassName
        String className = getClassNameFromFile(javaSrcFilePath);
        for (String name:POJO_NAMES ) {
            if(StringUtils.endsWithIgnoreCase(className,name)){
                className = className.substring(0,className.length()- name.length());
                break;
            }
        }
        table.setClassName(className);
        LinkedHashSet<Column> columns = new LinkedHashSet<Column>();
        table.setColumns(columns);
        String fileStr = getFileString(javaSrcFilePath);
        Matcher matcher = outPattern.matcher(fileStr);// 操作的字符串
        while (matcher.find()) {
            String findStr = matcher.group();
            // 有可能是类，也有可能是方法
            if (findStr.contains("{")) {
                // class的注释
                if (findStr.contains("class")) {
//                    // 查找中文
//                    Pattern descriptionPattern = Pattern.compile(
//                            "[\\u4e00-\\u9fa5]+", Pattern.MULTILINE
//                                    | Pattern.DOTALL);// 正则表达式
                    Matcher descriptionMatcher = descriptionPattern
                            .matcher(findStr);// 操作的字符串
                    if (descriptionMatcher.find()) {
                        table.setRemarks(getCommentContent(descriptionMatcher.group()));
                    }
                }
            }
            //排除method
            else if (!findStr.contains("{")) {
                Matcher descriptionMatcher = descriptionPattern
                        .matcher(findStr);// 操作的字符串
                String fieldCommet = null;
                if (descriptionMatcher.find()) {
                    fieldCommet = descriptionMatcher.group();
                }
                Matcher nameMatcher = fieldPattern.matcher(findStr.substring(fieldCommet.length()));// 操作的字符串
                if (nameMatcher.find()) {
                    String type = nameMatcher.group(2);
                    String name = nameMatcher.group(3);
                    Column column = new Column(name,type,getCommentContent(fieldCommet));
                    columns.add(column);
                }
            }
        }
        return table;
    }

    private static String getFileString(String filePath) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));
            int readLenth = 0;
            char[] bufferedByte = new char[1024 * 10];
            while ((readLenth = reader.read(bufferedByte)) > 0) {
                sb.append(bufferedByte, 0, readLenth);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        // System.out.println(str);
        return str;
    }

    private static String getCommentContent(String input)
    {
        if(StringUtils.isBlank(input))
        {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c=='\r' || c=='\n'){
                continue;
            }
            if (c=='/' || c=='*'){
                continue;
            }
            sb.append(c);
        }
        return sb.toString().trim();
    }

    private static String getClassNameFromFile(String input){
        if(StringUtils.isBlank(input)){
            return input;
        }
        String fileName = getFilename(input);
        return StringUtils.split(fileName,".")[0];
    }

    private static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.replace("\\","/").lastIndexOf("/");
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    public static void main(String[] args) {

        String className = "类目层级:FIRST(1,一级类目)|SECOND(2,二级类目)|THIRD(3,三级类目)";
        System.out.println(className.replace("|",";"));
    }
}
