package org.rdtif.zaxslackbot.interpreter;

import com.google.inject.Inject;
import com.zaxsoft.zax.zmachine.ZCPU;
import org.rdtif.zaxslackbot.GameRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartGameAction implements Action {
    private final GameRepository gameRepository;

    @Inject
    public StartGameAction(GameRepository repository) {
        gameRepository = repository;
    }

    @Override
    public String execute(String input, LanguagePattern pattern) {
        String gameName = extractGameName(input, pattern);

        if (gameRepository.fileNames().contains(gameName)) {
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
