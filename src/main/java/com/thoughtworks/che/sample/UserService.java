package com.thoughtworks.che.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

@Path("users")
public class UserService {

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Map get(@PathParam("id") String id) {
        Map result = new HashMap<>();
        result.put("id", id);
        return result;
    }
}
