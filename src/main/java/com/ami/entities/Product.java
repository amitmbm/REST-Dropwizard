package com.ami.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class Product {

    @NotEmpty
    private String name;
    private String id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String brand;
    @NotEmpty
    @Size(max = 1024)
    private String description;

}
