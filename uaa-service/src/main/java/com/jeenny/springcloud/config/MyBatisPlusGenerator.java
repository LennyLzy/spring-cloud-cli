package com.jeenny.springcloud.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/19.
 */
public class MyBatisPlusGenerator {

    private static String MODULE_NAME = "com.jeenny.springcloud";    //包
    private static String AUTHOR_NAME = "Lenny";     //作者
    private static String TABLE_PREFIX = "";                     //
    private static String[] tableNames ={};
    private static File FILE = new File(System.getProperty("user.dir")+"\\src\\main");
    private static String OUTPUT_PATH = FILE.getAbsolutePath();

    public static void excute(){
        AutoGenerator autoGenerator = new AutoGenerator();

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setOutputDir(OUTPUT_PATH + "\\java")
                .setFileOverride(false)
                .setActiveRecord(false)
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setEnableCache(false)
                .setAuthor(AUTHOR_NAME)
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
        autoGenerator.setGlobalConfig(globalConfig);

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("682630")
                .setUrl("jdbc:mysql://127.0.0.1:3306/springcloud?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false");
        autoGenerator.setDataSource(dataSourceConfig);

        //命名策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setTablePrefix(TABLE_PREFIX)   //设置表前缀
                .setInclude(tableNames)       //需要生成的表名，空为全部，多个用逗号隔开
                .setNaming(NamingStrategy.underline_to_camel); //修改替换成你需要的表名，多个表名传数组
        autoGenerator.setStrategy(strategyConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        autoGenerator.setCfg(cfg);

        //文件输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return OUTPUT_PATH + "/resources/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        //模版配置
        autoGenerator.setTemplate(new TemplateConfig().setXml(null));

        //包信息配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(MODULE_NAME)
                .setController("controller")
                .setEntity("model.entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("serviceimpl");
        autoGenerator.setPackageInfo(packageConfig);

        autoGenerator.execute();
    }
}
