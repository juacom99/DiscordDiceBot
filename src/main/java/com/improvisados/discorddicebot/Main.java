package com.improvisados.discorddicebot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.improvisados.discorddicebot.configuration.Configuration;
import java.io.FileNotFoundException;
import javax.security.auth.login.LoginException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author joaquin
 */
public class Main {

    
     private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LOGGER.info("Initializing DiceBot");
        try
        {
        Configuration cfg = Configuration.getInstance();
        if (cfg.getProxy() == null) {
            DiscordDiceBot bot = new DiscordDiceBot(cfg.getToken());
        } else {
            DiscordDiceBot bot = new DiscordDiceBot(cfg.getToken(), cfg.getProxy());
        }
        }
        catch(FileNotFoundException ex)
        {
            LOGGER.error("Config File settings.json not found");
        } catch (InterruptedException ex) {
            
        } catch (LoginException ex) {
             LOGGER.error("The bot was unable to Login");
        }

    }

}
