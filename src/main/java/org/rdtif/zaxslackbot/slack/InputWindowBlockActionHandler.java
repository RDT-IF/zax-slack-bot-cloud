package org.rdtif.zaxslackbot.slack;

import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.rdtif.zaxslackbot.userinterface.InputMode;
import org.rdtif.zaxslackbot.userinterface.InputState;

class InputWindowBlockActionHandler implements BlockActionHandler {
    private final InputState inputState;

    public InputWindowBlockActionHandler(InputState inputState) {
        this.inputState = inputState;
    }

    @Override
    public Response apply(BlockActionRequest blockActionRequest, ActionContext context) {
        System.out.println("-------------------------------------------------------");
        System.out.println(blockActionRequest.getPayload());
        System.out.println("-------------------------------------------------------");
        if (inputState.mode == InputMode.Character) {
            String value = blockActionRequest.getPayload().getActions().get(0).getValue();
            if (StringUtils.isNotEmpty(value)) {
                inputState.currentInput = String.valueOf(value.charAt(0));
                System.out.println("CurrentInput: `" + inputState.currentInput + "` ");
            }
        }
        return context.ack();
    }
}
