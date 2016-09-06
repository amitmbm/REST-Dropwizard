package com.ami.test.resources;

import com.ami.entities.Index;
import com.ami.entities.Product;
import com.ami.resources.ProductResource;
import com.ami.service.user.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

/**
 * @author: Amit Khandelwal
 * Date: 04/09/16
 */

public class ProductResourceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ProductServiceImpl productService = mock(ProductServiceImpl.class);

    @ClassRule
    public static final ResourceTestRule rule = ResourceTestRule.builder().setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new ProductResource(productService,objectMapper)).build();

    private final Product product = new Product("name","1","title","brand","desc");


    @Before
    public void setup() throws Exception {


    }

    @Test
    public void testGetProduct() throws Exception{
        when(productService.get(eq("1"))).thenReturn(product);
        Response response = rule.getJerseyTest().target("/products/1").request().get();
        verify(productService , atLeast(1)).get(any(String.class));
        Assert.assertEquals(200,response.getStatus());

    }
    @Test
    public void testPostProduct() throws Exception{
        String productAsString = objectMapper.writeValueAsString(product);
        Response response = rule.getJerseyTest().target("/products").request().post(Entity.entity(productAsString, MediaType.APPLICATION_JSON));
        verify(productService,atLeastOnce()).add(any(Product.class));
        Assert.assertEquals(201,response.getStatus());
    }

    @Test
    public void testGetAllProducts() throws Exception{
        Response response = rule.getJerseyTest().target("/products").request().get();
        Assert.assertEquals(200,response.getStatus());
        verify(productService,atMost(1)).getAll();
    }
}
