package com.ami.resources;

import com.ami.entities.Index;
import com.ami.service.admin.ClusterService;
import com.ami.service.user.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by amit.khandelwal on 15/08/16.
 */

@Path("/admin/cluster/indexes")
@Api("/admin/cluster/indexes")
@Produces(MediaType.TEXT_PLAIN)
public class ClusterResource {

    private static final Logger logger = LoggerFactory.getLogger(ClusterResource.class);

    @NotNull
    private ObjectMapper mapper;

    @NotNull
    private ClusterService clusterService;


    @Inject
    public ClusterResource(ClusterService clusterService, ObjectMapper mapper){
        this.clusterService = clusterService;
        this.mapper = mapper;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "To register a index in es cluster", notes = "API to create a index", response = String.class)
    public Response createIndex(@Valid Index index){
        logger.info("Create index api called with payload name:{},and type {}", index.getName(),index.getType());
        clusterService.createIndex(index.getName(),index.getType(),null);
        return Response.status(Response.Status.CREATED).entity("index created successfully").build();

    }
}
