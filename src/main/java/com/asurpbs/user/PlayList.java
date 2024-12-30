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
import com.asurpbs.util.DoublyLinkedList;
import com.asurpbs.util.M3U8PlayList;
import java.io.IOException;

public class PlayList {
    public String name;
    public String filePath;
    public String fileName;
    public static int count; // Palylist count
    public DoublyLinkedList songs; // To store playlist's songs
    public static String[] supportedFormats = new String[] {"m3u8"};
    /**
     *To store all playLists
     */
    public static DoublyLinkedList allList = new DoublyLinkedList();
    
    public PlayList(String name) throws IOException {
        this.name = name.strip();
        this.fileName = name.replace(' ', '_');
        this.filePath = Audyo.config.playList_dir + "\\" + name.strip().replace(" ", "_") + ".m3u8";
        count++;
        this.songs = new DoublyLinkedList();
    }
    
    public static void create(String name) {
        try {
            PlayList newPlayList = new PlayList(name);
            allList.insertToTail(newPlayList);
            M3U8PlayList.create(newPlayList.filePath);
        } catch (IOException e) {
            System.out.println("Failed to create playlist: " + e.getMessage());
        }
    }
    
    public void addSong(Song track) {
        this.songs.insertToTail(track);
        M3U8PlayList file = new M3U8PlayList(this.filePath);
        file.writer(track.fileName);
    }
    
    public void removeSong() throws Exception {
        this.songs.deleteFromLast();
    }
    
    public void removeSong(Song track) throws Exception {
        this.songs.delete(track);
    }
    
    public boolean isEmpty() {
        return this.songs.isEmpty();
    }
    /**
     * 
     * @throws Exception When the list is empty (from DoublyLinkedList)
     */
    public void deleteAllSongs() throws Exception {
        this.songs.deleteAll();
    }
    
    /**
     * This method only delete the all songs of the playlist and the LinkedList that 
     * contained playlist's song contained. Delete the playlist where you wanna delete
     * that object.
     * @throws Exception When the list is empty (from DoublyLinkedList)
     */
    public void delete() throws Exception{
        this.deleteAllSongs();
        this.songs = null;
        allList.delete(this);
        count--;
    }
    
    public void viewAllSongs() {
        if (this.songs.isEmpty()) System.out.println("The list is empty");
        else {
            Object[] objLists = this.songs.getObjectArray();
            for (Object obj: objLists) {
                if (obj instanceof Song songs) {
                    System.out.println(songs.title);
                } else {
                    System.out.println("Invalid object in playlist: " + obj);
                }
            }
        }
    }
    
    public static void viewAllPlayLists() {
        if (allList.isEmpty()) System.out.println("The list is empty");
        else {
            Object[] objLists = allList.getObjectArray();
            for (Object obj: objLists) {
                if (obj instanceof PlayList playList) {
                    System.out.println(playList.name);
                } else {
                    System.out.println("Invalid object in playlist: " + obj);
                }
            }
        }
    }
}
