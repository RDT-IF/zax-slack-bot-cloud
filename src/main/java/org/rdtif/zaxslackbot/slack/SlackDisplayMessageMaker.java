package org.rdtif.zaxslackbot.slack;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.DispatchActionConfig;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.PlainTextInputElement;

import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public class SlackDisplayMessageMaker {
    public List<LayoutBlock> makeMessageOf(String content) {
        DispatchActionConfig dispatchActionConfig = new DispatchActionConfig();
        dispatchActionConfig.setTriggerActionsOn(Arrays.asList("on_enter_pressed", "on_character_entered"));

        PlainTextInputElement inputElement = new PlainTextInputElement();
        inputElement.setFocusOnLoad(true);
        inputElement.setDispatchActionConfig(dispatchActionConfig);

        return asBlocks(
                section(section -> section.text(markdownText(preformatted(escapeTripleBacktick(content))))),
                input(input -> input.element(inputElement).label(new PlainTextObject("Command Input:", false)).optional(true))
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
