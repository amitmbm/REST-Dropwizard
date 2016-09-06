package com.ami.service.user;

import com.ami.constants.ApplicationConstants;
import com.ami.entities.Product;
import com.ami.exceptions.ServiceError;
import com.ami.exceptions.ServiceException;
import com.ami.service.IndexService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

@Getter
@Setter
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private IndexService indexService;
    private ObjectMapper mapper;
    private Map<String, Product> products = new ConcurrentHashMap<>();

    @Inject
    public ProductServiceImpl(IndexService indexService, ObjectMapper mapper) {
        this.indexService = indexService;
        this.mapper = mapper;
    }

    @Override
    /**
     * TODO :- Need to generate the product uuid.
     */
    public Product add(Product product) throws Exception {
        product.setId(UUID.randomUUID().toString());
        try {
            indexService.post(ApplicationConstants.productIdxName, ApplicationConstants.productIdxType, product.getId(), mapper.writeValueAsString(product));
        } catch (JsonProcessingException e) {
            logger.error("Error in serializing product", e);
            throw new ServiceException(500, ServiceError.INTERNAL_ERROR, e.getMessage());
        }
        return product;
    }

    @Override
    public Product update(Product Product, String id) throws Exception {
        return null;
    }

    @Override
    public Product get(String id) throws IOException {
        GetResponse response = indexService.get(ApplicationConstants.productIdxName, ApplicationConstants.productIdxType, id);
        Product product = null;
        if (response.isExists()) {
            product = mapper.readValue(mapper.writeValueAsString(response.getSource()), Product.class);
        }
        return product;
    }

    @Override
    public List<Product> getAll() throws Exception {
        SearchResponse searchResponse = indexService.getAll(ApplicationConstants.productIdxName);
        List<Product> products = new ArrayList<>();
        Product product = null;
        for (SearchHit searchHit : searchResponse.getHits()) {
            product = mapper.readValue(mapper.writeValueAsString(searchHit.getSource()), Product.class);
            products.add(product);
        }
        return products;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }
}
