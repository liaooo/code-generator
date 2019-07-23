package com.liaojs.codegenerator.model;

import java.util.List;

public class Tables {

    private String tableName;

    private String tableComment;

    private List<Columns> tableColumns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<Columns> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<Columns> tableColumns) {
        this.tableColumns = tableColumns;
    }
}
