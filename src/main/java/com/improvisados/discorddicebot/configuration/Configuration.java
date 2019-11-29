/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.improvisados.discorddicebot.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 *
 * @author Joaquin Martinez <juacom04@gmail.com>
 */
public class Configuration implements Serializable
{
    private String token;
    private String owner;
    private String commandPrefix;
    private InetAddress proxyIp;
    private int proxyPort;
    
    private static Configuration instance;

    public Configuration(String token, String owner, String commandPrefix, String roleName, Color roleColor,InetAddress proxyIp,int proxyPort) {
        this.token = token;
        this.owner = owner;
        this.commandPrefix = commandPrefix;
        this.proxyIp=proxyIp;
        this.proxyPort=proxyPort;
        
    }

    
    
    public Configuration(String token, String owner, String commandPrefix, String rolName, Color color)
    {
        this.token = token;
        this.owner = owner;
        this.commandPrefix = commandPrefix;
    }

    
    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getCommandPrefix()
    {
        return commandPrefix;
    }

    public void setCommandPrefix(String commandPrefix)
    {
        this.commandPrefix = commandPrefix;
    }
    
    public Proxy getProxy() {
     Proxy ret=null;
     
     if(this.proxyIp!=null && this.proxyPort!=0)
     {
         ret=new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyIp, proxyPort));
     }
     
     return ret;
        
    }
    
    public static Configuration getInstance() throws FileNotFoundException
    {
        if(instance==null)
        {
            Gson gson=new GsonBuilder().setPrettyPrinting().create();
            
            instance=gson.fromJson(new FileReader("./settings.json"), Configuration.class);
        }
        
        return instance;
    }
    
}
