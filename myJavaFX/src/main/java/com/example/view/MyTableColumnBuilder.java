package com.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;


/**
 * @description TODO
 * @auth chaijd
 * @date 2023/6/25
 */
public class MyTableColumnBuilder {
    /**
     * 复选框
     *
     * @param text
     * @param field
     * @param width
     * @return
     */
    public static TableColumn checkboxColumn(String text, String field, int width) {
        TableColumn column = new TableColumn();
        column.setText(text);
        column.setPrefWidth(width);
        column.setCellValueFactory(new PropertyValueFactory(field));
        column.setCellFactory(CheckBoxTableCell.forTableColumn(column));
        return column;
    }

    /**
     * 普通文本
     *
     * @param text
     * @param field
     * @param width
     * @return
     */
    public static TableColumn textColumn(String text, String field, int width) {
        TableColumn column = new TableColumn();
        column.setText(text);
        column.setPrefWidth(width);
        column.setCellValueFactory(new PropertyValueFactory(field));
        return column;
    }

    /**
     * 进度条
     *
     * @param text
     * @param field
     * @param width
     * @return
     */

    public static TableColumn progressColumn(String text, String field, int width) {
        TableColumn column = new TableColumn();
        column.setText(text);
        column.setPrefWidth(width);
        column.setCellValueFactory(new PropertyValueFactory(field));
        column.setCellFactory(v -> {
            return new TableCell<Object, Double>() {
                private HBox hBox = new HBox();
                private Label progressLabel = new Label("0% ");
                private ProgressBar progressBar = new ProgressBar();

                {
                    progressLabel.setPrefWidth(50);
                    progressLabel.setAlignment(Pos.CENTER_RIGHT);
                    hBox.getChildren().addAll(progressBar, progressLabel);
                }

                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        progressBar.setProgress(item);
                        progressLabel.setText((int) ((item * 100)) + "% ");
                        setGraphic(hBox);
                    }
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            };
        });
        return column;
    }

    /**
     * 操作按钮组
     *
     * @param text
     * @param field
     * @param width
     * @return
     */
    public static TableColumn operatorColumn(String text, String field, int width, Consumer<Integer> executeLoadWork, Consumer<Integer> executeDeleteWork, Runnable executeAdd) {
        TableColumn column = new TableColumn();
        column.setText(text);
        column.setPrefWidth(width);
        column.setCellValueFactory(new PropertyValueFactory(field));
        column.setCellFactory(v -> {
            return new TableCell<Object, Integer>() {
                private HBox hBox = new HBox();
                private Button button1 = new Button("加载");
                private Button button3 = new Button("新增");
                private Button button2 = new Button("删除");

                {
                    button1.setOnAction(event -> {
                        //runnable.run();
                        //executeLoadWork(this.getIndex());
                        executeLoadWork.accept(this.getItem());
                    });
                    button2.setOnAction(event -> {
                        //runnable.run();
                        //executeLoadWork(this.getIndex());
                        executeDeleteWork.accept(this.getItem());
                    });
                    button3.setOnAction(event -> {
                        executeAdd.run();
                    });
                    hBox.getChildren().addAll(button1, button2, button3);
                }

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(hBox);
                    }
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            };
        });
        return column;
    }

}
