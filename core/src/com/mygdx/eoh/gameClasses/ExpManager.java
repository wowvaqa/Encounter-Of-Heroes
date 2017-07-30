package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.assets.AssetsSounds;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * Class to control exp gain and leveling.
 * Created by v on 2017-05-30.
 */

public class ExpManager {

    private int tmpAtc;
    private int tmpDef;
    private int tmpSpd;
    private int tmpPow;
    private int tmpWis;
    private int tmpHp;

    private PlayerMob playerMob;
    private boolean nextLevel = false;

    public ExpManager(PlayerMob playerMob) {
        this.playerMob = playerMob;
    }

    /**
     * Fill the array with next level exp points.
     *
     * @param levelingArray
     */
    public static void fillLevelingArray(int[] levelingArray) {

        levelingArray[0] = 0;
        levelingArray[1] = 0;
        levelingArray[2] = 100;
        levelingArray[3] = 300;
        levelingArray[4] = 600;
        levelingArray[5] = 1000;
        levelingArray[5] = 1500;
        levelingArray[6] = 2100;
        levelingArray[7] = 2800;
        levelingArray[8] = 3600;
        levelingArray[9] = 4500;
        levelingArray[10] = 5500;

//        int counter = 100;
//
//        for (int i = 4; i < 99; i++) {
//            levelingArray[i] = levelingArray[i - 1] + counter;
//            counter += 100;
//        }

//        for (int i = 1; i < 99; i++) {
//            System.out.println("Poziom " + i + " : " + levelingArray[i]);
//        }
    }

    public void checkNextLevel() {
        if (playerMob.getActualExp() >= playerMob.getLevelingArray()[playerMob.getLevel() + 1] && !nextLevel) {
            System.out.println("NOWY POZIOM!!!");
            nextLevel();

            if (playerMob.getPlayerOwner().equals(GameStatus.getInstance().getCurrentPlayerTurn())){
                playPromotionSound();
            }
        }
    }

    /**
     * Play sound after next level.
     */
    private void playPromotionSound(){
        AssetsSounds.getInstance().getManager().get("sounds/levelUp.mp3", Sound.class).play();
    }

    private void nextLevel() {
        nextLevel = true;
        GameStatus.getInstance().getGameInterface().imageButtonPromotion.setVisible(true);
        //playerMob.setLevel(playerMob.getLevel() + 1);
    }

    public Window getNextLevelWindow() {
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(700, 600);
        window.setModal(true);
        Positioning.setWindowToCenter(window);


        playerMob.setLevel(playerMob.getLevel() + 1);

        final Table choiceTable = new Table();
        choiceTable.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).size(50, 50);

        Table expirienceTable = new Table();
        expirienceTable.padBottom(5);
        expirienceTable.add(new Label("Next Level: " + playerMob.getLevel(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32"));
        expirienceTable.row();
        expirienceTable.add(new Label("Experience points: " + playerMob.getActualExp(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32"));
        expirienceTable.row();
        expirienceTable.add(new Label("Next level: " + playerMob.getLevelingArray()[playerMob.getLevel() + 1], AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32"));

        Table attributeUpTable = new Table();
        attributeUpTable.padBottom(5);
        switch (attributeUp()) {
            case 0:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("Attack + 1", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                playerMob.setActualAttack(playerMob.getActualAttack() + 1);
                playerMob.setAttack(playerMob.getAttack() + 1);
                break;
            case 1:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/defenceIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("Defence + 1", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                playerMob.setActualDefence(playerMob.getActualDefence() + 1);
                playerMob.setDefence(playerMob.getDefence() + 1);
                break;
            case 2:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/hpIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("HP + 3", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                playerMob.setMaxHp(playerMob.getMaxHp() + 3);
                playerMob.setActualhp(playerMob.getMaxHp());
                break;
            case 3:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/powerIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("Power + 1", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                playerMob.setActualPower(playerMob.getActualPower() + 1);
                playerMob.setPower(playerMob.getPower() + 1);
                break;
            case 4:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/wisdomIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("Wisdom + 1", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                playerMob.setActualWisdom(playerMob.getActualWisdom() + 1);
                playerMob.setWisdom(playerMob.getWisdom() + 1);
                playerMob.setMaxMana(playerMob.getMaxMana() + 1);
                break;
            case 5:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/hpIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("HP + 3", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                playerMob.setMaxHp(playerMob.getMaxHp() + 3);
                playerMob.setActualhp(playerMob.getMaxHp());
                break;
            default:
                attributeUpTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).pad(5).size(100, 100);
                attributeUpTable.row();
                attributeUpTable.add(new Label("Attack + 1", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
                break;
        }

        Table attributeChoiceTable = new Table();

        TextButton btnAtc = new TextButton("Attack", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        btnAtc.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).size(50, 50);
                clearTmpAttributes();
                tmpAtc += 1;
            }
        });
        TextButton btnDef = new TextButton("Defence", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        btnDef.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/defenceIcon.png", Texture.class))).size(50, 50);
                clearTmpAttributes();
                tmpDef += 1;
            }
        });
        TextButton btnSpd = new TextButton("Speed", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        btnSpd.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/speedIcon.png", Texture.class))).size(50, 50);
                clearTmpAttributes();
                tmpSpd += 1;
            }
        });
        TextButton btnPow = new TextButton("Power", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        btnPow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/powerIcon.png", Texture.class))).size(50, 50);
                clearTmpAttributes();
                tmpPow += 1;
            }
        });
        TextButton btnWis = new TextButton("Wisdom", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        btnWis.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/wisdomIcon.png", Texture.class))).size(50, 50);
                clearTmpAttributes();
                tmpWis += 1;
            }
        });
        TextButton btnHp = new TextButton("HP", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        btnHp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.add(new Image(
                        AssetsGameScreen.getInstance().getManager().get("game/interface/hpIcon.png", Texture.class))).size(50, 50);
                clearTmpAttributes();
                tmpHp += 1;
            }
        });

        Table attributeChoiceButtonTable = new Table();
        attributeChoiceButtonTable.add(new Label("Choose attribute to upgrade:", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).colspan(5);
        attributeChoiceButtonTable.row();
        attributeChoiceButtonTable.add(btnAtc);
        attributeChoiceButtonTable.add(btnDef);
        attributeChoiceButtonTable.add(btnSpd);
        attributeChoiceButtonTable.add(btnPow);
        attributeChoiceButtonTable.add(btnWis);
        attributeChoiceButtonTable.add(btnHp);

        attributeChoiceTable.add(attributeChoiceButtonTable);
        attributeChoiceTable.row();
        attributeChoiceTable.add(choiceTable);

        TextButton tbCancel = new TextButton("CLOSE", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                playerMob.getExpManager().setNextLevel(false);
                GameStatus.getInstance().getGameInterface().imageButtonPromotion.setVisible(false);
                applyTmpAttributes();

                if (NetStatus.getInstance().getClient() != null) {
                    sendLevelUpInNetwork();
                }

                window.remove();
            }
        });

        window.add(expirienceTable).align(Align.top);
        window.row();
        window.add(attributeUpTable).align(Align.top);
        window.row();
        window.add(attributeChoiceTable).align(Align.top);
        window.row();
        window.add(tbCancel).pad(5).size(175, 50);

        return window;
    }

    /**
     * Sends information about the promotion through the network.
     */
    private void sendLevelUpInNetwork() {
        Network.ChangeAtributes ca = new Network.ChangeAtributes();

        ca.enemyId = NetStatus.getInstance().getEnemyId();
        ca.playerIndex = playerMob.getPlayerOwner().getInedxOfPlayerInArrayOfPlayer();
        ca.playerMobIndex = playerMob.getPlayerMobIndex();

        ca.attack = playerMob.getAttack();
        ca.actualAttack = playerMob.getActualAttack();
        ca.defence = playerMob.getDefence();
        ca.actualDefence = playerMob.getActualDefence();
        ca.speed = playerMob.getSpeed();
        ca.actualSpeed = playerMob.getActualSpeed();
        ca.power = playerMob.getPower();
        ca.actualPower = playerMob.getActualPower();
        ca.wisdom = playerMob.getWisdom();
        ca.actualWisdom = playerMob.getActualWisdom();
        ca.actualHp = playerMob.getActualhp();
        ca.maxHp = playerMob.getMaxHp();
        ca.exp = playerMob.getActualExp();
        ca.expToNextLevel = playerMob.getLevelingArray()[playerMob.getLevel() + 1];
        ca.level = playerMob.getLevel();

        NetStatus.getInstance().getClient().sendTCP(ca);
    }

    /**
     * Clear temp attributes
     */
    private void clearTmpAttributes() {
        tmpAtc = 0;
        tmpDef = 0;
        tmpSpd = 0;
        tmpPow = 0;
        tmpWis = 0;
        tmpHp = 0;
    }

    /**
     * Apply temp attributes to mob.
     */
    private void applyTmpAttributes() {
        playerMob.setAttack(playerMob.getAttack() + tmpAtc);
        playerMob.setActualAttack(playerMob.getActualAttack() + tmpAtc);

        playerMob.setDefence(playerMob.getDefence() + tmpDef);
        playerMob.setActualDefence(playerMob.getActualDefence() + tmpDef);

        playerMob.setSpeed(playerMob.getSpeed() + tmpSpd);
        playerMob.setActualSpeed(playerMob.getActualSpeed() + tmpSpd);

        playerMob.setPower(playerMob.getPower() + tmpPow);
        playerMob.setActualPower(playerMob.getActualPower() + tmpPow);

        playerMob.setWisdom(playerMob.getWisdom() + tmpWis);
        playerMob.setActualWisdom(playerMob.getActualWisdom() + tmpWis);
        playerMob.setMaxMana(playerMob.getMaxMana() + tmpWis);

        playerMob.setMaxHp(playerMob.getMaxHp() + tmpHp);
        playerMob.setActualhp(playerMob.getActualhp() + tmpHp);

        clearTmpAttributes();
    }

    /***********************************************************************************************
     * STATIC FUNCTIONS
     **********************************************************************************************/

    private int attributeUp() {
        int[] knightAttribute = new int[99];
        int[] wizardAttribute = new int[99];

        // 0 - attack, 1 - defence 2 - hp, 3 - power, 4 - wisdom, 5 - hp
        switch (playerMob.getPlayerMobClass()) {
            case Knight:
                int attribute = 0;
                for (int i = 2; i < 99; i++) {
                    knightAttribute[i] = attribute;
                    attribute += 1;
                    if (attribute > 2)
                        attribute = 0;
                }
                return knightAttribute[playerMob.getLevel()];

            case Wizard:
                attribute = 3;
                for (int i = 2; i < 99; i++) {
                    wizardAttribute[i] = attribute;
                    attribute += 1;
                    if (attribute > 5)
                        attribute = 3;
                }
                return wizardAttribute[playerMob.getLevel()];
        }
        return 0;
    }

    /***********************************************************************************************
     * GETTERS AND SETTERS
     **********************************************************************************************/
    public boolean isNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(boolean nextLevel) {
        this.nextLevel = nextLevel;
    }
}
