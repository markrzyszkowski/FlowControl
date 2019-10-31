package com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.notifications;

public final class Notifications {

    private Notifications() {
    }

    public static final class Tasks {

        public static final String NEW_TASK = "New tasks are awaiting assignment";
        public static final String TASK_UNASSIGNED = "Previously assigned tasks are awaiting assignment";

        private Tasks() {
        }
    }

    public static final class Time {

        public static final String NEW_TIME_CARD = "%s added a new daily time report";

        private Time() {
        }
    }
}
