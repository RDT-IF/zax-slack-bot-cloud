package org.rdtif.zaxslackbot.slack;

import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class SlackDisplayMessageMakerTest {

    private final SlackDisplayMessageMaker maker = new SlackDisplayMessageMaker();

    @Test
    void neverReturnNull() {
        List<LayoutBlock> message = maker.makeMessageOf("");

        assertThat(message, notNullValue());
    }

    @Test
    void includeTextWindow() {
        List<LayoutBlock> message = maker.makeMessageOf("");

        assertThat(message.get(0).getType(), equalTo("section"));
    }

    @Test
    void includeContentInTextWindow() {
        List<LayoutBlock> message = maker.makeMessageOf("some content");

        SectionBlock section = (SectionBlock) message.get(0);
        assertThat(section.getText().getText(), equalTo("```\nsome content\n```"));
    }

    @Test
    void includeInputWindow() {
        List<LayoutBlock> message = maker.makeMessageOf("");

        assertThat(message.get(1).getType(), equalTo("input"));
    }

    @Test
    void configureInputWindow() {
        List<LayoutBlock> message = maker.makeMessageOf("some content");

        InputBlock inputBox = (InputBlock) message.get(1);
        assertThat(inputBox.getElement(), notNullValue());
        assertThat(inputBox.isOptional(), equalTo(true));
        assertThat(inputBox.getLabel().getText(), equalTo("Command Input:"));
    }

    @Test
    void escapeTripleBacktickInContent() {
        List<LayoutBlock> message = maker.makeMessageOf("```");

        SectionBlock section = (SectionBlock) message.get(0);
        String replaced = "`" + (char) 11 + "`" + (char) 11 + "`";
        assertThat(section.getText().getText(), equalTo("```\n" + replaced + "\n```"));
    }
}
