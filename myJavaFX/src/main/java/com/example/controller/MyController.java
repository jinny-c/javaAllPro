package com.example.controller;

import com.example.bean.TableColumnModel;
import com.example.bean.TableModel;
import com.example.view.MyTableColumnBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @description TODO
 * @auth chaijd
 * @date 2023/5/30
 */
public class MyController {

    @FXML
    private Button closeButton;
    @FXML
    public Label time;
    @FXML
    public TableView<TableColumnModel> tableView;

    private TableModel model = new TableModel();

    public void initialize() {
        // 将视图层的Label控件和模型层的time属性进行双向绑定，这个跟vue的双向绑定有点类似。
        time.textProperty().bindBidirectional(model.timeProperty());
        // 启动新线程定时改变模型中的time属性，
        executeTimeWork();
        buildTableColumn();
        loadTestTableList();
    }

    private void executeTimeWork() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Thread daemon = new Thread(() -> {
            while (true) {
                // 注意：这里只需要改变model中的time属性即可，视图层的Label信息会跟着调整
                // 因为在initialize方法中已经将time属性绑定在Label控件中了。
                Platform.runLater(() -> model.setTime(sdf.format(new Date())));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignore) {
                }
            }
        });
        //在创建后台线程时调用了 setDaemon(true) 方法，将其设置为守护线程。这样，在 JavaFX 应用程序退出时，JVM 会自动终止该线程的执行，从而避免资源泄漏和其他问题。
        daemon.setDaemon(true);
        daemon.start();
    }


    private void buildTableColumn() {
        TableColumn<TableColumnModel, Boolean> selected = MyTableColumnBuilder.checkboxColumn("", "selected", 40);
        TableColumn<TableColumnModel, Integer> id = MyTableColumnBuilder.textColumn("ID", "id", 60);
        TableColumn<TableColumnModel, String> title = MyTableColumnBuilder.textColumn("名称", "title", 180);
        TableColumn<TableColumnModel, Double> progress = MyTableColumnBuilder.progressColumn("进度", "progress", 150);
        TableColumn<TableColumnModel, Integer> operator = MyTableColumnBuilder.operatorColumn("操作", "id", 160,
                integer -> {
                    // 处理任务加载，进度更新
                    Optional<TableColumnModel> opt = model.getTableList().stream().filter(i -> i.getId() == integer).findFirst();
                    if (opt.isPresent()) {
                        TableColumnModel cm = opt.get();
                        new Thread(() -> {
                            while (cm.getProgress() < 1) {
                                cm.setProgress(cm.getProgress() + 0.01);
                                try {
                                    TimeUnit.MILLISECONDS.sleep(100);
                                } catch (InterruptedException ignore) {
                                }
                            }
                            cm.setProgress(1);
                        }).start();
                    }
                }, new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        // 删除任务，将任务从模型中删除
                        model.getTableList().removeIf(i -> i.getId() == integer);
                    }
                }, this::executeDetail);

        tableView.getColumns().addAll(selected, id, title, progress, operator);
    }

    private void executeDetail() {

    }

    public void loadTestTableList() {
        // 这里省略了dao层，直接随机生成模拟数据
        String[] works = new String[]{"Hi IT青年", "JavaFX MVC", "https://www.cnblogs.com/itqn/", "Wx公众号：HiIT青年"};
        for (int i = 0; i < 10; i++) {
            model.getTableList().add(TableColumnModel.fromWork(i + 1, works[(int) (Math.random() * works.length)]));
        }
        tableView.setItems(model.getTableList());
    }

    @FXML
    protected void executeWorkAdd() {
        //Platform.exit();
        //daemon.setDaemon(true);
    }

    @FXML
    protected void closeButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        //Platform.exit();
        //daemon.setDaemon(true);
    }

}
