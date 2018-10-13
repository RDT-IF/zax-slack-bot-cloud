package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Inject;
import org.rdtif.zaxslackbot.GameFileRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameAction implements Action {
    private final GameFileRepository gameFileRepository;

    @Inject
    public StartGameAction(GameFileRepository repository) {
        gameFileRepository = repository;
    }

    @Override
    public String execute(String input, LanguagePattern pattern) {
        String gameName = extractGameName(input, pattern);

        if (gameFileRepository.fileNames().contains(gameName)) {
//            ZCPU cpu = new ZCPU(new SlackZUserInterface());
//            cpu.initialize("games/anchor.z8");
//            cpu.run();
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
