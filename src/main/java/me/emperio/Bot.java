package me.emperio;

import me.emperio.commands.SuggestionCommand;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.time.Instant;

public class Bot {

    public static void main(String[] args) throws LoginException {
        JDA jda = new JDABuilder(ConfigData.token).build();
        jda.addEventListener(new SuggestionCommand());
        jda.getPresence().setGame(Game.of(Game.GameType.WATCHING, "+help"));

    }

}
