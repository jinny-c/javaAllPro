package com.example.app.demo.jfoenix.controller.ui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.IntegerTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.base.ValidatorBase;
import io.datafx.controller.ViewController;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;

@ViewController(
        value = "/fxml/ui/TreeTableView.fxml",
        title = "Material Design Example"
)
public class TreeTableViewController {
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    @FXML
    private JFXTreeTableView<Person> treeTableView;
    @FXML
    private JFXTreeTableColumn<Person, String> firstNameColumn;
    @FXML
    private JFXTreeTableColumn<Person, String> lastNameColumn;
    @FXML
    private JFXTreeTableColumn<Person, Integer> ageColumn;
    @FXML
    private JFXTextField searchField;
    @FXML
    private JFXTreeTableView<Person> editableTreeTableView;
    @FXML
    private JFXTreeTableColumn<Person, String> firstNameEditableColumn;
    @FXML
    private JFXTreeTableColumn<Person, String> lastNameEditableColumn;
    @FXML
    private JFXTreeTableColumn<Person, Integer> ageEditableColumn;
    @FXML
    private Label treeTableViewCount;
    @FXML
    private JFXButton treeTableViewAdd;
    @FXML
    private JFXButton treeTableViewRemove;
    @FXML
    private Label editableTreeTableViewCount;
    @FXML
    private JFXTextField searchField2;
    private final String[] names = new String[]{"Morley", "Scott", "Kruger", "Lain", "Kennedy", "Gawron", "Han", "Hall", "Aydogdu", "Grace", "Spiers", "Perera", "Smith", "Connoly", "Sokolowski", "Chaow", "James", "June"};
    private final Random random = new SecureRandom();

    public TreeTableViewController() {
    }

    @PostConstruct
    public void init() {
        this.setupReadOnlyTableView();
        this.setupEditableTableView();
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<Person, T> column, Function<Person, ObservableValue<T>> mapper) {
        column.setCellValueFactory((param) -> {
            return column.validateValue(param) ? (ObservableValue) mapper.apply(param.getValue().getValue()) : column.getComputedValue(param);
        });
    }

    private void setupReadOnlyTableView() {
        this.setupCellValueFactory(this.firstNameColumn, Person::firstNameProperty);
        this.setupCellValueFactory(this.lastNameColumn, Person::lastNameProperty);
        this.setupCellValueFactory(this.ageColumn, (p) -> {
            return p.age.asObject();
        });
        ObservableList<Person> dummyData = this.generateDummyData(100);
        //this.treeTableView.setRoot(new RecursiveTreeItem((Callback<RecursiveTreeObject, ObservableList>) dummyData));
        //this.treeTableView.setRoot((TreeItem)new RecursiveTreeItem(dummyData, RecursiveTreeObject::getChildren));
        this.treeTableView.setRoot(new RecursiveTreeItem(dummyData, (Callback<RecursiveTreeObject, ObservableList>) RecursiveTreeObject::getChildren));

        this.treeTableView.setShowRoot(false);
        this.treeTableViewCount.textProperty().bind(Bindings.createStringBinding(() -> {
            return "( " + this.treeTableView.getCurrentItemsCount() + " )";
        }, new Observable[]{this.treeTableView.currentItemsCountProperty()}));
        this.treeTableViewAdd.disableProperty().bind(Bindings.notEqual(-1, this.treeTableView.getSelectionModel().selectedIndexProperty()));
        this.treeTableViewRemove.disableProperty().bind(Bindings.equal(-1, this.treeTableView.getSelectionModel().selectedIndexProperty()));
        this.treeTableViewAdd.setOnMouseClicked((e) -> {
            dummyData.add(this.createNewRandomPerson());
            IntegerProperty currCountProp = this.treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() + 1);
        });
        this.treeTableViewRemove.setOnMouseClicked((e) -> {
            dummyData.remove(((TreeItem) this.treeTableView.getSelectionModel().selectedItemProperty().get()).getValue());
            IntegerProperty currCountProp = this.treeTableView.currentItemsCountProperty();
            currCountProp.set(currCountProp.get() - 1);
        });
        this.searchField.textProperty().addListener(this.setupSearchField(this.treeTableView));
    }

    private void setupEditableTableView() {
        this.setupCellValueFactory(this.firstNameEditableColumn, Person::firstNameProperty);
        this.setupCellValueFactory(this.lastNameEditableColumn, Person::lastNameProperty);
        this.setupCellValueFactory(this.ageEditableColumn, (p) -> {
            return p.age.asObject();
        });
        this.firstNameEditableColumn.setCellFactory((param) -> {
            return new GenericEditableTreeTableCell(new TextFieldEditorBuilder());
        });
        this.firstNameEditableColumn.setOnEditCommit((t) -> {
            ((Person) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).firstName.set(t.getNewValue());
        });
        this.lastNameEditableColumn.setCellFactory((param) -> {
            return new GenericEditableTreeTableCell(new TextFieldEditorBuilder());
        });
        this.lastNameEditableColumn.setOnEditCommit((t) -> {
            ((Person) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).lastName.set(t.getNewValue());
        });
        this.ageEditableColumn.setCellFactory((param) -> {
            return new GenericEditableTreeTableCell(new IntegerTextFieldEditorBuilder(new ValidatorBase[0]));
        });
        this.ageEditableColumn.setOnEditCommit((t) -> {
            ((Person) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age.set((Integer) t.getNewValue());
        });
        ObservableList<Person> dummyData = this.generateDummyData(200);
        //this.editableTreeTableView.setRoot(new RecursiveTreeItem((Callback<RecursiveTreeObject, ObservableList>) dummyData));
        this.treeTableView.setRoot(new RecursiveTreeItem(dummyData, new Callback<RecursiveTreeObject, ObservableList>() {
            @Override
            public ObservableList call(RecursiveTreeObject param) {
                return param.getChildren();
            }
        }));
        this.editableTreeTableView.setShowRoot(false);
        this.editableTreeTableView.setEditable(true);
        this.editableTreeTableViewCount.textProperty().bind(Bindings.createStringBinding(() -> {
            return "( " + this.editableTreeTableView.getCurrentItemsCount() + " )";
        }, new Observable[]{this.editableTreeTableView.currentItemsCountProperty()}));
        this.searchField2.textProperty().addListener(this.setupSearchField(this.editableTreeTableView));
    }

    private ChangeListener<String> setupSearchField(JFXTreeTableView<Person> tableView) {
        return (o, oldVal, newVal) -> {
            tableView.setPredicate((personProp) -> {
                Person person = (Person) personProp.getValue();
                return ((String) person.firstName.get()).contains(newVal) || ((String) person.lastName.get()).contains(newVal) || Integer.toString(person.age.get()).contains(newVal);
            });
        };
    }

    private ObservableList<Person> generateDummyData(int numberOfEntries) {
        ObservableList<Person> dummyData = FXCollections.observableArrayList();

        for (int i = 0; i < numberOfEntries; ++i) {
            dummyData.add(this.createNewRandomPerson());
        }

        return dummyData;
    }

    private Person createNewRandomPerson() {
        return new Person(this.names[this.random.nextInt(this.names.length)], this.names[this.random.nextInt(this.names.length)], this.random.nextInt(100));
    }

    final class Person extends RecursiveTreeObject<Person> {
        final StringProperty firstName;
        final StringProperty lastName;
        final SimpleIntegerProperty age;

        Person(String firstName, String lastName, int age) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.age = new SimpleIntegerProperty(age);
        }

        StringProperty firstNameProperty() {
            return this.firstName;
        }

        StringProperty lastNameProperty() {
            return this.lastName;
        }
    }
}
