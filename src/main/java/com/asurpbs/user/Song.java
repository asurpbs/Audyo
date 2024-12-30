/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.user;

/**
 *
 * @author Mithila Prabashwara
 */

import com.asurpbs.util.DoublyLinkedList;

public class Song {
    public String title;;
    public String artist;
    public String duration;
    public String fileName;
    public static int count = 0; //The count of the all files in the folder
    public static String[] supportedFormats = {"mp3", "flac", "opus", "wav", "aac"};
    /**
     *To store all songs
     */
    public static DoublyLinkedList allList = new DoublyLinkedList();
    
    /**
     * 
     * @param title - song's title in title case (title case is not necessary)
     * @param artist - artist's name in title case (title case is not necessary)
     * @param timeSpan - the song's duration as seconds
     * @param fileName - file's name
     */
    public Song(String title, String artist, int timeSpan, String fileName) {
        this.title = title.strip();
        this.artist = artist.strip();
        this.duration = formatDuration(timeSpan);
        this.fileName = fileName.strip();
        count++;
    }
    
    //get song's metadata as a array
    public String[] getMetaData() {
        return new String[] {this.title, this.artist, this.duration};
    }
    
    //format duration as mm:ss ~ ISO 8601 format
    private String formatDuration(int seconds) {
        return String.format("%02d:%02d",seconds / 60, seconds % 60);
    }
    
    public static void viewAllSongs() {
        Object[] objLists = allList.getObjectArray();
        for (Object obj: objLists) {
            if (obj instanceof Song songs) {
                System.out.println(songs.title);
            } else {
                System.out.println("Invalid object in playlist: " + obj);
            }
        }
    }
    
}
