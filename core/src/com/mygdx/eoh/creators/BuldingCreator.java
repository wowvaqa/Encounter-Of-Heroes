package com.mygdx.eoh.creators;

import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.Buldings;
import com.mygdx.eoh.gameClasses.Bulding;
import com.mygdx.eoh.gameClasses.Options;

/**
 * Creates buldings
 * Created by v on 2016-10-19.
 */
public class BuldingCreator {
    private static BuldingCreator instance = new BuldingCreator();

    private BuldingCreator() {
    }

    public static BuldingCreator getInstance() {
        return instance;
    }

    public Bulding createBulding(Buldings typeOfBulding, int mapColumn, int mapRow) {
        switch (typeOfBulding) {
            case Hospital:
                return createHospital(mapColumn * Options.tileSize, mapRow * Options.tileSize);
        }
        return createHospital(mapColumn, mapRow);
    }

    private Bulding createHospital(int mapColumn, int mapRow) {
        Bulding bulding = new Bulding(AnimationTypes.HospitalAnimation);
        bulding.setPosition(mapColumn, mapRow);
        bulding.setSize(Options.tileSize, Options.tileSize);
        return bulding;
    }
}
