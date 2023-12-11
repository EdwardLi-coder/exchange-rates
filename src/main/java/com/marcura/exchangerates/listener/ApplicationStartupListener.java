package com.marcura.exchangerates.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final WebApplicationContext webApplicationContext;

    public ApplicationStartupListener(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            int port = webApplicationContext.getEnvironment().getProperty("local.server.port", Integer.class, 8080);
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String applicationUrl = "http://" + hostAddress + ":" + port;
            System.out.println("Application is running! Access URLs:");
            System.out.println("Local: \t\thttp://localhost:" + port);
            System.out.println("External: \t" + applicationUrl);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
