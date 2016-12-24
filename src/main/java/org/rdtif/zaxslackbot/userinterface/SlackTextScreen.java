package org.rdtif.zaxslackbot.userinterface;

import java.util.ArrayList;
import java.util.List;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;

// TODO: Erasing lines, Scrolling, etc.

class SlackTextScreen {
    private static final int LINES = 25;
    private static final int COLUMNS = 80;
    private final List<TextScreenLine> screenLines = new ArrayList<>(LINES);
    private final SlackSession slackSession;
    private final SlackChannel channel;
    private final ScreenPosition cursorPosition = new ScreenPosition(0, 0);

    SlackTextScreen(SlackSession slackSession, SlackChannel channel) {
        this.slackSession = slackSession;
        this.channel = channel;
        for (int i = 0; i < LINES; i++) {
            screenLines.add(new TextScreenLine(writeLine("")));
        }
    }

    private String writeLine(String line) {
        SlackMessageHandle<SlackMessageReply> handle = slackSession.sendMessage(channel, line);
        return handle.getReply().getTimestamp();
    }

    void writeString(String string) {
        if (cursorPosition.getColumn() + string.length() > COLUMNS) {
            throw new RuntimeException("Out of bounds");
        }
        TextScreenLine line = screenLines.get(cursorPosition.getRow());
        String left = line.getText().substring(0, cursorPosition.getColumn());
        String right = line.getText().substring(cursorPosition.getColumn() + string.length());
        line.setText(left + string + right);
        line.setTimeStamp(updateLine(line));
    }

    void scroll(int lines) {
        if (lines == 0) {
            return;
        }

        for (int i = 0; i < lines; i++) {
            screenLines.get(i).setText(screenLines.get(i + 1).getText());
        }
        screenLines.get(lines).setText("");
    }

    void eraseLine() {
        screenLines.get(cursorPosition.getRow()).setText("");
    }

    void update() {
        screenLines.stream().filter(TextScreenLine::hasChanged).forEach(line -> line.setTimeStamp(updateLine(line)));
    }

    private String updateLine(TextScreenLine line) {
        SlackMessageHandle<SlackMessageReply> handle = slackSession.updateMessage(line.getTimeStamp(), channel, line.getText());
        return handle.getReply().getTimestamp();
    }
}
