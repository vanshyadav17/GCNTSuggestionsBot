package me.emperio.commands;

import me.emperio.ConfigData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class SuggestionCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){

        String message = e.getMessage().getContentRaw();
        String[] args = message.split(" ");

        if(args[0].equalsIgnoreCase("+suggest")){

            if(args.length >= 3){

                String suggestion = "";
                String pluginName = "";
                int i = 0;

                for(String each : args){
                    if(i == 1){
                        pluginName = each;
                    }
                    else if(i > 1){
                        suggestion = suggestion + " " + each;
                    }
                    i++;
                }

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("**New Suggestion**");
                embedBuilder.addField("Plugin ID", pluginName, false);
                embedBuilder.addField("Suggestion", suggestion, false);
                embedBuilder.setFooter("Suggestion by " + e.getAuthor().getName(), e.getAuthor().getAvatarUrl());
                embedBuilder.setColor(Color.CYAN);

                e.getGuild().getTextChannelById(ConfigData.suggestionChannel).sendMessage(embedBuilder.build()).queue(m -> {
                    m.addReaction("✅").queue();
                    m.addReaction("❌").queue();
                });

                EmbedBuilder sent = getRawError("Suggestion added to <#" + ConfigData.suggestionChannel + ">");
                sent.setColor(Color.CYAN);

                e.getChannel().sendMessage(sent.build()).queue();
                e.getAuthor().openPrivateChannel().queue((channel) ->
                {
                    EmbedBuilder eb = new EmbedBuilder();

                    channel.sendMessage("Hello " + e.getAuthor().getAsMention() + "! You suggested the following:").queue();
                    channel.sendMessage(embedBuilder.build()).queue();

                });

            }

            else{
                e.getChannel().sendMessage(getRawError("You must provide more arguments! ``!suggest <Plugin> <My Suggestion Here>``").build()).queue();
            }

        }
        else if(args[0].equalsIgnoreCase("+help")){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.CYAN);
            embedBuilder.setTitle("GCNT Help");
            embedBuilder.addField("+suggest <PluginID> <Suggestion Here>", "Submits a suggestion.", false);
            e.getChannel().sendMessage(embedBuilder.build()).queue();


        }




    }
    public static EmbedBuilder getRawError(String message){

        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription(message);
        eb.setColor(Color.RED);
        return eb;

    }

}
