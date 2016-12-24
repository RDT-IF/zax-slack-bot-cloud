package org.rdtif.zaxslackbot.userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;

class SlackTextScreen {
    private static final int LINES = 25;
    private static final int COLUMNS = 80;
    private final List<TextScreenLine> screenLines = new ArrayList<>(LINES);
    private final SlackSession slackSession;
    private final SlackChannel channel;
    private final ScreenPosition cursorPosition = new ScreenPosition(0, 0);
    private String slackMessageTimestamp;

    SlackTextScreen(SlackSession slackSession, SlackChannel channel) {
        this.slackSession = slackSession;
        this.channel = channel;
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < LINES; i++) {
            screenLines.add(new TextScreenLine());
        }
        SlackMessageHandle<SlackMessageReply> handle = slackSession.sendMessage(channel, "");
        slackMessageTimestamp = handle.getReply().getTimestamp();
    }

    void writeString(String string) {
        if (cursorPosition.getColumn() + string.length() > COLUMNS) {
            throw new RuntimeException("Out of bounds");
        }
        TextScreenLine line = screenLines.get(cursorPosition.getRow());
        String left = line.getText().substring(0, cursorPosition.getColumn());
        String right = line.getText().substring(cursorPosition.getColumn() + string.length());
        line.setText(left + string + right);
    }

    void scroll(int lines) {
        if (lines == 0) {
            return;
        }

        for (int i = 0; i < lines; i++) {
            screenLines.get(i).setText(screenLines.get(i + 1).getText());
        }
        screenLines.get(lines).setText("");
        update();
    }

    void eraseLine() {
        screenLines.get(cursorPosition.getRow()).setText("");
    }

    void update() {
        String message = screenLines.stream().map(TextScreenLine::getText).collect(Collectors.joining("\n"));
        slackMessageTimestamp = slackSession.updateMessage(slackMessageTimestamp, channel, message).getReply().getTimestamp();
    }
}
