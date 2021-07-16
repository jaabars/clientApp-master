package client_app.controllers;

import client_app.apiRequester.HttpClient;
import client_app.models.Category;
import client_app.models.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> tblViewProducts;

    @FXML
    private TableColumn<Product, Long> colmnId;

    @FXML
    private TableColumn<Product, String> colmnProductName;

    @FXML
    private TableColumn<Product, String> colmnCategoryName;

    @FXML
    private TableColumn<Product, String> colmnPrice;
    @FXML
    private MenuItem mnItemCategories;
    @FXML
    private MenuItem mnItemAddProduct;

    @FXML
    private MenuItem mnItemEditProduct;



    @FXML
    void onMenuItemClicked(ActionEvent event) {

        if (event.getSource().equals(mnItemCategories)){
            menuCategories();
        }else if (event.getSource().equals(mnItemAddProduct)){
            addProduct();
        }else  if (event.getSource().equals(mnItemEditProduct)){
            editProduct();
        }

    }

    private void editProduct() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/productAddAndEdit.fxml"));
        try {
            loader.load();
            Parent parent = loader.getRoot();
            stage.setTitle("Редактирование продукта");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            ProductEditController productEditController = loader.getController();
            productEditController.setProduct(tblViewProducts.getSelectionModel().getSelectedItem());
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

    private void addProduct() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/productAddAndEdit.fxml"));
        try {
            loader.load();
            Parent parent = loader.getRoot();
            stage.setTitle("Создание продукта");
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            ProductEditController productEditController = loader.getController();
            productEditController.setProduct(new Product());
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
        tblViewProducts.refresh();
        initTableView();
    }

    private void menuCategories() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../layout/categories.fxml"));
            loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.getRoot()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        initTableViewProperties();
        initTableView();
    }

    private void initTableView() {
        List<Product> productList = HttpClient.INSTANCE.getAllProducts();
        tblViewProducts.setItems(FXCollections.observableArrayList(productList));
    }

    private void initTableViewProperties() {
        colmnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colmnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colmnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colmnCategoryName.setCellValueFactory(column->new SimpleStringProperty(column.getValue().getCategory().getName()));
    }

}
