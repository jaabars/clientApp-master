package client_app.apiRequester;


import client_app.apiRequester.impl.HttpClientImpl;
import client_app.models.Category;
import client_app.models.Product;

import java.util.List;

public interface HttpClient {

    HttpClient INSTANCE = new HttpClientImpl();

    List<Category> getAllCategories();
    Category saveCategory(Category category);
    Category getCategoryById(Long id);

    List<Product> getAllProducts();
    Product saveProduct(Product product);

    Product getProductById(Long id);

}
