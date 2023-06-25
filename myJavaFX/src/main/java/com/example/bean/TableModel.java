package com.example.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/25
 */
public class TableModel {
    // 时间Label模型
    private StringProperty time = new SimpleStringProperty();
    // 表格组合TableColumnModel模型
    private ObservableList<TableColumnModel> tableList = FXCollections.observableArrayList();

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public ObservableList<TableColumnModel> getTableList() {
        return tableList;
    }
    public void setTableList(ObservableList<TableColumnModel> tableList) {
        this.tableList = tableList;
    }
}
