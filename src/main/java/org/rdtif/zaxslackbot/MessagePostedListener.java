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
import org.rdtif.zaxslackbot.userinterface.TextScreenExtent;
import org.rdtif.zaxslackbot.userinterface.TextScreenPosition;

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
                    SlackTextScreen textScreen = new SlackTextScreen(session, event.getChannel());
                    for (int i = 0; i < 5000; i++) {
                        String pad = StringUtils.leftPad("", i % 50, " ");
                        for (String line : ASCII_ART_ROSE) {
                            textScreen.eraseLine();
                            textScreen.writeString(pad + line);
                            textScreen.translateCursorBy(new TextScreenExtent(1, 0));
                        }
                        textScreen.moveCursorTo(new TextScreenPosition(0, 0));
                        textScreen.update();
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
