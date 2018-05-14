package ru.vainahtelecom.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by REsterWolf on 23.04.2017.
 */
public class MySessionListener implements HttpSessionListener {

    private static AtomicInteger activeSessions = new AtomicInteger();

    public void sessionCreated(HttpSessionEvent se) {
        activeSessions.incrementAndGet();
        se.getSession().setAttribute("countSession", activeSessions);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        if (activeSessions.get() > 0)
            activeSessions.decrementAndGet();
        se.getSession().setAttribute("countSession", activeSessions);
    }

    public static int getActiveSessions() {
        return activeSessions.get();
    }

}
