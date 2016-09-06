package com.ami.test.services;

import com.ami.entities.Product;
import com.ami.service.IndexServiceImpl;
import com.ami.service.user.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author: Amit Khandelwal
 * Date: 04/09/16
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private static IndexServiceImpl indexService;

    private static TransportClient esClient;

    @InjectMocks
    private static ProductServiceImpl productService;

    private static GetResponse getResponse;
    private static ObjectMapper mockedObjectMapper;
    private static SearchResponse searchResponse;
    private static SearchHits searchHits;
    private static Product product;
    private static List<Product> products = new ArrayList<>();
    @Before
    public void setup() throws JsonProcessingException {
        getResponse = mock(GetResponse.class);
        mockedObjectMapper = mock(ObjectMapper.class);
        esClient = mock(TransportClient.class);
        searchResponse = mock(SearchResponse.class);
        searchHits = mock(SearchHits.class);
        initMocks(this);
        //When
        product = new Product();
        product.setBrand("brand");
        product.setDescription("desc");
        product.setName("name");
        product.setTitle("title");
        products.add(product);
        products.add(new Product());
    }

    @Test
    public void testAdd() throws Exception{

        //then
        Product retProduct = productService.add(product);
        //verify
        Assert.assertNotNull(retProduct);
        System.out.println("pr"+ retProduct.toString());
        verify(indexService,atLeastOnce()).post(anyString(),anyString(),anyString(),anyString());
    }


    @Test
    public void testGet() throws Exception{
        // when
        when(indexService.get(anyString(),anyString(),anyString())).thenReturn(getResponse);
        when(getResponse.isExists()).thenReturn(true);
        //when(getResponse.getSource()).thenReturn(map);
        when(mockedObjectMapper.readValue(mockedObjectMapper.writeValueAsString(getResponse.getSource()),Product.class)).thenReturn(product);
        Product product1 = productService.get("1");
        Assert.assertNotNull(product1);
        Assert.assertEquals(product.getBrand(),product1.getBrand());
        Assert.assertEquals(product.getName(),product1.getName());
        verify(indexService,atLeastOnce()).get(anyString(),anyString(),anyString());
    }

    //TODO :- Need to work on this.

    //@Test
    public void testGetAll() throws Exception{
        // when
        when(indexService.getAll(anyString())).thenReturn(searchResponse);
        //when(searchResponse.getHits()).thenReturn(searchHits);
      //  doReturn(searchHits).when(searchResponse.getHits());
        List<Product> product1 = productService.getAll();
        Assert.assertNotNull(product1);
        Assert.assertEquals(2,product1.size());
        verify(indexService,atLeastOnce()).getAll(anyString());
    }

    
}
