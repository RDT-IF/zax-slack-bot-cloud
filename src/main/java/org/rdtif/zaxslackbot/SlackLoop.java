package org.rdtif.zaxslackbot;

import org.rdtif.zaxslackbot.userinterface.SlackTextScreen;

public class SlackLoop {
    public void run(SlackTextScreen screen) {
        while (true) {
            screen.update();
        }
    }
}
