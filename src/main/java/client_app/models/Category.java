package client_app.models;


import lombok.Data;

@Data
public class Category {

    private Long id;
    private String name;
    private boolean active;

}
