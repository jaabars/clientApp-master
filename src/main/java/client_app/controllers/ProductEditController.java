package client_app.controllers;

import client_app.apiRequester.HttpClient;
import client_app.models.Category;
import client_app.models.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Jaabars
 */
public class ProductEditController {

    private Product product;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtProductName;

    @FXML
    private ComboBox<Category> cmbCategories;

    @FXML
    private Spinner<Double> spnPrice;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    void onButtonClicked(ActionEvent event) {
        if (event.getSource().equals(btnSave)){
            saveCategory();

        }
    }

    private void saveCategory() {
        product.setName(txtProductName.getText());
        product.setPrice(Double.valueOf(spnPrice.getEditor().getText()));
        product.setCategory(HttpClient.INSTANCE.getCategoryById(cmbCategories.getSelectionModel().getSelectedItem().getId()));
        product = HttpClient.INSTANCE.saveProduct(product);
        if (product == null){
            showALert("Не удалось сохранить");
            return;
        }else {
            showALert("Успешно");
        }
    }

    private void showALert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,message);
        alert.show();
    }

    @FXML
    void initialize() {
        initCmbCategories();
        initSpinner();
    }

    private void initSpinner() {
        spnPrice.setEditable(true);
        spnPrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,10000000,0,2));
    }

    private void initCmbCategories() {
        List<Category> categoryList = HttpClient.INSTANCE.getAllCategories();
        cmbCategories.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
        cmbCategories.setItems(FXCollections.observableArrayList(categoryList));
    }

    public void setProduct(Product product){
        this.product = product;
        if (product.getId()!=null){
            this.product = HttpClient.INSTANCE.getProductById(product.getId());
            txtProductName.setText(product.getName());
            spnPrice.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,1000000,product.getPrice(),2));
            cmbCategories.getSelectionModel().select(product.getCategory());
        }
    }
}
