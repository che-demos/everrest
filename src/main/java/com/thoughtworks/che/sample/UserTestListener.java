package com.thoughtworks.che.sample;

import org.everrest.websockets.ServerContainerInitializeListener;
import org.everrest.websockets.WSConnectionImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.util.LinkedList;
import java.util.List;

import static javax.websocket.server.ServerEndpointConfig.Builder.create;

public class UserTestListener implements ServletContextListener {

    ServerEndpointConfig wsServerEndpointConfig;
    ServerEndpointConfig eventbusServerEndpointConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized");

        final ServletContext servletContext = sce.getServletContext();
        final ServerContainer serverContainer = (ServerContainer)servletContext.getAttribute("javax.websocket.server.ServerContainer");

        final List<Class<? extends Encoder>> encoders = new LinkedList<>();
        final List<Class<? extends Decoder>> decoders = new LinkedList<>();
        encoders.add(ServerContainerInitializeListener.OutputMessageEncoder.class);
        decoders.add(ServerContainerInitializeListener.InputMessageDecoder.class);

        ServerEndpointConfig endpointConfig = create(WSConnectionImpl.class, "/wss")
                .encoders(encoders).decoders(decoders).build();

        try {
            serverContainer.addEndpoint(endpointConfig);
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }

    private ServerEndpointConfig.Configurator createConfigurator() {
        return new ServerEndpointConfig.Configurator() {
            @Override
            public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
                super.modifyHandshake(sec, request, response);
                final HttpSession httpSession = (HttpSession) request.getHttpSession();
            }
        };
    }

    private ServerEndpointConfig createEventbusServerEndpointConfig(ServletContext servletContext) {
        return null;
    }

    private ServerEndpointConfig createWsServerEndpointConfig(ServletContext servletContext) {
        return null;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }


}
