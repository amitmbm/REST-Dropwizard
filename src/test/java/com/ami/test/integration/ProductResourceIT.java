package com.ami.test.integration;

import com.ami.DropWizardApp;
import com.ami.ApplicationConfig;
import com.ami.entities.Index;
import com.ami.entities.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author: Amit Khandelwal
 * Date: 01/09/16
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductResourceIT {
    private static final Logger logger = LoggerFactory.getLogger(ProductResourceIT.class);

    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("integration-test.yml");
    private static final Product product = new Product("name", "id", "title", "brand", "desc");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String IDX_NAME = "integration-idx";
    private static final String LOCALHOST = "http://localhost:";
    private static Client client;
    private static String productId;
    private final Index index = new Index(IDX_NAME, "integ");

    @ClassRule
    public static final DropwizardAppRule<ApplicationConfig> rule = new DropwizardAppRule<ApplicationConfig>(DropWizardApp.class, CONFIG_PATH);

    @Before
    public void setup() throws JsonProcessingException {
        client = new JerseyClientBuilder().build();
        logger.info("creating idx:{} for test", index);
        Response response = client.target("http://localhost:" + rule.getLocalPort() + "/admin/cluster/indexes").request()
                .post(Entity.entity(objectMapper.writeValueAsString(index), MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());

    }

    @Test
    public void testaPostProduct() throws Exception {
       logger.info("Indexing a product document: {} in es-index: {}",product,IDX_NAME);
       Response response = client.target(LOCALHOST+rule.getLocalPort()+"/products").request()
               .post(Entity.entity(objectMapper.writeValueAsString(product),MediaType.APPLICATION_JSON));
        Product retProd = response.readEntity(new GenericType<Product>(){});
        Assert.assertEquals(201,response.getStatus());
        Assert.assertEquals("name",retProd.getName());
        productId = retProd.getId();
        System.out.println("Created product id is: "+productId);
    }

    @Test
    public void testbGetProduct() throws Exception{
        logger.info("GET a product document with id: {} from es-index: {}",productId,product,IDX_NAME);
        Response response = client.target(LOCALHOST+rule.getLocalPort()+"/products/"+productId).request().get();
        Product retProd = response.readEntity(new GenericType<Product>(){});
        Assert.assertEquals(200,response.getStatus());
        Assert.assertEquals("name",retProd.getName());
    }

    @Test // This will not fetch the created doc as es by default fetch just 10 docs.
    public void testcGetProducts() throws Exception{
        logger.info("GET all product documents from es-index: {}",IDX_NAME);
        Response response = client.target(LOCALHOST+rule.getLocalPort()+"/products").request().get();
        List<Product> productList = response.readEntity(new GenericType<List<Product>>(){});
        Assert.assertEquals(200,response.getStatus());
        // filter the previously created product , so that it can be used in Assert
        Product result = productList.stream()
                .filter(product -> productId.equals(product.getId()))
                .findAny().orElse(null);
       Assert.assertTrue(productList.size() > 1);

    }

    @After
    public void tearDown() throws Exception {
        logger.info("deleting the idx: {}", IDX_NAME);
        Response response = client.target("http://52.66.123.19:9200/" + IDX_NAME+"/").request().delete();
        if (response.getStatus() != 200) {
            logger.error("Error in deleting the idx {}", IDX_NAME);
            Assert.fail();
        }
        logger.info("idx: {} deleted successfully",IDX_NAME);
    }

}
