package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * @ClassName: IndexController
 * @Description: 主页
 * @author: xufeng
 * @date: 2017年7月20日 下午1:50:00
 */
@Slf4j
@Getter
@Setter
public class IndexController extends IndexView {
    public static final String QQ_URL = "https://support.qq.com/product/127577";
    public static final String STATISTICS_URL = "https://xwintop.gitee.io/maven/tongji/xJavaFxTool.html";

    private Map<String, Menu> menuMap = new HashMap<String, Menu>();
    private Map<String, MenuItem> menuItemMap = new HashMap<String, MenuItem>();
    //private IndexService indexService = new IndexService(this);
    private ContextMenu contextMenu = new ContextMenu();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        initView();
        initEvent();
        initService();

        initNotepad();
        //this.indexService.addWebView("欢迎吐槽", QQ_URL, null);
        this.tongjiWebView.getEngine().load(STATISTICS_URL);
    }

    private void initNotepad() {

    }

    private void initView() {
        menuMap.put("toolsMenu", toolsMenu);
        menuMap.put("moreToolsMenu", moreToolsMenu);
    }

    private void initEvent() {
        myTextField.textProperty().addListener((observable, oldValue, newValue) -> selectAction(newValue));
        myButton.setOnAction(arg0 -> {
            selectAction(myTextField.getText());
        });
    }

    private void initService() {
    }

    public void addToolMenu(File file) throws Exception {

    }


    public void selectAction(String selectText) {
        if (contextMenu.isShowing()) {
            contextMenu.hide();
        }
        //contextMenu = indexService.getSelectContextMenu(selectText);
        contextMenu.show(myTextField, null, 0, myTextField.getHeight());
    }

    @FXML
    private void exitAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void closeAllTabAction(ActionEvent event) {
        tabPaneMain.getTabs().clear();
    }

    @FXML
    private void openAllTabAction(ActionEvent event) {
        for (MenuItem value : menuItemMap.values()) {
            value.fire();
        }
    }

    @FXML
    private void addNodepadAction(ActionEvent event) {
        //indexService.addNodepadAction(event);
    }

    @FXML
    private void addLogConsoleAction(ActionEvent event) {
        //indexService.addLogConsoleAction(event);
    }

    @FXML
    private void pluginManageAction() throws Exception {
    }

    @FXML
    private void SettingAction() {
        //SystemSettingService.openSystemSettings(bundle.getString("Setting"));
    }

    @FXML
    private void aboutAction(ActionEvent event) throws Exception {
        //AlertUtil.showInfoAlert(bundle.getString("aboutText") + Config.xJavaFxToolVersions);
    }

    @FXML
    private void setLanguageAction(ActionEvent event) throws Exception {
        MenuItem menuItem = (MenuItem) event.getSource();
        //indexService.setLanguageAction(menuItem.getText());
    }

    @FXML
    private void openLogFileAction() {
        String filePath = "logs/logFile." + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ".log";
        //JavaFxSystemUtil.openDirectory(filePath);
    }

    @FXML
    private void openLogFolderAction() {
        //JavaFxSystemUtil.openDirectory("logs/");
    }

    @FXML
    private void openConfigFolderAction() {
        //JavaFxSystemUtil.openDirectory(ConfigureUtil.getConfigurePath());
    }

    @FXML
    private void openPluginFolderAction() {
        //JavaFxSystemUtil.openDirectory("libs/");
    }

    @FXML
    private void xwintopLinkOnAction() throws Exception {
        //HttpClientUtil.openBrowseURLThrowsException("https://gitee.com/xwintop/xJavaFxTool-spring");
    }

    @FXML
    private void userSupportAction() throws Exception {
        //HttpClientUtil.openBrowseURLThrowsException("https://support.qq.com/product/127577");
    }
}
