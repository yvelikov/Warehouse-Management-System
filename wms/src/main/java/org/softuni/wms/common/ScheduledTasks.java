package org.softuni.wms.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softuni.wms.areas.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private final SessionRegistry sessionRegistry;
    private final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    public ScheduledTasks(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }


    @Scheduled(cron = "0 0 9-18 * * MON-FRI")
    public void listLoggedInUsers() {
        final List<Object> allPrincipals = this.sessionRegistry.getAllPrincipals();
        List<User> currentlyLoggedInUsers = new ArrayList<>();
        for (final Object principal : allPrincipals) {
            if (principal instanceof User) {
                final User user = (User) principal;

                List<SessionInformation> activeUserSessions =
                        this.sessionRegistry.getAllSessions(principal, false);

                if (!activeUserSessions.isEmpty()) {
                    currentlyLoggedInUsers.add(user);
                }
            }
        }

        logger.info("Currently logged users: " + currentlyLoggedInUsers.toString());
    }
}
