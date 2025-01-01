/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Mithila Prabashwara
 */
public class M3U8PlayList {
    public String playlist;
    
    public M3U8PlayList(String playlistPath) {
        this.playlist = playlistPath;
    }
    
    /**
     * add song's path to the playlist file
     * @param songPath - Path to the music file
     */
    public void writer(String songPath) {
        File playlistFile = new File(this.playlist);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playlistFile, true))) {
            if (!playlistFile.exists() || playlistFile.length() == 0) {
                writer.write("#EXTM3U");
                writer.newLine();
            }
            writer.write("#EXT-X-RATING:0");
            writer.newLine();
            writer.write(songPath);
            writer.newLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Use create to playlist file (.m3u8)
     * @param filePath - The playlist name that you whish to create
     */
    public static void create(String filePath)  {
        try {
            if (Files.exists(Paths.get(filePath))) {
                System.out.println("The PlayList is already created.");
            } else {
                new File(filePath).createNewFile();
                System.out.println("The PlayList is created successfully.");
            }
        } catch (IOException ex) {
                System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Use to update the playlist file when a song is deleted from the playlist.
     * ~ Remove the song's path with #EXT-X-RATING
     * 
     * @param songPath - Path to the song file
     */
    public void remove(String songPath) {
        File playlistFile = new File(this.playlist);
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(playlistFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals(songPath)) {
                if (i > 0 && lines.get(i - 1).startsWith("#EXT-X-RATING")) {
                    lines.remove(i - 1);
                    i--;
                }
                lines.remove(i);
                i--;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.playlist))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Use to delete all playlist information
     */
    public void removeAll() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.playlist))) {
            writer.write("");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
