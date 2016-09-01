package com.ami.service.user;

import com.ami.entities.Product;

import java.util.List;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

public interface ProductService {
    public Product add(Product Product) throws Exception;
    public Product update(Product Product, String id) throws Exception;
    public Product get(String id) throws Exception;
    public List<Product> getAll() throws Exception;
    public boolean delete(String id) throws Exception;

}
