package com.mygdx.eoh.gameClasses;

import com.mygdx.eoh.enums.Terrains;
import com.mygdx.eoh.mapEditor.MapEditor;
import com.mygdx.eoh.mapEditor.MapFile;

import static com.mygdx.eoh.mapEditor.MapEditor.*;

/**
 * Representation of map.
 * Created by v on 2016-10-12.
 */
public class Map {
    private Field[][] fields;
    private int fieldsColumns;
    private int fieldsRows;

    public static String getTextureRegionName(int x, int y, Object obj, Terrains tT) {

        MapEditor map = new MapEditor();

        if (obj.getClass().equals(MapFile.class)){
            map.fields = map.createFields( ((MapFile)obj).mapColumns, ((MapFile)obj).mapRows);
            map = convertMapFileToMapEditor(map, (MapFile) obj);
        }

        if (obj.getClass().equals(MapEditor.class)){
            map = (MapEditor) obj;
        }

        String terrainName = "grass";

        switch (tT) {
            case River:
                terrainName = "river";
                break;
            case Mountain:
                terrainName = "mountain";
                break;
            case Forest:
                terrainName = "forest";
                break;
        }

        if (isNorthAvailable(y, map.mapRows) && !tT.equals(Terrains.Grass)) {
            if (map.fields[x][y + 1].terrains.equals(tT)) {
                terrainName += "N";
            }
        }
        if (isSouthAvailable(y) && !tT.equals(Terrains.Grass)) {
            if (map.fields[x][y - 1].terrains.equals(tT)) {
                terrainName += "S";
            }
        }
        if (isWestAvailable(x) && !tT.equals(Terrains.Grass)) {
            if (map.fields[x - 1][y].terrains.equals(tT)) {
                terrainName += "W";
            }
        }
        if (isEastAvailable(x, map.mapColumns) && !tT.equals(Terrains.Grass)) {
            if (map.fields[x + 1][y].terrains.equals(tT)) {
                terrainName += "E";
            }
        }
        return terrainName;
    }

    private static boolean isNorthAvailable(int y, int mapRows) {
        return y != mapRows - 1;
    }

    private static boolean isSouthAvailable(int y) {
        return y != 0;
    }

    private static boolean isEastAvailable(int x, int mapColumns) {
        return x != mapColumns - 1;
    }

    private static boolean isWestAvailable(int x) {
        return x != 0;
    }

    /**
     * Getting array of fields
     *
     * @return Array object with fields
     */
    public Field[][] getFields() {
        return fields;
    }

    /**
     * Getting how many colums of fields has map.
     *
     * @return Number of columns.
     */
    public int getFieldsColumns() {
        return fieldsColumns;
    }

    /**
     * Setting amount of columns.
     *
     * @param fieldsColumns
     */
    public void setFieldsColumns(int fieldsColumns) {
        this.fieldsColumns = fieldsColumns;
    }

    /**
     * Getting how many rows of fields has map.
     *
     * @return Number of rows.
     */
    public int getFieldsRows() {
        return fieldsRows;
    }

    /**
     * Setting amount of rows.
     *
     * @param fieldsRows
     */
    public void setFieldsRows(int fieldsRows) {
        this.fieldsRows = fieldsRows;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }
}
