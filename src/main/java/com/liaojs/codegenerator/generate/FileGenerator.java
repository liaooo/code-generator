package com.liaojs.codegenerator.generate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liaojs.codegenerator.constant.CommonConstant;
import com.liaojs.codegenerator.core.model.ClassInfo;
import com.liaojs.codegenerator.core.model.FieldInfo;
import com.liaojs.codegenerator.core.util.StringUtils;
import com.liaojs.codegenerator.mapper.TableInfoMapper;
import com.liaojs.codegenerator.model.Columns;
import com.liaojs.codegenerator.model.Tables;
import com.liaojs.codegenerator.util.FileByteUtil;
import com.liaojs.codegenerator.util.FreemarkerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 生成代码并保存
 */
@Component
public class FileGenerator implements CommandLineRunner {


    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Autowired
    private FreemarkerTool freemarkerTool;

    @Override
    public void run(String... strings) throws Exception {

        List<Tables> tables = tableInfoMapper.listTable(CommonConstant.TABLE_SCHEMA);
        System.out.println(new ObjectMapper().writeValueAsString(tables));
        for (Tables tableInfo : tables) {
            printTemplate(tableInfo);
        }
    }

    private void printTemplate(Tables tableInfo) throws Exception {
        for (Map.Entry<String, String> entry : CommonConstant.TEMPLATE_MAP.entrySet()) {
            Map<String, Object> params = new HashMap<>();
            ClassInfo classInfo = buildClassInfo(tableInfo);
            classInfo.setPackageName(CommonConstant.PACKAGE_NAME + "." + entry.getKey().replace("/", "."));
            params.put("classInfo", classInfo);
            String template = entry.getValue();
            String content = freemarkerTool.processString(template, params);
            System.out.println(tableInfo.getTableName() + "-" + entry.getKey() + "已生成");
            FileByteUtil.byte2File(content.getBytes(),
                    CommonConstant.SAVE_PATH + File.separator + entry.getKey(),
                    classInfo.getClassName() + CommonConstant.FILE_SUFFIX_MAP.get(entry.getKey()));
        }

    }

    private ClassInfo buildClassInfo(Tables tableInfo) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.setTableName(tableInfo.getTableName());
        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableInfo.getTableName()));
        classInfo.setClassName(className);
        classInfo.setClassComment(tableInfo.getTableComment());

        List<FieldInfo> fieldList = new ArrayList<>();
        for (Columns column : tableInfo.getTableColumns()) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setColumnName(column.getColumnName());
            fieldInfo.setFieldComment(column.getColumnComment());
            String fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(column.getColumnName()));
            fieldInfo.setFieldName(fieldName);
            String fieldClass = Object.class.getSimpleName();
            String dataType = column.getDataType();
            if (dataType.startsWith("int")
                    || dataType.startsWith("tinyint")
                    || dataType.startsWith("smallint")) {
                fieldClass = Integer.TYPE.getSimpleName();
            } else if (dataType.startsWith("bigint")) {
                fieldClass = Long.TYPE.getSimpleName();
            } else if (dataType.startsWith("float")) {
                fieldClass = Float.TYPE.getSimpleName();
            } else if (dataType.startsWith("double")) {
                fieldClass = Double.TYPE.getSimpleName();
            } else if (dataType.startsWith("datetime")
                    || dataType.startsWith("timestamp")) {
                fieldClass = Date.class.getSimpleName();
            } else if (dataType.startsWith("decimal")) {
                fieldClass = BigDecimal.class.getSimpleName();
            } else if (dataType.startsWith("varchar")
                    || dataType.startsWith("text")) {
                fieldClass = String.class.getSimpleName();
            }
            fieldInfo.setFieldClass(fieldClass);
            fieldList.add(fieldInfo);
        }
        classInfo.setFieldList(fieldList);
        return classInfo;
    }
}
