package com.ami.resources;

import org.apache.lucene.index.ReaderSlice;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author: Amit Khandelwal
 * Date: 25/09/16
 */

@Path("/rest")
public class ProxyResource {

    private static String REST_SYSTEM="rest";



    @GET
    @Path("{id}")
    public Response proxy(@PathParam("id") String id){
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://10.0.0.3:8080/rest/")
                            .path("api/products/{id}")
                            .resolveTemplate("id",id)
                            .request(MediaType.APPLICATION_JSON)
                            .get(Response.class);


        System.out.println("Res status "+ response.getStatus());
        return response;



    }

    @GET
    public String proxy(){
        return "Hi";

    }
}
