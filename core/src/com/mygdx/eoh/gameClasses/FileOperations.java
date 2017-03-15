package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.eoh.mapEditor.MapEditor;
import com.mygdx.eoh.mapEditor.MapFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by v on 2016-10-17.
 */
public class FileOperations {

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }

    public static void saveMap(FileHandle file, MapEditor mapEditor) throws IOException {
        try {
            file.writeBytes(serialize(mapEditor), false);
        } catch (Exception ex) {
            Gdx.app.log("Save Map Error", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void saveMap(FileHandle file, MapFile mapFile) throws IOException {
        try {
            file.writeBytes(serialize(mapFile), false);
        } catch (Exception ex) {
            Gdx.app.log("Save Map Error", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
