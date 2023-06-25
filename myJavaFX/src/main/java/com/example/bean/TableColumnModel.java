package com.example.bean;

import javafx.beans.property.*;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/25
 */
public class TableColumnModel {

    private BooleanProperty selected = new SimpleBooleanProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private DoubleProperty progress = new SimpleDoubleProperty();

    public static TableColumnModel fromWork(Integer id, String title) {
        TableColumnModel model = new TableColumnModel();
        model.setId(id);
        model.setTitle(title);
        model.setSelected(false);
        model.setProgress(0);
        return model;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public double getProgress() {
        return progress.get();
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }
}
