package com.ami.resources;

import com.ami.entities.Session;
import com.ami.service.user.SessionService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

/**
 * @author: Amit Khandelwal
 * Date: 24/09/16
 */

@Path("/session")
public class SessionResource {

    private static final Logger logger = LoggerFactory.getLogger(SessionResource.class);

    @Inject
    private SessionService sessionService;

    @POST
    public Response createSession(Session session){
        logger.info("save response for key {} is {}",session.getKey(),sessionService.save(session));
        return Response.status(Response.Status.CREATED).entity("session with key"+session.getKey()+"created").build();
    }

    @GET
    @Path("{key}")
    public Response getSession(@QueryParam("key") String key){
        return Response.status(Response.Status.OK).entity(sessionService.get(key)).build();
    }

}
