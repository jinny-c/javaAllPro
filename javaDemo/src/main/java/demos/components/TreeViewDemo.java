package demos.components;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.JFXTreeViewPath;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class TreeViewDemo extends Application {
    private static final String SALES_DEPARTMENT = "Sales Depa";
    private static final String IT_SUPPORT = "IT adsfasdfasfdasdfsadfsadfasdf Support";
    private static final String ACCOUNTS_DEPARTMENT = "Accounts Department";
    private final List<Employee> employees = Arrays.asList(new Employee("Ethan Williams", "Sales Depa"), new Employee("Emma Jones", "Sales Depa"), new Employee("Michael Brownasdfasdfsadfs", "Sales Depa"), new Employee("Anna Black", "Sales Depa"), new Employee("Roer York", "Sales Depa"), new Employee("Susan Collins", "Sales Depa"), new Employee("Miaaaake Graham", "IT adsfasdfasfdasdfsadfsadfasdf Support"), new Employee("Judy Mayer", "IT adsfasdfasfdasdfsadfsadfasdf Support"), new Employee("Gregy Smith", "IT adsfasdfasfdasdfsadfsadfasdf Support"), new Employee("Jacob Smith", "Accounts Department"), new Employee("Isabella Johnson", "Accounts Department"));
    private final FilterableTreeItem<String> rootNode = new FilterableTreeItem( "MyCompany Human Resources");

    public TreeViewDemo() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.rootNode.setExpanded(true);
        JFXTreeView<String> treeView = new JFXTreeView(this.rootNode);
        Iterator var3 = this.employees.iterator();

        while(var3.hasNext()) {
            Employee employee = (Employee)var3.next();
            FilterableTreeItem<String> empLeaf = new FilterableTreeItem( employee.getName());
            boolean found = false;
            Iterator var7 = this.rootNode.getChildren().iterator();

            while(var7.hasNext()) {
                TreeItem<String> depNode = (TreeItem)var7.next();
                if (((String)depNode.getValue()).contentEquals(employee.getDepartment())) {
                    ((FilterableTreeItem)depNode).getInternalChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }

            if (!found) {
                FilterableTreeItem<String> depNode = new FilterableTreeItem( employee.getDepartment());
                this.rootNode.getInternalChildren().add(depNode);
                depNode.getInternalChildren().add(empLeaf);
            }
        }

        stage.setTitle("Tree View Sample");
        VBox box = new VBox();
        Scene scene = new Scene(box, 400.0, 300.0);
        scene.setFill(Color.LIGHTGRAY);
        treeView.setShowRoot(false);
        TextField filterField = new JFXTextField();
        this.rootNode.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            return filterField.getText() != null && !filterField.getText().isEmpty() ? demos.components.TreeViewDemo.TreeItemPredicate.create((actor) -> {
                return actor.toString().contains(filterField.getText());
            }) : null;
        }, new Observable[]{filterField.textProperty()}));
        box.getChildren().addAll(new Node[]{new JFXTreeViewPath(treeView), treeView, filterField});
        VBox.setVgrow(treeView, Priority.ALWAYS);
        stage.setScene(scene);
        stage.show();
    }


    @FunctionalInterface
    public static interface TreeItemPredicate<T> {
        boolean test(TreeItem<T> param1TreeItem, T param1T);

        static <T> TreeItemPredicate<T> create(Predicate<T> predicate) {
            return (parent, value) -> predicate.test(value);
        }
    }

    public class FilterableTreeItem<T>
            extends TreeItem<T> {
        private final ObservableList<TreeItem<T>> sourceList;
        private FilteredList<TreeItem<T>> filteredList;
        private ObjectProperty<TreeViewDemo.TreeItemPredicate<T>> predicate = new SimpleObjectProperty<>();


        public FilterableTreeItem(T value) {
            super(value);
            this.sourceList = FXCollections.observableArrayList();
            this.filteredList = new FilteredList<>(this.sourceList);
            this.filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                return (child) -> {
                    if (child instanceof FilterableTreeItem) {
                        FilterableTreeItem<T> filterableChild = (FilterableTreeItem) child;
                        filterableChild.setPredicate((TreeViewDemo.TreeItemPredicate) this.predicate.get());
                    }

                    if (this.predicate.get() == null) {
                        return true;
                    } else {
                        return child.getChildren().size() > 0 ? true : ((TreeViewDemo.TreeItemPredicate) this.predicate.get()).test(this, child.getValue());
                    }
                };
            }, new Observable[]{this.predicate}));
            this.setHiddenFieldChildren(this.filteredList);
            setHiddenFieldChildren(this.filteredList);
        }

        protected void setHiddenFieldChildren(ObservableList<TreeItem<T>> list) {
            try {
                Field childrenField = TreeItem.class.getDeclaredField("children");
                childrenField.setAccessible(true);
                childrenField.set(this, list);

                Field declaredField = TreeItem.class.getDeclaredField("childrenListener");
                declaredField.setAccessible(true);
                list.addListener((ListChangeListener<? super TreeItem<T>>) declaredField.get(this));
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Could not set TreeItem.children", e);
            }
        }

        public ObservableList<TreeItem<T>> getInternalChildren() {
            return this.sourceList;
        }

        public void setPredicate(TreeViewDemo.TreeItemPredicate<T> predicate) {
            this.predicate.set(predicate);
        }

        public TreeViewDemo.TreeItemPredicate getPredicate() {
            return this.predicate.get();
        }

        public ObjectProperty<TreeViewDemo.TreeItemPredicate<T>> predicateProperty() {
            return this.predicate;
        }
    }


    public static class Employee {
        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName() {
            return this.name.get();
        }

        public void setName(String firstName) {
            this.name.set(firstName);
        }

        public String getDepartment() {
            return this.department.get();
        }

        public void setDepartment(String firstName) {
            this.department.set(firstName);
        }
    }
}
