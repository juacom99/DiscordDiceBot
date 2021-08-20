/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.discorddicebot.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.improvisados.jdiceroller.JDiceRoller;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author joaquin
 */
@CommandInfo(
        name= {"roll"},
        description = "Roll a dice"
)
public class RollCommand extends Command {

    private JDiceRoller dice;
    private Gson gson;

    public RollCommand() {
        dice = new JDiceRoller();
        gson = new GsonBuilder().setPrettyPrinting().create();
        this.name = "roll";
        this.aliases=new String[]{"r"};
        this.help = "Roll a Dice";
        this.guildOnly = false;
        super.cooldown=5;
        super.arguments="\\d+[dD]\\d+([+-](\\d+[dD]\\d+|\\d+))*";
    }

    protected void execute(CommandEvent event) {

        User auth=event.getAuthor();
        String[] params = event.getArgs().split(" ");
        if (params != null && params.length > 0) {
            String result = dice.roll(params[0]);
            EmbedBuilder embedBuilder = new EmbedBuilder();
            JsonObject res = gson.fromJson(result, JsonObject.class);
            if (res.get("success").getAsBoolean()) {
                embedBuilder.setAuthor(auth.getName());
                embedBuilder.setTitle("Roll ***" + res.get("expression").getAsString() + "***");
                embedBuilder.setDescription(res.get("resultText").getAsString() + "=" + res.get("total").getAsString());
                embedBuilder.setColor(Color.green);
                embedBuilder.setThumbnail("https://d20dames.com/favicon.ico");
            } else {
                embedBuilder.setAuthor(auth.getName());
                embedBuilder.setTitle("Roll ***" + params[0] + "***");
                embedBuilder.setDescription("Error: " + res.get("errorMsg").getAsString());
                embedBuilder.setColor(Color.red);
                embedBuilder.setThumbnail("https://d20dames.com/favicon.ico");
            }            
            event.reply(embedBuilder.build());
            embedBuilder.clear();
        }

    }
}
