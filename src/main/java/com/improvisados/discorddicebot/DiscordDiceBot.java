package com.improvisados.discorddicebot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.improvisados.discorddicebot.commands.RollCommand;
import com.improvisados.discorddicebot.configuration.Configuration;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import java.io.FileNotFoundException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

/**
 *
 * @author joaquin
 */
public class DiscordDiceBot extends ListenerAdapter {

    private JDA jda;
    private boolean respondeToBots;
    
   // private static final Logger LOGGER = LogManager.getLogger(DiscordDiceBot.class.getName());

    public DiscordDiceBot(String token) throws InterruptedException, LoginException {
        this.jda = new JDABuilder(AccountType.BOT).setToken(token).build();
        respondeToBots = false;
        this.jda.addEventListener(this);
    }

    public DiscordDiceBot(String token, Proxy proxy) throws InterruptedException, LoginException {

        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(proxy).proxyAuthenticator(Authenticator.NONE);

        OkHttpClient cli = builder.proxy(proxy).proxyAuthenticator(Authenticator.NONE).connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        respondeToBots = false;
        this.jda = new JDABuilder(AccountType.BOT).setHttpClientBuilder(builder).setHttpClient(cli).setToken(token).build();
        

    }
    
    
    
    @Override
    public void onReady(ReadyEvent event)
    {
         String owner="368791176796700672";
         String prefix="!";
        try
        {
            Configuration cfg=Configuration.getInstance();
            owner=cfg.getOwner();
            prefix=cfg.getCommandPrefix();
        } catch (FileNotFoundException ex)
        {
            
        }
        
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix(prefix);
        builder.setOwnerId(owner);
        builder.addCommands(new RollCommand());
        

        CommandClient clientCommand = builder.build();
        
        jda.addEventListener(clientCommand);
        
       // LOGGER.info("Ready to Listen");
    }






}
