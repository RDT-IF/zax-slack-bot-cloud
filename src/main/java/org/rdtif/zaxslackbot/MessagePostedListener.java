package org.rdtif.zaxslackbot;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.apache.commons.lang3.StringUtils;
import org.rdtif.zaxslackbot.interpreter.LanguageProcessor;
import org.rdtif.zaxslackbot.userinterface.SlackTextScreen;
import org.rdtif.zaxslackbot.userinterface.Extent;
import org.rdtif.zaxslackbot.userinterface.Position;

class MessagePostedListener implements SlackMessagePostedListener {
    private final LanguageProcessor languageProcessor;

    @Inject
    MessagePostedListener(LanguageProcessor languageProcessor) {
        this.languageProcessor = languageProcessor;
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if (!event.getSender().isBot()) {
            String messageContent = event.getMessageContent();
            String tag = "<@" + session.sessionPersona().getId() + ">";
            if (event.getChannel().isDirect() || messageContent.contains(tag)) {
                boolean inGame = true;
                if (inGame) {
                    try {
                        SlackTextScreen textScreen = new SlackTextScreen(session, event.getChannel(), new Extent(30, 80));
                        textScreen.initialize();
                        for (int i = 0; i < 5000; i++) {
                            String pad = StringUtils.leftPad("", i % 50, " ");
                            for (String line : ASCII_ART_ROSE) {
                                textScreen.eraseLine();
                                textScreen.print(pad + line);
                                textScreen.moveCursorBy(new Extent(1, 0));
                            }
                            textScreen.moveCursorTo(new Position(0, 0));
                            textScreen.update();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    String message = languageProcessor.responseTo(messageContent.replaceAll(tag, "").trim());
                    session.sendMessage(event.getChannel(), message);
                }
            }
        }
    }

    private static final List<String> ASCII_ART_ROSE = Arrays.asList(
            "        .     .",
            "    ...  :``..':",
            "     : ````.'   :''::'",
            "   ..:..  :     .'' :",
            "``.    `:    .'     :",
            "    :    :   :        :",
            "     :   :   :         :",
            "     :    :   :        :",
            "      :    :   :..''''``::.",
            "       : ...:..'     .''",
            "       .'   .'  .::::'",
            "      :..'''``:::::::",
            "      '         `::::",
            "                  `::.",
            "                   `::",
            "                    :::.",
            "         ..:```.:'`. ::'`.",
            "       ..'      `:.: ::",
            "      .:        .:``:::",
            "      .:    ..''     :::",
            "       : .''         .::",
            "        :          .'`::",
            "                       ::",
            "                       ::",
            "                        :",
            "                        :",
            "                        :",
            "                        :",
            "                        ."
    );
}
