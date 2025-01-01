/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.asurpbs.audyo;

/**
 *
 * @author Mithila Prabashwara
 */

import com.asurpbs.user.Song;
import com.asurpbs.user.PlayList;
import com.asurpbs.user.Settings;
import com.asurpbs.util.MetaInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

public class Audyo {
    // make global static instant of settings to manage application configuration settings.
    public static Settings config;
    
    /**
     * Normal startup process of the program
     */
    
    public static void startup() {
        fetchUserConfiguration();
        fetchDirectories();
    }
    
    /**
     * Use to load or refresh settings when the settings have been changed
     */
    public static void fetchUserConfiguration() {
        config = new Settings(); // instantiation of config
    }
    
    /**
     * Scan all music and playlist file directories
     */
    public static void fetchDirectories() {
            scanMusicDir(config.Song_dir);
            scanPlaylistDir(config.playList_dir);
    }
    
    /**
     * Custom directory scan ~ use to scan the music folder to detect songs that are added when the program is using
     * (Not impelimented this in the program yet)
     * @param choice - S or P | Stands for Song and P stands for playlist
     */
    public static void fetchDirectories(char choice) {
        try {
            switch (choice) {
                case 's' | 'S' -> scanMusicDir(config.Song_dir);
                case 'p' | 'P' -> scanPlaylistDir(config.playList_dir);
                default -> System.out.println("(: Invalid input. Try again.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * Check whether input file have correct file type or not
     * @param file - file that you want to check
     * @param extensions - String array that included supported file types without full stop
     * @return True when file's extension in supported extensions
     */
    public static boolean isValidFile(File file, String[] extensions) {
        if (file.isFile()) for (String ext : extensions) {
            if (FilenameUtils.getExtension(file.getPath()).equals(ext)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check all song's in the given directory and added to the allSong linkedList
     * @param directory - Music folder's path
     */
    public static void scanMusicDir(String directory) {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (isValidFile(file, Song.supportedFormats)) {
                String[] tags = MetaInfo.getAll(file);
                Song.allList.insertToTail(new Song(tags[0], tags[1], Integer.parseInt(tags[2]), tags[3]));
            }
        }
    }
    
    
    
    /**
     * Get the playlist name from playlist file's name
     * eg- My_Favourite.m3u8 --> My Favourite
     * 
     * @param file - Play list file (m3u8)
     * @return the playlist's name
     */
    public static String getFormattedPlayListName (File file) {
        return FilenameUtils.removeExtension(file.getName()).replace("_", " ");
    }
    
    /**
     * Check all playlist file's in the given directory
     * @param directory - Music folder's path
     */
    public static void scanPlaylistDir(String directory) {
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (isValidFile(file, PlayList.supportedFormats)) {
                // Read playlist file and added to PlayList.allList linkedList
                PlayList.allList.insertToTail(new PlayList(getFormattedPlayListName(file)));
                settingUpPlayListSong(file);
            }
        }
    }
    
    /**
     * Read the playlist files and added the songs of the playlist file to relevant instance of playlist
     * @param playListFile - m3u8 playlist file
     */
    public static void settingUpPlayListSong(File playListFile) {
        PlayList temp = PlayList.getByName(getFormattedPlayListName(playListFile));
        Scanner reader;
        try {
            reader = new Scanner(playListFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine().strip();
                if (data.isEmpty() || data.charAt(0) == '#') {}
            else {
                Song trackSong = Song.getByName(MetaInfo.title(data));
                if (trackSong != null) {
                    temp.songs.insertToTail(trackSong);
                }
            }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Audyo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * COnvert array of objects to songs
     * @param objList - object typed array
     * @return array of songs
     */
    public static Song[] ObjectToSong_Array(Object[] objList) {
        Song[] songs = new Song[objList.length];
        int i = 0;
        for (Object obj : objList) {
            songs[i++] = (Song) obj;
        }
        return songs;
    }
    
    /**
     * Ask a question and get an integer answer
     * @param prompt Question that you want to ask from the user
     * @return True when user said yes otherwise, false.
     */
    public static boolean inputBool(String prompt) {
        Scanner getInput = new Scanner(System.in);
        boolean True = true;
        while (True) {
            System.out.println(prompt);
            String input = getInput.next();
            switch (input.strip()) {
                case "y" , "Y" : return true;
                case "n" , "N": return false;
                default : {
                    System.out.println("Invalid input. Try again");
                }
            }
        }
        return false;
    }
    
    public static void main(String... args) {
        startup();
        System.out.println("\n\n");
        Song.viewAllSongs();
        System.out.println("\n\n");
        PlayList.create("Classic Hits");
        PlayList newPlayList = PlayList.getByName("Classic Hits");
        System.out.println("\n\n");
        String[] titles = {"Hotel California", "Imagine", "Smells Like Teen Spirit", "Bohemian Rhapsody", "Hey Jude"};
        for (String title : titles) newPlayList.addSong(title);
        System.out.println("\n\n");
        newPlayList.viewAllSongs();
        System.out.println("\n\n");
        if (inputBool("Do you wannt to play the songs in new playlist in ascending order? [Y/n]")) newPlayList.playFromBegining();
        else if (inputBool("Do you wannt to play the songs in new playlist in descending order? [Y/n]")) newPlayList.playFromEnd();
        else if (inputBool("Do you wannt to delete Hey Jude & Shape Of You songs? [Y/n]")) { 
            newPlayList.removeSong("Hey Jude");
            newPlayList.removeSong("Shape Of You");
        }
    }
}
