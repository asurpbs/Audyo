/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.user;

/**
 *
 * @author Mithila Prabashwara
 */
import com.asurpbs.audyo.Audyo;
import static com.asurpbs.audyo.Audyo.ObjectToSong_Array;
import static com.asurpbs.audyo.Audyo.config;
import com.asurpbs.util.DoublyLinkedList;
import com.asurpbs.util.M3U8PlayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.commons.io.FileUtils;

public class PlayList {

    public String playlist_dir = Audyo.config.playList_dir;
    public String song_dir = Audyo.config.Song_dir;
    public String name;
    public String filePath;
    public String fileName;
    public DoublyLinkedList songs; // To store playlist's songs
    public static String[] supportedFormats = new String[] {"m3u8"};
    
    /**
     *To store all playLists
     */
    public static DoublyLinkedList allList = new DoublyLinkedList();
    
    /**
     * Playlist instance
     * @param name - Playlist name that you wish to create
     */
    public PlayList(String name) {
        this.name = name.strip();
        this.fileName = name.replace(' ', '_');
        this.filePath = playlist_dir + "\\" + name.strip().replace(" ", "_") + ".m3u8";
        this.songs = new DoublyLinkedList();
    }
    
    /**
     * Use to check if the playlist exists or not
     * @param playListName - The playlist's name
     * @return 
     */
    public static boolean isExistPlayList(String playListName) {
        return allList.isExist(getByName(playListName));
    } 
    
    /**
     * Use to create playlist
     * @param name - playlist's name
     */
    public static void create(String name) {
        try {
            if (isExistPlayList(name)) System.out.printf(":) :) Playlist aleardy exists in the playlist. Use another name.\n"); 
            else {
                PlayList newPlayList = new PlayList(name);
                allList.insertToTail(newPlayList);
                M3U8PlayList.create(newPlayList.filePath);
            }
        } catch (Exception ex) {
            Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get back the reference of needed playlist instance from the PlayList.allList
     * @param name - Playlist name
     * @return the reference to the playlist instance in the heap
     */
    public static PlayList getByName(String name) {
        Object[] objLists = PlayList.allList.getObjectArray();
        for (Object obj : objLists) {
            if (obj instanceof PlayList temp) {
                if (temp.name.toLowerCase().equals(name.trim().toLowerCase())) {
                    return temp;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether the playlist is empty or not
     * @return 
     */
    public boolean isEmpty() {
        return this.songs.isEmpty();
    }
    
    /**
     * Check if the song is exist or not?
     * @param songTitle - Song's title
     * @return true when the song is exist
     */
    public boolean isExistSong(String songTitle) {
        return this.songs.isExist(Song.getByName(songTitle));
    }
    
    /**
     * Use to add song to the playlist
     * @param songTitle - Song's title
     */
    public void addSong(String songTitle) {
        try {
            if (this.isExistSong(songTitle)) System.out.printf(":) :) " + songTitle + " aleardy exists in the " + this.name + " playlist\n");
            else {
                Song track = Song.getByName(songTitle);
                this.songs.insertToTail(track);
                new M3U8PlayList(this.filePath).writer(song_dir + "\\" + track.fileName);
                System.out.printf(":) " + songTitle + " is added to the " + this.name + " playlist\n");
            }
        } catch (Exception ex) {
            Logger.getLogger(PlayList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Use to remove a selected song
     * @param songTitle - Song's title
     */
    public void removeSong(String songTitle) {
        try { 
            if (!this.isExistSong(songTitle)) System.out.printf(":( " + songTitle + " not exists in the " + this.name +" playlist.\n");
            else {
                Song track = Song.getByName(songTitle);
                String deletedFilePath = track.fileName;
                new M3U8PlayList(this.filePath).remove(song_dir + "\\" + deletedFilePath); // Remove the deleted playlist from the playlist and updaet it
                System.out.printf(":) " + songTitle + " is deleted from " + this.name + " playlist\n");
                this.songs.delete(track);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * 
     * Delete a playlist
     * 
     * @param playListTitle The playlist that you want to delete
     */
    public static void delete(String playListTitle) {
        try { 
            if (!isExistPlayList(playListTitle)) System.out.println(":( The playlist does not exist.");
            else {
                PlayList temp = getByName(playListTitle);
                new File(temp.filePath).delete();
                allList.delete(temp);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    
    /**
     * Use to view all songs under a playlist
     */
    public void viewAllSongs() {
        if (this.songs.isEmpty()) {
            System.out.println(":( No songs found in this playlist.");
        } else {
            Object[] objLists = this.songs.getObjectArray();
            int i = 1;

            System.out.println("===========================================");
            System.out.printf("         [PLAYLIST] - %s\n", this.name);
            System.out.println("===========================================");

            for (Object obj : objLists) {
                if (obj instanceof Song song) {
                    System.out.printf(" [%d] %s\n", i++, song.title);
                } else {
                    System.out.println(" :( Unexpected data found in the playlist.");
                }
            }

            System.out.println("===========================================");
        }
    }

    /**
     * Use to view all playlists that are created
     */
    public static void viewAllPlayLists() {
        if (allList.isEmpty()) {
            System.out.println("(: No playlists available.");
        } else {
            System.out.println("Your Available Playlists:");
            System.out.println("---------------------------");
            Object[] objLists = allList.getObjectArray();
            for (Object obj : objLists) {
                if (obj instanceof PlayList playList) {
                    System.out.println(" - " + playList.name);
                } else {
                    System.out.println(" :( Unexpected data found.");
                }
            }
        }
    }

    /**
     * Use to delete all songs from a playlist
     * @throws Exception When the list is empty (from DoublyLinkedList)
     */
    public void deleteAllSongs() throws Exception {
        this.songs.deleteAll();
        new M3U8PlayList(this.filePath).removeAll();
        System.out.println("(: All songs have been successfully removed from the playlist.");
    }

    /**
     * Use to delete all playlists
     */
    public static void deleteAllPlayList() {
        try {
            allList.deleteAll();
            FileUtils.cleanDirectory(new File(Audyo.config.playList_dir));
            System.out.println("(: All playlists have been successfully deleted.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * Play all songs in the playlist from head to tail
     *
     */
    public void playFromBegining() {
        try {
            Song[] song = ObjectToSong_Array(getByName(this.name).songs.getObjectArray());
            int len = song.length;
            for (int i = 0; i < len; i++) {
                String nextSong = (i < len - 1) ? song[i + 1].title : "End of the playlist";
                System.out.printf("""
 
                                  [%d of %d] (Now) - %s >>    %s
                                  
                                  """, i, len, song[i].title, nextSong);
                FileInputStream fileInputStream = new FileInputStream(config.Song_dir + File.separator + song[i].fileName);
                Player player = new Player(fileInputStream);
                player.play();
            }
        } catch (FileNotFoundException | JavaLayerException ex) {
                System.out.println(ex.getMessage());
        }
    }

    /**
     * Play all songs in the playlist from tail to head
     *
     */
    public void playFromEnd() {
        try {
            Song[] song = ObjectToSong_Array(getByName(this.name).songs.getObjectArray());
            int len = song.length;
            for (int i = len - 1; i >= 0; i--) {
                String nextSong = (i > 0) ? song[i - 1].title : "End of the playlis";
                System.out.printf("""

                                  [%d of %d] (Now) - %s >>    %s

                                  """, len - i, len, song[i].title, nextSong);
                FileInputStream fileInputStream = new FileInputStream(config.Song_dir + File.separator + song[i].fileName);
                Player player = new Player(fileInputStream);
                player.play();
            }
        } catch (FileNotFoundException | JavaLayerException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
