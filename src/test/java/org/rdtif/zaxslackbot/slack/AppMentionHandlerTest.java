package org.rdtif.zaxslackbot.slack;

import com.slack.api.app_backend.events.payload.AppMentionPayload;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.event.AppMentionEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.rdtif.zaxslackbot.interpreter.Interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class AppMentionHandlerTest {
    @Test
    void publishesAppMentionEvent() {
        Interpreter interpreter = mock(Interpreter.class);

        AppMentionHandler handler = new AppMentionHandler(interpreter);
        handler.apply(createPayload("event text"), new EventContext());

        verify(interpreter).interpret("event channel", "event text");
    }

    @Test
    void publishesAppMentionEventWithoutTheID() {
        Interpreter interpreter = mock(Interpreter.class);

        AppMentionHandler handler = new AppMentionHandler(interpreter);
        handler.apply(createPayload("<@ID> event text"), new EventContext());

        verify(interpreter).interpret("event channel", "event text");
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void publishesAppMentionEventHandlesEmptyInput(String text) {
        Interpreter interpreter = mock(Interpreter.class);

        AppMentionHandler handler = new AppMentionHandler(interpreter);
        handler.apply(createPayload("<@ID>" + text), new EventContext());

        verify(interpreter, never()).interpret(anyString(), anyString());
    }

    @Test
    void acknowledgeSlackEvent() {
        Interpreter interpreter = mock(Interpreter.class);

        AppMentionHandler handler = new AppMentionHandler(interpreter);
        Response response = handler.apply(createPayload("event text"), new EventContext());

        assertThat(response.getStatusCode(), equalTo(200));
    }

    private AppMentionPayload createPayload(String eventText) {
        AppMentionPayload payload = new AppMentionPayload();
        AppMentionEvent event = new AppMentionEvent();
        event.setChannel("event channel");
        event.setText(eventText);
        payload.setEvent(event);
        return payload;
    }
}
