package org.rdtif.zaxslackbot.userinterface;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.GenericSlackReply;

import java.util.HashMap;
import java.util.Map;

class SlackDisplayMessageUpdater {
    private static final String CHAT_MESSAGE_POST = "chat.postMessage";
    private static final String CHAT_MESSAGE_UPDATE = "chat.update";
    private final SlackSession slackSession;
    private final ObjectMapper mapper;
    private final String channelID;
    private String slackDisplayMessageTimestamp;

    SlackDisplayMessageUpdater(SlackSession slackSession, String channelID) {
        this.slackSession = slackSession;
        this.channelID = channelID;
        this.mapper = new ObjectMapper();
        mapper.setConfig(mapper.getDeserializationConfig().without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
    }

    String postMessage(String content) {
        slackDisplayMessageTimestamp = postToSlack(CHAT_MESSAGE_POST, content, new HashMap<>());
        return "";
    }

    String updateMessage(String content) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("ts", slackDisplayMessageTimestamp);
        slackDisplayMessageTimestamp = postToSlack(CHAT_MESSAGE_UPDATE, content, parameters);
        return "";
    }

    private String postToSlack(String command, String content, Map<String, String> parameters) {
        parameters.put("channel", channelID);
        parameters.put("blocks", constructBlocks(processContent(content)));
        SlackMessageHandle<GenericSlackReply> handle = slackSession.postGenericSlackCommand(parameters, command);
        try {
            return mapper.readValue(handle.getReply().getPlainAnswer(), MessageUpdateResponse.class).ts;
        } catch (JsonProcessingException exception) {
            return "";
        }
    }

    private String processContent(String content) {
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        return "```\n" + content.replaceAll("```", replaceActualBackTicks) + "\n```";
    }

    private static record MessageUpdateResponse(String ts) {
    }

    private String constructBlocks(String content) {
        return "[" +
                "  {" +
                "    \"type\": \"section\"," +
                "    \"text\": {" +
                "      \"type\": \"mrkdwn\"," +
                "      \"text\": \"" + content + "\"" +
                "    }" +
                "  }," +
                "  {" +
                "    \"type\": \"input\"," +
                "    \"block_id\": \"input123\"," +
                "    \"label\": {" +
                "      \"type\": \"plain_text\"," +
                "      \"text\": \" \"" +
                "    }," +
                "    \"element\": {" +
                "      \"type\": \"plain_text_input\"," +
                "      \"action_id\": \"plain_input\"," +
                "      \"placeholder\": {" +
                "        \"type\": \"plain_text\"," +
                "        \"text\": \"Enter some plain text\"" +
                "      }" +
                "    }" +
                "  }" +
                "]";
    }
}
