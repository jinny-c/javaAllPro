package demos.components;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeTableDemo extends Application {
    private static final String COMPUTER_DEPARTMENT = "Computer Department";
    private static final String SALES_DEPARTMENT = "Sales Department";
    private static final String IT_DEPARTMENT = "IT Department";
    private static final String HR_DEPARTMENT = "HR Department";

    public TreeTableDemo() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        JFXTreeTableColumn<User, String> deptColumn = new JFXTreeTableColumn("Department");
        deptColumn.setPrefWidth(150.0);
        deptColumn.setCellValueFactory((param) -> {
            return (ObservableValue)(deptColumn.validateValue(param) ? ((User)param.getValue().getValue()).department : deptColumn.getComputedValue(param));
        });
        JFXTreeTableColumn<User, String> empColumn = new JFXTreeTableColumn("Employee");
        empColumn.setPrefWidth(150.0);
        empColumn.setCellValueFactory((param) -> {
            return (ObservableValue)(empColumn.validateValue(param) ? ((User)param.getValue().getValue()).userName : empColumn.getComputedValue(param));
        });
        JFXTreeTableColumn<User, String> ageColumn = new JFXTreeTableColumn("Age");
        ageColumn.setPrefWidth(150.0);
        ageColumn.setCellValueFactory((param) -> {
            return (ObservableValue)(ageColumn.validateValue(param) ? ((User)param.getValue().getValue()).age : ageColumn.getComputedValue(param));
        });
        ageColumn.setCellFactory((param) -> {
            return new GenericEditableTreeTableCell(new TextFieldEditorBuilder());
        });
        ageColumn.setOnEditCommit((t) -> {
            ((User)t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age.set(t.getNewValue());
        });
        empColumn.setCellFactory((param) -> {
            return new GenericEditableTreeTableCell(new TextFieldEditorBuilder());
        });
        empColumn.setOnEditCommit((t) -> {
            ((User)t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).userName.set(t.getNewValue());
        });
        deptColumn.setCellFactory((param) -> {
            return new GenericEditableTreeTableCell(new TextFieldEditorBuilder());
        });
        deptColumn.setOnEditCommit((t) -> {
            ((User)t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).department.set(t.getNewValue());
        });
        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User("Computer Department", "23", "CD 1"));
        users.add(new User("Sales Department", "22", "Employee 1"));
        users.add(new User("Sales Department", "24", "Employee 2"));
        users.add(new User("Sales Department", "25", "Employee 4"));
        users.add(new User("Sales Department", "27", "Employee 5"));
        users.add(new User("IT Department", "42", "ID 2"));
        users.add(new User("HR Department", "21", "HR 1"));
        users.add(new User("HR Department", "28", "HR 2"));

        int i;
        for(i = 0; i < 40000; ++i) {
            users.add(new User("HR Department", Integer.toString(i % 10), "HR 3" + i));
        }

        for(i = 0; i < 40000; ++i) {
            users.add(new User("Computer Department", Integer.toString(i % 20), "CD 2" + i));
        }

        for(i = 0; i < 40000; ++i) {
            users.add(new User("IT Department", Integer.toString(i % 5), "HR 4" + i));
        }

        TreeItem<User> root = new RecursiveTreeItem((Callback<RecursiveTreeObject, ObservableList>) users);
        JFXTreeTableView<User> treeView = new JFXTreeTableView(root);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        treeView.getColumns().setAll(new TreeTableColumn[]{deptColumn, ageColumn, empColumn});
        FlowPane main = new FlowPane();
        main.setPadding(new Insets(10.0));
        main.getChildren().add(treeView);
        JFXButton groupButton = new JFXButton("Group");
        groupButton.setOnAction((action) -> {
            (new Thread(() -> {
                treeView.group(new TreeTableColumn[]{empColumn});
            })).start();
        });
        main.getChildren().add(groupButton);
        JFXButton unGroupButton = new JFXButton("unGroup");
        unGroupButton.setOnAction((action) -> {
            treeView.unGroup(new TreeTableColumn[]{empColumn});
        });
        main.getChildren().add(unGroupButton);
        JFXTextField filterField = new JFXTextField();
        main.getChildren().add(filterField);
        Label size = new Label();
        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            treeView.setPredicate((userProp) -> {
                User user = (User)userProp.getValue();
                return ((String)user.age.get()).contains(newVal) || ((String)user.department.get()).contains(newVal) || ((String)user.userName.get()).contains(newVal);
            });
        });
        size.textProperty().bind(Bindings.createStringBinding(() -> {
            return String.valueOf(treeView.getCurrentItemsCount());
        }, new Observable[]{treeView.currentItemsCountProperty()}));
        main.getChildren().add(size);
        Scene scene = new Scene(main, 475.0, 500.0);
        scene.getStylesheets().add(TreeTableDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static final class User extends RecursiveTreeObject<User> {
        final StringProperty userName;
        final StringProperty age;
        final StringProperty department;

        User(String department, String age, String userName) {
            this.department = new SimpleStringProperty(department);
            this.userName = new SimpleStringProperty(userName);
            this.age = new SimpleStringProperty(age);
        }
    }
}
