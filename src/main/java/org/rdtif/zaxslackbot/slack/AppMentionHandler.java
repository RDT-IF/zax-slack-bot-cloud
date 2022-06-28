package org.rdtif.zaxslackbot.slack;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.AppMentionEvent;
import org.apache.commons.lang3.StringUtils;
import org.rdtif.zaxslackbot.interpreter.Interpreter;

class AppMentionHandler implements BoltEventHandler<AppMentionEvent> {
    private final Interpreter interpreter;

    AppMentionHandler(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Response apply(EventsApiPayload<AppMentionEvent> payload, EventContext context) {
        try {
            AppMentionEvent event = payload.getEvent();
            String original = event.getText();
            String text = original.substring(original.indexOf(">") + 1).trim();
            if (StringUtils.isNotEmpty(text)) {
                interpreter.interpret(event.getChannel(), text);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return context.ack();
    }
}
