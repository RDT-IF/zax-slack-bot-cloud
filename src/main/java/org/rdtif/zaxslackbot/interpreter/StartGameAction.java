package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Inject;
import com.zaxsoft.zax.zmachine.ZCPU;
import org.rdtif.zaxslackbot.GameFileRepository;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;
import org.rdtif.zaxslackbot.userinterface.SlackZUserInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameAction implements Action {
    private final ZaxSlackBotConfiguration configuration;
    private final GameFileRepository gameFileRepository;
    private final ZCpuFactory zCpuFactory;

    @Inject
    public StartGameAction(ZaxSlackBotConfiguration configuration, GameFileRepository gameFileRepository, ZCpuFactory zCpuFactory) {
        this.configuration = configuration;
        this.gameFileRepository = gameFileRepository;
        this.zCpuFactory = zCpuFactory;
    }

    @Override
    public String execute(String input, LanguagePattern pattern) {
        String gameName = extractGameName(input, pattern);

        if (gameFileRepository.fileNames().contains(gameName)) {
            ZCPU cpu = zCpuFactory.create(new SlackZUserInterface());
            cpu.initialize(configuration.getGameDirectory() + gameName);
            cpu.run();
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
