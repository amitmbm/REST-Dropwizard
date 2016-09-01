package com.ami.resources;

import com.ami.entities.Product;
import com.ami.service.user.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by amit.khandelwal on 14/08/16.
 */

@Path("/products")
@Api("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);

    @NotNull
    private ObjectMapper mapper;

    @NotNull
    private ProductService productService;


    @Inject
    public ProductResource(ProductService productService, ObjectMapper mapper){
        this.productService = productService;
        this.mapper = mapper;
    }


    /*
    *
    *
    * <p>index a product document in ES.</p>
    * { "name":"product_name",
    * }
    * */
    @POST
    @ApiOperation(value = "To index a product", notes = "API to index a product to ES", response = Product.class)
    public Response indexProduct(@Valid Product product) throws Exception {
        logger.info("Index product api called with payload",product.toString());
        product.setId(UUID.randomUUID().toString());
        productService.add(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }





    //get the specific product.
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get a specific product", notes = "Get a specific product")
    public Response getProduct(@PathParam("id") String productId) throws Exception {
        logger.info("Getting a product.");
        return Response.status(Response.Status.OK).entity(productService.get(productId)).build();
    }

    //get all specific product.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all products", notes = "Get all products")
    public Response getAllProducts() throws Exception {
        logger.info("Getting all product.");
        return Response.status(Response.Status.OK).entity(productService.getAll()).build();
    }
}
