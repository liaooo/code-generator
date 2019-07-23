package com.liaojs.codegenerator.constant;

import java.util.HashMap;
import java.util.Map;

public class CommonConstant {

    public static final String TABLE_SCHEMA = "gys";

    public static final String SAVE_PATH = "D:\\项目\\code-generator\\code";

    public static final Map<String, String> TEMPLATE_MAP = new HashMap<>();

    static {
        TEMPLATE_MAP.put("controller", "code-template/controller.ftl");
        TEMPLATE_MAP.put("dao", "code-template/dao.ftl");
        TEMPLATE_MAP.put("entity", "code-template/entity.ftl");
        TEMPLATE_MAP.put("mybatis", "code-template/mybatis.ftl");
        TEMPLATE_MAP.put("service", "code-template/service.ftl");
        TEMPLATE_MAP.put("service/impl", "code-template/service_impl.ftl");
    }

    public static final Map<String, String> FILE_SUFFIX_MAP = new HashMap<>();

    static {
        FILE_SUFFIX_MAP.put("controller", "Controller.java");
        FILE_SUFFIX_MAP.put("dao", "Dao.java");
        FILE_SUFFIX_MAP.put("entity", ".java");
        FILE_SUFFIX_MAP.put("mybatis", "Mapper.xml");
        FILE_SUFFIX_MAP.put("service", "Service.java");
        FILE_SUFFIX_MAP.put("service/impl", "ServiceImpl.java");
    }

    public static final String PACKAGE_NAME = "com.liaojs.gys";
}
