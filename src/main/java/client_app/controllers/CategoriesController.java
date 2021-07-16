package client_app.controllers;

import java.io.IOException;
import java.util.List;

import client_app.apiRequester.HttpClient;
import client_app.models.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CategoriesController {


    @FXML
    private MenuItem mnItemAdd;

    @FXML
    private MenuItem mnItemEdit;

    @FXML
    private ListView<Category> listCategories;


    @FXML
    void onMenuItemClicked(ActionEvent event) {

        if (event.getSource().equals(mnItemAdd)){
            addCategory();



        }else if (event.getSource().equals(mnItemEdit)){
            editCategory();
        }

    }

    private void editCategory() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/edit_category.fxml"));
        try {
            loader.load();
            Parent parent = loader.getRoot();
            stage.setTitle("Редактирование категории");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditCategoryController editCategoryController = loader.getController();
            editCategoryController.init(listCategories.getSelectionModel().getSelectedItem());
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    refreshTableView();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCategory() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/edit_category.fxml"));
        try {
            loader.load();
            Parent parent = loader.getRoot();
            stage.setTitle("Создание категории");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditCategoryController editCategoryController = loader.getController();
            editCategoryController.init(new Category());
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    refreshTableView();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshTableView() {
        listCategories.refresh();
        initListView();
    }

    @FXML
    void initialize() {
        initListViewProperties();
        initListView();

    }

    private void initListViewProperties() {
        listCategories.setCellFactory(category->new ListCell<Category>(){
            @Override
            protected void updateItem(Category c,boolean empty){
                super.updateItem(c,empty);
                if (empty||c==null||c.getName()==null){
                    setText(null);
                }else {
                    setText(c.getName());
                }
                if (c!=null&& !c.isActive()){
                    setStyle("-fx-background-color: red");
                }else {
                    setStyle("");
                }
            }
        });
    }

    private void initListView() {
        List<Category> categoryList = HttpClient.INSTANCE.getAllCategories();
        listCategories.setItems(FXCollections.observableArrayList(categoryList));
    }
}
