package org.rdtif.zaxslackbot.slack;

import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.rdtif.zaxslackbot.userinterface.InputMode;
import org.rdtif.zaxslackbot.userinterface.InputState;

class InputWindowBlockActionHandler implements BlockActionHandler {
    private final InputState inputState;

    public InputWindowBlockActionHandler(InputState inputState) {
        this.inputState = inputState;
    }

    @Override
    public Response apply(BlockActionRequest blockActionRequest, ActionContext context) {
        if (inputState.mode == InputMode.Character) {
            inputState.currentInput = String.valueOf(blockActionRequest.getPayload().getActions().get(0).getValue().charAt(0));
        }
        return context.ack();
    }
}