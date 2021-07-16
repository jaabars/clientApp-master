package client_app.models;

import lombok.Data;


@Data
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Category category;
}
