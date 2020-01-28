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
import java.util.Arrays;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author jomartinez
 */
@CommandInfo(
        name = {"roll"},
        description = "Roll a dice"
)
public class FateRollCommand extends Command {

    private JDiceRoller dice;
    private Gson gson;

    public FateRollCommand() {
        dice = new JDiceRoller();
        gson = new GsonBuilder().setPrettyPrinting().create();
        this.name = "fateroll";
        this.help = "Roll a Fudge Dice";
        this.guildOnly = false;
        super.cooldown = 20;
        super.arguments = "\\d*";
    }

    @Override
    protected void execute(CommandEvent event) {
        User auth = event.getAuthor();
        int total = 0;
        String out="";
        EmbedBuilder embedBuilder = new EmbedBuilder();
Gson gson = new GsonBuilder().create();
        int[] result = dice.roll(4, 6);
        
        
        out+=gson.toJson(result).replaceAll("[1|2]", " - ").replaceAll("[3|4]", "  ").replaceAll("[5|6]", " + ");
        
        if (event.getArgs() != null && ! event.getArgs().trim().equals("")) {
            out+=event.getArgs();
            total+=Integer.parseInt(event.getArgs());
        }        

        for (int val : result) {
            total += Math.floor((val - 1) / 2) - 1;
        }

        out+=" = "+total;
     
        //embedBuilder.setAuthor(auth.getName());
        embedBuilder.setTitle("Fate Roll for ***"+auth.getName()+ "***");
        embedBuilder.setDescription(out);
        embedBuilder.setColor(Color.green);
        //embedBuilder.setThumbnail("https://d20dames.com/favicon.ico");
        event.reply(embedBuilder.build());
        embedBuilder.clear();

    }
}
