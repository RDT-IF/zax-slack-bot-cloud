package org.rdtif.zaxslackbot.slack;

import com.slack.api.model.block.LayoutBlock;

import java.util.List;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

public class SlackDisplayMessageMaker {
    public List<LayoutBlock> makeMessageOf(String content) {
        return asBlocks(
                section(section -> section.text(markdownText(preformatted(escapeTripleBacktick(content)))))
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
