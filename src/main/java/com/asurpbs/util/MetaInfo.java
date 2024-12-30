/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.util;

/**
 *
 * @author Mithila Prabashwara
 */

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.AudioHeader;

public class MetaInfo {
    
    /**
     * Get meta data of the song
     * @param file - Song
     * @return as String array - (Title, Artist, Duration, Song's file name with extension)
     */
    public static String[] getAll(File file) {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        try {
            AudioFile track = AudioFileIO.read(file);
            Tag tag = track.getTag();
            AudioHeader audioHeader = track.getAudioHeader();
            return new String[] {tag.getFirst(FieldKey.TITLE), tag.getFirst(FieldKey.ARTIST), "" + audioHeader.getTrackLength(), file.getName()};
        } catch (IOException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | TagException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static String title(String trackPath) {
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        try {
            AudioFile track;
            track = AudioFileIO.read(new File(trackPath));
            return track.getTag().getFirst(FieldKey.TITLE);
        } catch (IOException | CannotReadException | InvalidAudioFrameException | ReadOnlyFileException | TagException e) {
                System.out.println(e.getMessage());
        }
        return null;
    }
}
