package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Inject;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.zaxsoft.zax.zmachine.ZCPU;
import org.rdtif.zaxslackbot.GameFileRepository;
import org.rdtif.zaxslackbot.SlackLoop;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;
import org.rdtif.zaxslackbot.userinterface.Extent;
import org.rdtif.zaxslackbot.userinterface.SlackTextScreen;
import org.rdtif.zaxslackbot.userinterface.SlackZUserInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameAction implements Action {
    private final ZaxSlackBotConfiguration configuration;
    private final SlackSession session;
    private final GameFileRepository gameFileRepository;
    private final ZCpuFactory zCpuFactory;

    @Inject
    public StartGameAction(ZaxSlackBotConfiguration configuration, SlackSession session, GameFileRepository gameFileRepository, ZCpuFactory zCpuFactory) {
        this.configuration = configuration;
        this.session = session;
        this.gameFileRepository = gameFileRepository;
        this.zCpuFactory = zCpuFactory;
    }

    @Override
    public String execute(SlackChannel channel, String input, LanguagePattern pattern) {
        String gameName = extractGameName(input, pattern);

        if (gameFileRepository.fileNames().contains(gameName)) {
            SlackTextScreen screen = new SlackTextScreen(session, channel, new Extent(30, 80));
            ZCPU cpu = zCpuFactory.create(new SlackZUserInterface(screen));
            cpu.initialize(configuration.getGameDirectory() + gameName);
            cpu.start();
            Executors.newSingleThreadExecutor().execute(() -> new SlackLoop().run(screen));
            return pattern.responseFor("start") + " " + gameName;
        }

        return pattern.responseFor("default");
    }

    private String extractGameName(String input, LanguagePattern pattern) {
        Pattern regex = Pattern.compile(pattern.getPattern());
        Matcher matcher = regex.matcher(input);

        if (matcher.find()) {
            return matcher.group(2);
        }

        return "";
    }

}
