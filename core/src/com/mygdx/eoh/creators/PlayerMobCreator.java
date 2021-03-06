package com.mygdx.eoh.creators;

import com.mygdx.eoh.Equipment.Equip;
import com.mygdx.eoh.Equipment.EquipKinds;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.gameClasses.CastleMob;
import com.mygdx.eoh.gameClasses.ExpManager;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Map;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * Creates player
 * Created by v on 2016-11-24.
 */
public class PlayerMobCreator {
    private static PlayerMobCreator instance = new PlayerMobCreator();

    private PlayerMobCreator() {
    }

    public static PlayerMobCreator getInstance() {
        return instance;
    }

    public PlayerMob createPlayerMob(PlayerMobClasses playerMobClass, int numberOfPlayerOwner, int locX, int locY, Field field) {

        PlayerMob playerMob = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
                AnimationTypes.KnightStanding), true, GameStatus.getInstance().getPlayers().get(numberOfPlayerOwner), playerMobClass);
                //AnimationTypes.WizardStanding), true, map, GameStatus.getInstance().getPlayers().get(numberOfPlayerOwner));

        playerMob.setCoordinateXonMap(locX);
        playerMob.setCoordinateYonMap(locY);
        playerMob.setLevel(1);
        playerMob.setActualExp(0);
        playerMob.setRewardExp(100);
        playerMob.setLevelingArray(new int[100]);

        ExpManager.fillLevelingArray(playerMob.getLevelingArray());

//        for (int i = 1; i < 99; i++){
//            playerMob.getLevelingArray()[i] = playerMob.getLevelingArray()[i - 1] + 50;
//        }

        if (playerMobClass.equals(PlayerMobClasses.Knight))
            createStatisticForKnight(playerMob);
        else if (playerMobClass.equals(PlayerMobClasses.Wizard))
            createStatisticForWizard(playerMob);


        playerMob.setSize(Options.tileSize, Options.tileSize);
        playerMob.setPosition(
                playerMob.getCoordinateXonMap() * Options.tileSize,
                playerMob.getCoordinateYonMap() * Options.tileSize);
//        playerMob.getPlayerColorImage().setPosition(
//                playerMob.getCoordinateXonMap() * Options.tileSize,
//                playerMob.getCoordinateYonMap() * Options.tileSize);
        field.setPlayerMob(playerMob);
        GameStatus.getInstance().getPlayers().get(numberOfPlayerOwner).getPlayerMobs().add(playerMob);

        playerMob.setArmor(Equip.createEquip(EquipKinds.None));
        playerMob.setWeapon(Equip.createEquip(EquipKinds.None));
        playerMob.setArtifact(Equip.createEquip(EquipKinds.None));

//        playerMob.getEquip().add(Equip.createEquip(EquipKinds.WoodenStick));
//        playerMob.getEquip().add(Equip.createEquip(EquipKinds.LeatherPants));
//        playerMob.getEquip().add(Equip.createEquip(EquipKinds.GoldRing));
//        playerMob.getEquip().add(Equip.createEquip(EquipKinds.MagicStaff));
//        playerMob.getEquip().add(Equip.createEquip(EquipKinds.LeatherArmor));
//        playerMob.getEquip().add(Equip.createEquip(EquipKinds.SphereOfSpeed));

        return playerMob;
    }

    /**
     * Creates statistic for Knight
     *
     * @param playerMob
     */
    private void createStatisticForKnight(PlayerMob playerMob) {
        playerMob.setLevel(1);
        playerMob.setSpeed(5);
        playerMob.setActualSpeed(5);
        playerMob.setAttack(5);
        playerMob.setActualAttack(5);
        playerMob.setDefence(5);
        playerMob.setActualDefence(5);
        playerMob.setActualhp(10);
        playerMob.setActualPower(2);
        playerMob.setPower(2);
        playerMob.setActualWisdom(2);
        playerMob.setWisdom(2);
        playerMob.setMaxHp(10);
        playerMob.setMaxMana(2);
        playerMob.setActualMana(2);
        playerMob.setActionPoints(playerMob.getActualSpeed());
    }

    /**
     * Creates statistic for Wizard
     *
     * @param playerMob
     */
    private void createStatisticForWizard(PlayerMob playerMob) {
        playerMob.setSpeed(5);
        playerMob.setActualSpeed(5);
        playerMob.setAttack(4);
        playerMob.setActualAttack(4);
        playerMob.setDefence(4);
        playerMob.setActualDefence(4);
        playerMob.setActualhp(10);
        playerMob.setActualPower(3);
        playerMob.setPower(3);
        playerMob.setActualWisdom(3);
        playerMob.setWisdom(3);
        playerMob.setMaxHp(10);
        playerMob.setMaxMana(3);
        playerMob.setActualMana(3);
        playerMob.setActionPoints(playerMob.getActualSpeed());
    }

    /**
     *
     * @param map
     * @param numberOfPlayerOwner
     * @param locX
     * @param locY
     * @param field
     * @return
     */
    public CastleMob createCastleMob(Map map, int numberOfPlayerOwner, int locX, int locY, Field field){

        CastleMob castleMob = new CastleMob(AnimationCreator.getInstance().makeAnimation(AnimationTypes.CastleAnimation),
                true, map, GameStatus.getInstance().getPlayers().get(numberOfPlayerOwner));

        castleMob.setCoordinateXonMap(locX);
        castleMob.setCoordinateYonMap(locY);
        castleMob.setActualDefence(5);
        castleMob.setActualhp(5);
        castleMob.setMaxHp(5);
        castleMob.setSize(Options.tileSize, Options.tileSize);
        castleMob.setPosition(
                castleMob.getCoordinateXonMap() * Options.tileSize,
                castleMob.getCoordinateYonMap() * Options.tileSize);
//        castleMob.getPlayerColorImage().setPosition(
//                castleMob.getCoordinateXonMap() * Options.tileSize,
//                castleMob.getCoordinateYonMap() * Options.tileSize);
        field.setCastleMob(castleMob);
        GameStatus.getInstance().getPlayers().get(numberOfPlayerOwner).getCastleMobs().add(castleMob);
        //GameStatus.getInstance().getPlayers().get(numberOfPlayerOwner).getPlayerMobs().add(playerMob);

        return castleMob;
    }

    /**
     * Returns class of player mob
     * @param intPlayerMobClass ineger number of class 0 - Knight, 1 - Wizard
     * @return class type.
     */
    public PlayerMobClasses getPlayerMobClass(int intPlayerMobClass){
        switch (intPlayerMobClass){
            case 0:
                return PlayerMobClasses.Knight;
            case 1:
                return PlayerMobClasses.Wizard;
            default:
                return PlayerMobClasses.Knight;
        }
    }
}
