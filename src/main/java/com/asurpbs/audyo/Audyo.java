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
import java.io.IOException;
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
        try {
            scanMusicDir(config.Song_dir);
            scanPlaylistDir(config.playList_dir);
        } catch (IOException ex) {
            Logger.getLogger(Audyo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Audyo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Custom directory scan ~ use to scan the music folder to detect songs that are added when the program is using
     * (Not impelemented this in the program yet)
     * @param choice - S or P | Stands for Song and P stands for playlist
     */
    public static void fetchDirectories(char choice) {
        try {
            if (choice == 's' || choice == 'S') scanMusicDir(config.Song_dir);
            else if (choice == 'p' || choice == 'P') {scanPlaylistDir(config.playList_dir);}
            else {System.out.println("Invalid input. Try again");}
        } catch (IOException ex) {
            Logger.getLogger(Audyo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Audyo.class.getName()).log(Level.SEVERE, null, ex);
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
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (isValidFile(file, Song.supportedFormats)) {
                String[] tags = MetaInfo.getAll(file);
                Song.allList.insertToTail(new Song(tags[0], tags[1], Integer.parseInt(tags[2]), tags[3]));
            }
        }
    }
    
    
    /**
     * Get back the reference of needed song instance from the Song.allList
     * @param name - song name
     * @return the reference to the song instance in the heap
     */
    public static Song getSongByName(String name) {
        Object[] objLists = Song.allList.getObjectArray();
        for (Object obj: objLists) {
            if (obj instanceof Song temp) {
                if (temp.title.toLowerCase().equals(name.trim().toLowerCase())) return temp;
            } 
        } return null;
    }
    
    /**
     * Get the playlist name from playlist file's name
     * eg- My_Favourite.m3u8 --> My Favourite
     * 
     * @param file - Play list file (m3u8)
     * @return the playlist's name
     */
    public static String getPlayListName (File file) {
        return FilenameUtils.removeExtension(file.getName()).replace("_", " ");
    }
    
    /**
     * Check all playlist file's in the given directory
     * @param directory - Music folder's path
     * @throws java.lang.Exception
     */
    public static void scanPlaylistDir(String directory) throws Exception {
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (isValidFile(file, PlayList.supportedFormats)) {
                // Read playlist file and added to PlayList.allList linkedList
                PlayList.allList.insertToTail(new PlayList(getPlayListName(file)));
                settingUpPlayListSong(file);
            }
        }
    }
    
    /**
     * Read the playlist files and added the songs of the playlist file to relevant instance of playlist
     * @param playListFile - m3u8 playlist file
     */
    public static void settingUpPlayListSong(File playListFile) {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        PlayList temp = getPlaylistByName(getPlayListName(playListFile));
        Scanner reader;
        try {
            reader = new Scanner(playListFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine().strip();
                if (data.isEmpty() || data.charAt(0) == '#') {}
            else {
                    Song trackSong = getSongByName(MetaInfo.title(data));
                if (trackSong != null) {
                    temp.addSong(trackSong);
                }
            }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Audyo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Get back the reference of needed playlist instance from the PlayList.allList
     * @param name - Playlist name
     * @return the reference to the playlist instance in the heap
     */
    public static PlayList getPlaylistByName(String name) {
        Object[] objLists = PlayList.allList.getObjectArray();
        for (Object obj: objLists) {
            if (obj instanceof PlayList temp) {
                if (temp.name.toLowerCase().equals(name.trim().toLowerCase())) return temp;
            } 
        } return null;
    }
    
    /**
     * Use this for unit testing purpose
     */
    private static void unitTest() {
        try {
            PlayList.create("Hello");
            PlayList.viewAllPlayLists();
            PlayList playlist = getPlaylistByName("Hello");
            playlist.addSong(getSongByName("Hotel California (2013 Remaster)"));
            playlist.addSong(getSongByName("Smells Like Teen Spirit"));
            System.out.println("-------Check one playlist's songs----------");
            playlist.viewAllSongs();
            System.out.println("\n");
            System.out.println(playlist.songs.getNoOfElements());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main(String... args) throws Exception {
        startup();
        unitTest();
    }
}
