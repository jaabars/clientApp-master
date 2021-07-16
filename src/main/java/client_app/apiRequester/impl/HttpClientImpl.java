package client_app.apiRequester.impl;

import client_app.apiRequester.HttpClient;
import client_app.models.Category;
import client_app.models.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.*;

import java.io.IOException;
import java.util.List;


public class HttpClientImpl implements HttpClient {

    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient client = new OkHttpClient();
    private String baseCategoryUrl = "http://localhost:8080/api/v1/category/";
    private String baseProductUrl = "http://localhost:8080/api/v1/product/";

    public HttpClientImpl(){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public List<Category> getAllCategories() {
        Request request = new Request.Builder()
                .url(baseCategoryUrl+"all")
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            List<Category> categoryList = objectMapper.readValue(response.body().string(), new TypeReference<List<Category>>(){});

            return categoryList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category saveCategory(Category category) {
        try {
            String categoryJson = objectMapper.writeValueAsString(category);
            RequestBody requestBody = RequestBody.create(categoryJson,MediaType.parse("application/json; charset=UTF-8"));

            Request request = null;
            if (category.getId()!=null){
                request = new Request.Builder()
                        .url(baseCategoryUrl+"update")
                        .put(requestBody)
                        .build();
            }else {
                request = new Request.Builder()
                        .url(baseCategoryUrl+"save")
                        .post(requestBody)
                        .build();
            }
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()){
                category = objectMapper.readValue(response.body().string(),Category.class);
                return category;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Category getCategoryById(Long id) {
        Request request = new Request.Builder()
                .url(baseCategoryUrl+id)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            Category category = objectMapper.readValue(response.body().string(), Category.class);

            return category;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        Request request = new Request.Builder()
                .url(baseProductUrl+"all")
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            List<Product> productList = objectMapper.readValue(response.body().string(), new TypeReference<List<Product>>(){});

            return productList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product saveProduct(Product product) {
        try {
            String categoryJson = objectMapper.writeValueAsString(product);
            RequestBody requestBody = RequestBody.create(categoryJson,MediaType.parse("application/json; charset=UTF-8"));
            String method;
            Request request = null;
            if (product.getId()!=null){
                request = new Request.Builder()
                        .url(baseProductUrl+"update")
                        .put(requestBody)
                        .build();
            }else {
                request = new Request.Builder()
                        .url(baseProductUrl+"save")
                        .post(requestBody)
                        .build();
            }
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()){
                product = objectMapper.readValue(response.body().string(),Product.class);
                return product;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public Product getProductById(Long id) {
        Request request = new Request.Builder()
                .url(baseProductUrl+id)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();

            Product product = objectMapper.readValue(response.body().string(), Product.class);

            return product;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
