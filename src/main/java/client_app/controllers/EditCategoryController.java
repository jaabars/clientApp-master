package client_app.controllers;

import client_app.apiRequester.HttpClient;
import client_app.models.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import okhttp3.*;

import java.io.IOException;

public class EditCategoryController {

    private Category category;




    @FXML
    private TextField txtName;

    @FXML
    private CheckBox checkActive;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    void onButtonClicked(ActionEvent event){

        if (event.getSource().equals(btnSave)){
            saveCategory();
        }else if (event.getSource().equals(btnCancel)){
            btnCancel.getScene().getWindow().hide();
        }

    }

    private void saveCategory() {
        category.setName(txtName.getText());
        category.setActive(checkActive.isSelected());
        category = HttpClient.INSTANCE.saveCategory(category);
        if (category == null){
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

    public void init(Category category){
        this.category = category;
        if (category.getId()!=null){
            this.category = HttpClient.INSTANCE.getCategoryById(category.getId());
            txtName.setText(category.getName());
            checkActive.setSelected(category.isActive());
        }
    }

}
