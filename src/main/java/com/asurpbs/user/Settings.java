/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.user;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Mithila Prabashwara
 */
public class Settings {
    public static final String PROGRAM_NAME = "audyo";
    public static final String CONFIG_FILE_NAME = PROGRAM_NAME + ".properties";
    //public static final String CONFIG_DIR = System.getProperty("user.home") + File.separator + "." + PROGRAM_NAME;
    public static final String CONFIG_DIR = System.getProperty("user.home") + "\\Desktop\\Music\\." + PROGRAM_NAME;
    
    public static final String KEY_SONG_DIR = "Song_dir";
    public static final String KEY_PLAYLIST_DIR = "playList_dir";
    
    //public String playList_dir;
    //public String Song_dir;;
    /**
     * Use these values for unit testing
     */
    public String playList_dir = System.getProperty("user.home") + "\\Desktop\\Music\\PlayList";
    public String Song_dir = System.getProperty("user.home") + "\\Desktop\\Music";
    
    private final Path configFilePath; // The user configuration file's path
    
    public Settings() {
        configFilePath = Paths.get(CONFIG_DIR, CONFIG_FILE_NAME);
    }
    
    /**
     * default flow runs when settings instance, instantiation in the Audyo ~ used in main()
     * @throws IOException 
     */
    public void initialize() throws IOException {
        if (Files.notExists(configFilePath)) {
            createDefaultConfig();
            updateConfig();
        } else {
            loadConfig();
        }
    }
    
    /**
     * Create the default configuration file when the config path is no exist in the configuration file's path
     * @throws IOException 
     */
    public void createDefaultConfig() throws IOException {
        Files.createDirectories(Paths.get(CONFIG_DIR));
        Properties defaultProperties = new Properties();
        for (String key : new String[] {KEY_SONG_DIR, KEY_PLAYLIST_DIR}) defaultProperties.setProperty(key, "");
        defaultProperties.store(new FileWriter(CONFIG_DIR + File.separator + CONFIG_FILE_NAME), getTime().concat(" ~ Audyo Player's user configurations"));
        System.out.println("Configuration file is initialized successfully in " + CONFIG_DIR);
    }
    
    /**
     * Load the configuration settings to the program when startup
     * @throws IOException 
     */
    public void loadConfig() throws IOException {
        try (FileReader reader = new FileReader(configFilePath.toFile())) {
            Properties properties = new Properties();
            properties.load(reader);
            this.Song_dir = properties.getProperty(KEY_SONG_DIR);
            this.playList_dir = properties.getProperty(KEY_PLAYLIST_DIR);
            System.out.println("Configurations loaded successfully.");
            reader.close();
        }
    }
    
    /**
     * Use to update the key value and write the updated settings to the file
     * @param key KEY_SONG_DIR or KEY_PLAYLIST_DIR
     * @param value relevant value 
     * @throws IOException 
     */
    public void updateConfig(String key, String value) throws IOException {
        if (Files.notExists(configFilePath)) createDefaultConfig();
        FileReader reader = new FileReader(configFilePath.toFile());
        Properties update = new Properties();
        update.load(reader);
        update.setProperty(key, value);
        update.store(new FileWriter(configFilePath.toFile()), getTime().concat(" ~ Audyo Player's user configurations"));
        System.out.println("Updated properties file successfully!");
        reader.close();
    }
    
    /**
     * Use to loading the configuration files form the file when startup - For testing
     * @throws IOException 
     */
    public void updateConfig() throws IOException {
        if (Files.notExists(configFilePath)) createDefaultConfig();
        FileReader reader = new FileReader(configFilePath.toFile());
        Properties update = new Properties();
        update.load(reader);
        update.setProperty(KEY_SONG_DIR, this.Song_dir);
        update.setProperty(KEY_PLAYLIST_DIR, this.playList_dir);
        update.store(new FileWriter(configFilePath.toFile()), getTime().concat(" ~ Audyo Player's user configurations"));
        System.out.println("Updated properties file successfully!");
        reader.close();
    }
    
    /**
     * Use to get the time and date
     * @return the date and time in dd : mm : yyyy HH : mm : ss format
     */
    public static String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    
    public void main(String... args) throws IOException {
        initialize();
    }
}

