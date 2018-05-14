package ru.vainahtelecom.listeners;

import ru.vainahtelecom.config.AppConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CustomServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppConfig.init(sce.getServletContext());
    }
}
