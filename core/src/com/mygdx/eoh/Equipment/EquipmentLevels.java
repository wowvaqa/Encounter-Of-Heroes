package com.mygdx.eoh.Equipment;

import com.badlogic.gdx.utils.SnapshotArray;

import java.util.Random;

/**
 * Contains level 1 and 2 equip.
 * Class to draw equip.
 * Created by wowvaqa on 11.04.17.
 */

class EquipmentLevels {
    private static final EquipmentLevels instance = new EquipmentLevels();

    //All level 1 equipment
    private SnapshotArray<EquipKinds> equipLevel1;
    private SnapshotArray<EquipKinds> equipLevel2;
    private SnapshotArray<EquipKinds> equipLevel3;

    private EquipmentLevels() {
        equipLevel1 = new SnapshotArray<EquipKinds>();
        equipLevel2 = new SnapshotArray<EquipKinds>();
        equipLevel3 = new SnapshotArray<EquipKinds>();

        fillEquipment();
    }

    static EquipmentLevels getInstance() {
        return instance;
    }

    private void fillEquipment() {
        // Level 1 equip
        equipLevel1.add(EquipKinds.WoodenStick);
        equipLevel1.add(EquipKinds.LeatherPants);
        equipLevel1.add(EquipKinds.GoldRing);
        equipLevel1.add(EquipKinds.Sword);

        // Level 2 equip
        equipLevel2.add(EquipKinds.MagicStaff);
        equipLevel2.add(EquipKinds.LeatherArmor);
        equipLevel2.add(EquipKinds.SphereOfSpeed);

        // Level 2 equip
        equipLevel3.add(EquipKinds.GreenStoneStaff);
        equipLevel3.add(EquipKinds.SteelArmor);
    }

    EquipKinds drawLevel1Equip() {
        Random rnd = new Random();
        return equipLevel1.get(rnd.nextInt(equipLevel1.size));
    }

    EquipKinds drawLevel2Equip() {
        Random rnd = new Random();
        return equipLevel2.get(rnd.nextInt(equipLevel2.size));
    }

    EquipKinds drawLevel3Equip() {
        Random rnd = new Random();
        return equipLevel3.get(rnd.nextInt(equipLevel3.size));
    }

}
