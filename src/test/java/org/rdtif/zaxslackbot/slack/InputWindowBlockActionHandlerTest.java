package org.rdtif.zaxslackbot.slack;

import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.userinterface.InputMode;
import org.rdtif.zaxslackbot.userinterface.InputState;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class InputWindowBlockActionHandlerTest {
    @Test
    void acknowledgeSlackEvent() throws Exception {
        InputWindowBlockActionHandler handler = new InputWindowBlockActionHandler(new InputState(InputMode.Unknown, ""));
        Response response = handler.apply(new BlockActionRequest("", "", null), new ActionContext());

        assertThat(response.getStatusCode(), equalTo(200));
    }
}