package com.thoughtworks.che.user;

import org.everrest.core.impl.provider.json.JsonUtils;
import org.everrest.websockets.WSConnectionContext;
import org.everrest.websockets.message.ChannelBroadcastMessage;

import javax.websocket.EncodeException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Path("users")
public class UserService {

    private int n=0;

    @Path("schedule")
    @GET
    @Produces("application/json")
    public Map get(@PathParam("id") String id) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        Runnable counter = new Runnable() {
            @Override
            public void run() {
                sendMessage(String.valueOf(n++));
            }
        };

        schedule.scheduleAtFixedRate(counter, 1, 1, TimeUnit.SECONDS);

        Map result = new HashMap<>();
        result.put("id", id);
        return result;
    }

    @Path("exec")
    @GET
    @Produces("application/json")
    public Map exec() {
        Process p;
        try {
            p = Runtime.getRuntime().exec("ls");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while((line = reader.readLine())!= null) {
                sendMessage(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map result = new HashMap<>();
        result.put("status", 200);
        return result;
    }

    private void sendMessage(String msg) {

        ChannelBroadcastMessage cbm = new ChannelBroadcastMessage();
        cbm.setChannel("test");
        cbm.setBody(JsonUtils.getJsonString(msg));

        try {
            WSConnectionContext.sendMessage(cbm);
        } catch (EncodeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
