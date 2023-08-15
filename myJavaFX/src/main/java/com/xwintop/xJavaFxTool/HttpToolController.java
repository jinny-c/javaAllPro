package com.xwintop.xJavaFxTool;

import com.example.utils.GsonUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpToolController extends HttpToolView {
    private static final Logger log = LoggerFactory.getLogger(HttpToolController.class);
    private HttpToolService httpToolService = new HttpToolService(this);
    private String[] methodStrings = new String[]{"GET", "POST", "HEAD", "PUT", "PATCH", "DELETE"};
    private ObservableList<Map<String, String>> paramsDatatableData = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> paramsHeadertableData = FXCollections.observableArrayList();
    private ObservableList<Map<String, String>> paramsCookietableData = FXCollections.observableArrayList();
    private static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public HttpToolController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initView();
        this.initEvent();
        this.initService();
    }

    private void initView() {
        this.methodChoiceBox.getItems().addAll(this.methodStrings);
        this.methodChoiceBox.setValue(this.methodStrings[0]);
        setTableColumnMapValueFactory(this.paramsDataNameTableColumn, "name");
        setTableColumnMapValueFactory(this.paramsDataValueTableColumn, "value");
        setTableColumnMapValueFactory(this.paramsDataRemarkTableColumn, "remark");
        setTableColumnMapValueFactory(this.paramsHeaderNameTableColumn, "name");
        setTableColumnMapValueFactory(this.paramsHeaderValueTableColumn, "value");
        setTableColumnMapValueFactory(this.paramsHeaderRemarkTableColumn, "remark");
        setTableColumnMapValueFactory(this.paramsCookieNameTableColumn, "name");
        setTableColumnMapValueFactory(this.paramsCookieValueTableColumn, "value");
        setTableColumnMapValueFactory(this.paramsCookieRemarkTableColumn, "remark");
        this.paramsDataTableView.setItems(this.paramsDatatableData);
        this.paramsHeaderTableView.setItems(this.paramsHeadertableData);
        this.paramsCookieTableView.setItems(this.paramsCookietableData);
    }

    private void initEvent() {
        this.paramsDataIsStringCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue()) {
                    HttpToolController.this.paramsDataTextArea.setVisible(true);
                    HttpToolController.this.paramsDataTableView.setVisible(false);
                } else {
                    HttpToolController.this.paramsDataTextArea.setVisible(false);
                    HttpToolController.this.paramsDataTableView.setVisible(true);
                }
            }
        });
        this.setTableViewOnMouseClicked(this.paramsDataTableView, this.paramsDatatableData);
        this.setTableViewOnMouseClicked(this.paramsHeaderTableView, this.paramsHeadertableData);
        this.setTableViewOnMouseClicked(this.paramsCookieTableView, this.paramsCookietableData);
        MenuItem compressJsonMenuItem = new MenuItem("\u538b\u7f29JSON");
        compressJsonMenuItem.setOnAction((event) -> {
            Matcher m = p.matcher(this.ResponseBodyTextArea.getText());
            this.ResponseBodyTextArea.setText(m.replaceAll(""));
        });
        MenuItem formatJsonMenuItem = new MenuItem("\u683c\u5f0f\u5316JSON");
        formatJsonMenuItem.setOnAction((event) -> {
            try {
                String prettyJsonString = GsonUtils.toJson(this.ResponseBodyTextArea.getText());
                this.ResponseBodyTextArea.setText("null".equals(prettyJsonString) ? "" : prettyJsonString);
            } catch (Exception var3) {
                log.debug("\u683c\u5f0f\u5316\u9519\u8bef:" + var3.getMessage());
                //TooltipUtil.showToast("\u683c\u5f0f\u5316\u9519\u8bef:" + var3.getMessage());
            }

        });
        this.ResponseBodyTextArea.setContextMenu(new ContextMenu(new MenuItem[]{compressJsonMenuItem, formatJsonMenuItem}));
    }

    private void initService() {
    }

    @FXML
    private void sendAction(ActionEvent event) {
        try {
            this.httpToolService.sendAction();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    void addParamsDataAction(ActionEvent event) {
        this.paramsDatatableData.add(new HashMap());
    }

    @FXML
    void addParamsHeaderAction(ActionEvent event) {
        this.paramsHeadertableData.add(new HashMap());
    }

    @FXML
    void addParamsCookieAction(ActionEvent event) {
        this.paramsCookietableData.add(new HashMap());
    }

    @FXML
    private void toBrowerAction(ActionEvent event) {
        this.httpToolService.toBrowerAction();
    }

    private void setTableViewOnMouseClicked(TableView<Map<String, String>> paramsDataTableView, ObservableList<Map<String, String>> paramsDatatableData) {
        paramsDataTableView.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.SECONDARY && !paramsDatatableData.isEmpty()) {
                MenuItem menu_Copy = new MenuItem("\u590d\u5236\u9009\u4e2d\u884c");
                menu_Copy.setOnAction((event1) -> {
                    Map<String, String> tableBean = (Map) paramsDataTableView.getSelectionModel().getSelectedItem();
                    Map<String, String> tableBean2 = new HashMap(tableBean);
                    paramsDatatableData.add(paramsDataTableView.getSelectionModel().getSelectedIndex(), tableBean2);
                });
                MenuItem menu_Remove = new MenuItem("\u5220\u9664\u9009\u4e2d\u884c");
                menu_Remove.setOnAction((event1) -> {
                    paramsDatatableData.remove(paramsDataTableView.getSelectionModel().getSelectedIndex());
                });
                MenuItem menu_RemoveAll = new MenuItem("\u5220\u9664\u6240\u6709");
                menu_RemoveAll.setOnAction((event1) -> {
                    paramsDatatableData.clear();
                });
                paramsDataTableView.setContextMenu(new ContextMenu(new MenuItem[]{menu_Copy, menu_Remove, menu_RemoveAll}));
            }

        });
    }

    public void setHttpToolService(HttpToolService httpToolService) {
        this.httpToolService = httpToolService;
    }

    public void setMethodStrings(String[] methodStrings) {
        this.methodStrings = methodStrings;
    }

    public void setParamsDatatableData(ObservableList<Map<String, String>> paramsDatatableData) {
        this.paramsDatatableData = paramsDatatableData;
    }

    public void setParamsHeadertableData(ObservableList<Map<String, String>> paramsHeadertableData) {
        this.paramsHeadertableData = paramsHeadertableData;
    }

    public void setParamsCookietableData(ObservableList<Map<String, String>> paramsCookietableData) {
        this.paramsCookietableData = paramsCookietableData;
    }

    public HttpToolService getHttpToolService() {
        return this.httpToolService;
    }

    public String[] getMethodStrings() {
        return this.methodStrings;
    }

    public ObservableList<Map<String, String>> getParamsDatatableData() {
        return this.paramsDatatableData;
    }

    public ObservableList<Map<String, String>> getParamsHeadertableData() {
        return this.paramsHeadertableData;
    }

    public ObservableList<Map<String, String>> getParamsCookietableData() {
        return this.paramsCookietableData;
    }

    public static void setTableColumnMapValueFactory(TableColumn tableColumn, String name) {
        setTableColumnMapValueFactory(tableColumn, name, true, (Runnable) null);
    }

    public static void setTableColumnMapValueFactory(TableColumn tableColumn, String name, boolean isEdit, Runnable onEditCommitHandle) {
        tableColumn.setCellValueFactory(new MapValueFactory(name));
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        if (isEdit) {
            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Map<String, String>, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Map<String, String>, String> t) {
                    ((Map<String, String>) t.getRowValue()).put(name, t.getNewValue());
                    if (onEditCommitHandle != null) {
                        onEditCommitHandle.run();
                    }
                }
            });
        }
    }

}