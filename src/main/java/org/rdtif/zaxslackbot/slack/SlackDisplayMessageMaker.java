package org.rdtif.zaxslackbot.slack;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.DispatchActionConfig;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.PlainTextInputElement;
import org.rdtif.zaxslackbot.userinterface.InputMode;
import org.rdtif.zaxslackbot.userinterface.InputState;

import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public class SlackDisplayMessageMaker {
    private final InputState inputState;

    public SlackDisplayMessageMaker(InputState inputState) {
        this.inputState = inputState;
    }

    public List<LayoutBlock> makeMessageOf(String content) {
        DispatchActionConfig dispatchActionConfig = new DispatchActionConfig();
        String triggerAction = inputState.mode == InputMode.Character ? "on_character_entered" : "on_enter_pressed";
        dispatchActionConfig.setTriggerActionsOn(List.of(triggerAction));

        PlainTextInputElement inputElement = new PlainTextInputElement();
        inputElement.setFocusOnLoad(true);
        inputElement.setDispatchActionConfig(dispatchActionConfig);
        inputElement.setActionId("window-container");

        return asBlocks(
                section(section -> section.text(markdownText(preformatted(escapeTripleBacktick(content))))),
                input(input -> input.element(inputElement)
                        .label(new PlainTextObject("Command Input:", false))
                        .blockId("input-window")
                        .optional(true)
                        .dispatchAction(true)
                )
        );
    }

    private String preformatted(String content) {
        return "```\n" + content + "\n```";
    }

    private String escapeTripleBacktick(String content) {
        String replaceActualBackticks = "`" + (char) 11 + "`" + (char) 11 + "`";
        return content.replaceAll("```", replaceActualBackticks);
    }
}
