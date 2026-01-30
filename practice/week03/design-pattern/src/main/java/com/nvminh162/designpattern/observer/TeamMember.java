package com.nvminh162.designpattern.observer;

/**
 * Concrete Observer for Task Status Notification
 * Represents a team member who receives notifications when task status changes
 */
public class TeamMember implements Observer {
    private String name;

    public TeamMember(String name) {
        this.name = name;
    }

    @Override
    public void update(String subjectName, Object data) {
        if (data instanceof String) {
            String status = (String) data;
            System.out.println("[Team Member] " + name + " received notification: " 
                + subjectName + " task status is now \"" + status + "\"");
        }
    }

    public String getName() {
        return name;
    }
}
