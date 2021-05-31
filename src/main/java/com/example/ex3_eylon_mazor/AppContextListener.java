package com.example.ex3_eylon_mazor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The type App context listener. initiate the DB before everything, and save the request counter (ID).
 */
@WebListener
public class AppContextListener implements ServletContextListener {
    /**
     * The request counter - ID for each crawling.
     */
    static int requestCounter = 0;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        FullDataBase fullDataBase = new FullDataBase();
        servletContext.setAttribute("fullDB", fullDataBase);
    }
}