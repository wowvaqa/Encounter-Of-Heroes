package com.mygdx.eoh.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.mygdx.eoh.Equipment.Equip;
import com.mygdx.eoh.Equipment.EquipKinds;
import com.mygdx.eoh.Equipment.EquipTypes;
import com.mygdx.eoh.Equipment.Treasure;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.animation.AnimationSpellCreator;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.creators.BuldingCreator;
import com.mygdx.eoh.creators.PlayerMobCreator;
import com.mygdx.eoh.defaultClasses.DefaultCamera;
import com.mygdx.eoh.defaultClasses.DefaultDamageLabel;
import com.mygdx.eoh.defaultClasses.DefaultGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultGestureDetector;
import com.mygdx.eoh.defaultClasses.DefaultGestureListener;
import com.mygdx.eoh.effects.InstantEffect;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.Buldings;
import com.mygdx.eoh.enums.FreeMobsKinds;
import com.mygdx.eoh.enums.InstantEffects;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Terrains;
import com.mygdx.eoh.gameClasses.BuyPlayerMob;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Map;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.Player;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.gameClasses.Positioning;
import com.mygdx.eoh.items.AvailableItems;
import com.mygdx.eoh.items.ItemCreator;
import com.mygdx.eoh.mapEditor.MapFile;
import com.mygdx.eoh.mob.FreeMobCreator;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

import java.util.ArrayList;

/**
 * Screen show network game
 * Created by v on 2016-11-22.
 */
public class ScreenNetGame extends DefaultGameScreen {

    private Window waitingWindow;
    private boolean cameraStartPosition = false;

    public ScreenNetGame(GameStatus gs) {
        super(gs);
        createTables();

        super.createMapStage();
        GameStatus.getInstance().setMap(createMap(GameStatus.getInstance().getMapFile()));
        super.fillStage(GameStatus.getInstance().getMap());
    }

    private Map createMap(MapFile mapfile) {

        Map map = new Map();
        GameStatus.getInstance().setPlayers(new ArrayList<Player>());

        map.setFieldsColumns(mapfile.mapColumns);
        map.setFieldsRows(mapfile.mapRows);

        Field[][] fields = new Field[mapfile.mapColumns][mapfile.mapRows];

        map.setFields(fields);

        DefaultGestureListener myGL = new DefaultGestureListener(super.getMapStage(), mapfile);
        DefaultGestureDetector myGD = new DefaultGestureDetector(myGL);
        getInputMultiplexer().clear();
        getInputMultiplexer().addProcessor(myGD);

        getInputMultiplexer().addProcessor(getMainStage());

        super.createFreameAroudMap(mapfile);

        GameStatus.getInstance().getPlayers().clear();
        GameStatus.getInstance().getPlayers().add(new Player(1));
        GameStatus.getInstance().getPlayers().add(new Player(2));

        GameStatus.getInstance().setCurrentPlayerTurn(GameStatus.getInstance().getPlayers().get(
                NetStatus.getInstance().getNetPlayers().get(0).getPlayerNumber() - 1
        ));
        GameStatus.getInstance().setCurrentPlayerIcon(GameStatus.getInstance().getCurrentPlayerTurn().getPlayerIcon());

        GameStatus.getInstance().getUpperBarTable().getCells().get(0).setActor(
                GameStatus.getInstance().getCurrentPlayerIcon()
        );

        for (int i = 0; i < mapfile.mapColumns; i++) {
            for (int j = 0; j < mapfile.mapRows; j++) {

                if (mapfile.fields[i][j].terrains.equals(MapFile.Terrains.River)) {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                            Map.getTextureRegionName(i, j, mapfile, Terrains.River)
                    ));
                } else if (mapfile.fields[i][j].terrains.equals(MapFile.Terrains.Mountain)) {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                            Map.getTextureRegionName(i, j, mapfile, Terrains.Mountain)
                    ));
                } else if (mapfile.fields[i][j].terrains.equals(MapFile.Terrains.Forest)) {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                            Map.getTextureRegionName(i, j, mapfile, Terrains.Forest)
                    ));
                } else {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion("grass"));
                }

                if (mapfile.fields[i][j].itemGold) {
                    fields[i][j].setItem(ItemCreator.getInstance().createItem(AvailableItems.Gold, i, j));
                }

                if (mapfile.fields[i][j].healthPotion) {
                    fields[i][j].setItem(ItemCreator.getInstance().createItem(AvailableItems.HealthPotion, i, j));
                }

                if (mapfile.fields[i][j].manaPotion) {
                    fields[i][j].setItem(ItemCreator.getInstance().createItem(AvailableItems.ManaPotion, i, j));
                }

                if (mapfile.fields[i][j].towerHospital)
                    fields[i][j].setBulding(BuldingCreator.getInstance().createBulding(Buldings.Hospital, i, j));

                /***********************************************************************************
                 * TREASURE CREATION
                 **********************************************************************************/
                if (mapfile.fields[i][j].tresureBoxLvl1) {
                    fields[i][j].setTreasure(Treasure.createTreasure(1, i, j));
                }

                if (mapfile.fields[i][j].tresureBoxLvl2) {
                    fields[i][j].setTreasure(Treasure.createTreasure(2, i, j));
                }

                /***********************************************************************************
                 * FREE MOB CLASSES
                 **********************************************************************************/
                if (mapfile.fields[i][j].mobSkeletonLocation) {
                    fields[i][j].setFreeMob(FreeMobCreator.getInstance().createFreeMob(FreeMobsKinds.Skeleton, i, j));
                }

                /***********************************************************************************
                 * PLAYER MOB CLASSES
                 **********************************************************************************/
                PlayerMobClasses player1mobClass;
                PlayerMobClasses player2mobClass;

                if (NetStatus.getInstance().getNetGamePlayerNumber() == 1) {
                    player1mobClass = GameStatus.getInstance().getNewPlayerMobClass();
                    player2mobClass = PlayerMobCreator.getInstance().getPlayerMobClass(NetStatus.getInstance().getNewPlayerMobEnemyClass());
                } else {
                    player1mobClass = PlayerMobCreator.getInstance().getPlayerMobClass(NetStatus.getInstance().getNewPlayerMobEnemyClass());
                    player2mobClass = GameStatus.getInstance().getNewPlayerMobClass();
                }

                /***********************************************************************************
                 * PLAYERS START LOCATIONS
                 **********************************************************************************/
                if (mapfile.fields[i][j].player1StartLocation) {
                    fields[i][j].setPlayerMob(
                            PlayerMobCreator.getInstance().createPlayerMob(
                                    player1mobClass, map, 0, i, j, fields[i][j]
                            )
                    );
                    fields[i][j].setCastleMob(
                            PlayerMobCreator.getInstance().createCastleMob(
                                    map, 0, i, j, fields[i][j]
                            )
                    );
                }

                if (mapfile.fields[i][j].player2StartLocation) {
                    fields[i][j].setPlayerMob(
                            PlayerMobCreator.getInstance().createPlayerMob(
                                    player2mobClass, map, 1, i, j, fields[i][j]
                            )
                    );
                    fields[i][j].setCastleMob(
                            PlayerMobCreator.getInstance().createCastleMob(
                                    map, 1, i, j, fields[i][j]
                            )
                    );
                }

                fields[i][j].setSize(Options.tileSize, Options.tileSize);
                fields[i][j].setPosition(i * Options.tileSize, j * Options.tileSize);
            }
        }

//        PlayerMob playerMob = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
//                AnimationTypes.KnightStanding), true, super.getMapStage(), map, GameStatus.getInstance().getPlayers().get(0));
//        playerMob.setCoordinateXonMap(1);
//        playerMob.setCoordinateYonMap(1);
//        playerMob.setSpeed(1);
//        playerMob.setActualSpeed(1);
//        playerMob.setActualhp(5);
//        playerMob.setActualAttack(5);
//        playerMob.setActualDefence(5);
//        playerMob.setSize(Options.tileSize, Options.tileSize);
//        playerMob.setPosition(
//                playerMob.getCoordinateXonMap() * Options.tileSize,
//                playerMob.getCoordinateYonMap() * Options.tileSize);
//        playerMob.getPlayerColorImage().setPosition(
//                playerMob.getCoordinateXonMap() * Options.tileSize,
//                playerMob.getCoordinateYonMap() * Options.tileSize);
//        fields[1][1].setPlayerMob(playerMob);
//        GameStatus.getInstance().getPlayers().get(0).getPlayerMobs().add(playerMob);
//
//        PlayerMob playerMob2 = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
//                AnimationTypes.KnightStanding), true, super.getMapStage(), map, GameStatus.getInstance().getPlayers().get(1));
//        playerMob2.setCoordinateXonMap(7);
//        playerMob2.setCoordinateYonMap(7);
//        playerMob2.setSpeed(30);
//        playerMob2.setActualSpeed(30);
//        playerMob2.setActualhp(5);
//        playerMob2.setActualAttack(5);
//        playerMob2.setActualDefence(5);
//        playerMob2.setSize(Options.tileSize, Options.tileSize);
//        playerMob2.setPosition(
//                playerMob2.getCoordinateXonMap() * Options.tileSize,
//                playerMob2.getCoordinateYonMap() * Options.tileSize);
//        playerMob2.getPlayerColorImage().setPosition(
//                playerMob2.getCoordinateXonMap() * Options.tileSize,
//                playerMob2.getCoordinateYonMap() * Options.tileSize);
//        fields[7][7].setPlayerMob(playerMob2);
//        GameStatus.getInstance().getPlayers().get(1).getPlayerMobs().add(playerMob2);
//
//        PlayerMob playerMob3 = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
//                AnimationTypes.KnightStanding), true, super.getMapStage(), map, GameStatus.getInstance().getPlayers().get(1));
//        playerMob3.setCoordinateXonMap(5);
//        playerMob3.setCoordinateYonMap(5);
//        playerMob3.setSpeed(15);
//        playerMob3.setActualSpeed(15);
//        playerMob3.setActualhp(5);
//        playerMob3.setActualAttack(5);
//        playerMob3.setActualDefence(5);
//        playerMob3.setSize(Options.tileSize, Options.tileSize);
//        playerMob3.setPosition(
//                playerMob3.getCoordinateXonMap() * Options.tileSize,
//                playerMob3.getCoordinateYonMap() * Options.tileSize);
//        playerMob3.getPlayerColorImage().setPosition(
//                playerMob3.getCoordinateXonMap() * Options.tileSize,
//                playerMob3.getCoordinateYonMap() * Options.tileSize);
//        fields[5][5].setPlayerMob(playerMob3);
//        GameStatus.getInstance().getPlayers().get(1).getPlayerMobs().add(playerMob3);

        GameStatus.getInstance().setMap(map);

        return map;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!cameraStartPosition) {

            System.out.println("Cam posi X: " + getMapStageCamera().position.x);
            System.out.println("Cam posi Y: " + getMapStageCamera().position.y);

            float positionX = GameStatus.getInstance().getCurrentPlayerTurn().getPlayerMobs().get(0).getX();
            float positiony = GameStatus.getInstance().getCurrentPlayerTurn().getPlayerMobs().get(0).getY();

            System.out.println("Pl MOb posi X: " + positionX);
            System.out.println("Pl MOb posi Y: " + positiony);

            ((DefaultCamera) getMapStageCamera()).position.x = positionX + Options.tileSize / 2;
            ((DefaultCamera) getMapStageCamera()).position.y = positiony + Options.tileSize / 2;
            cameraStartPosition = true;

        }

        if (NetStatus.getInstance().isGameReady()) {
            waitingWindow.remove();
            NetStatus.getInstance().setGameReady(false);
        }

        if (NetStatus.getInstance().isSpellCastNet()) {

            GameStatus.getInstance().getMap().getFields()
                    [NetStatus.getInstance().getLocationXofSpellCaster()]
                    [NetStatus.getInstance().getLocationYofSpellCaster()].getPlayerMob().decreseMana(
                    NetStatus.getInstance().getSpellManaCost()
            );

            System.out.println("MANA: " + GameStatus.getInstance().getMap().getFields()
                    [NetStatus.getInstance().getLocationXofSpellCaster()]
                    [NetStatus.getInstance().getLocationYofSpellCaster()].getPlayerMob().getActualMana());
            NetStatus.getInstance().setSpellCastNet(false);
        }

        if (NetStatus.getInstance().isInstantEffectNet()) {
            switch (NetStatus.getInstance().getEnumIntVarible()) {

                // FIREBAL INSTANT EFFECT
                case 0:

                    System.out.println("X: " + NetStatus.getInstance().getLocationXofCaster() + " Y: " + NetStatus.getInstance().getLocationYofCaster());

                    GameStatus.getInstance().getMap().getFields()
                            [NetStatus.getInstance().getLocationXofCaster()]
                            [NetStatus.getInstance().getLocationYofCaster()].getPlayerMob().changeToCastAnimation(
                            GameStatus.getInstance().getMap().getFields()
                                    [NetStatus.getInstance().getLocationXofCaster()]
                                    [NetStatus.getInstance().getLocationYofCaster()].getPlayerMob()
                    );

                    InstantEffect instantEffect = new InstantEffect(
                            AnimationSpellCreator.getInstance().makeSpellAnimation(
                                    InstantEffects.FiraballDamage),
                            false, InstantEffects.FiraballDamage
                    );

                    instantEffect.setPosition(
                            NetStatus.getInstance().getLocationXofDefender() * Options.tileSize,
                            NetStatus.getInstance().getLocationYofDefender() * Options.tileSize
                    );

                    GameStatus.getInstance().getMapStage().addActor(instantEffect);

                    if (GameStatus.getInstance().getMap().getFields()
                            [NetStatus.getInstance().getLocationXofDefender()]
                            [NetStatus.getInstance().getLocationYofDefender()].getPlayerMob() != null) {
                        FightManager.setActualHPofMob(
                                GameStatus.getInstance().getMap().getFields()
                                        [NetStatus.getInstance().getLocationXofDefender()]
                                        [NetStatus.getInstance().getLocationYofDefender()].getPlayerMob(),
                                NetStatus.getInstance().getIntVarible()
                        );
                    } else if (GameStatus.getInstance().getMap().getFields()
                            [NetStatus.getInstance().getLocationXofDefender()]
                            [NetStatus.getInstance().getLocationYofDefender()].getFreeMob() != null) {
                        FightManager.setActualHPofMob(
                                GameStatus.getInstance().getMap().getFields()
                                        [NetStatus.getInstance().getLocationXofDefender()]
                                        [NetStatus.getInstance().getLocationYofDefender()].getFreeMob(),
                                NetStatus.getInstance().getIntVarible()
                        );
                    }

                    DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                            Integer.toString(NetStatus.getInstance().getIntVarible()),
                            (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "fight",
                            NetStatus.getInstance().getLocationXofDefender() * Options.tileSize + Options.tileSize / 2,
                            NetStatus.getInstance().getLocationYofDefender() * Options.tileSize + Options.tileSize / 2);
                    GameStatus.getInstance().getMapStage().addActor(defaultDamageLabel);

                    break;

                //ATTACK UPGRADE INSTANT EFFECT
                case 1:
                    instantEffect = new InstantEffect(
                            AnimationSpellCreator.getInstance().makeSpellAnimation(
                                    InstantEffects.AttackUpgrade),
                            false, InstantEffects.AttackUpgrade
                    );

                    instantEffect.action(
                            GameStatus.getInstance().getMap().getFields()[NetStatus.getInstance().getLocationXofCaster()][NetStatus.getInstance().getLocationYofCaster()].getPlayerMob(),
                            GameStatus.getInstance().getMap().getFields()[NetStatus.getInstance().getLocationXofDefender()][NetStatus.getInstance().getLocationYofDefender()].getPlayerMob()
                    );

                    instantEffect.setPosition(
                            NetStatus.getInstance().getLocationXofDefender() * Options.tileSize,
                            NetStatus.getInstance().getLocationYofDefender() * Options.tileSize
                    );

                    GameStatus.getInstance().getMapStage().addActor(instantEffect);

                    break;
                // Health potion use
                case 2:
                    instantEffect = new InstantEffect(
                            AnimationCreator.getInstance().makeAnimation(AnimationTypes.PotionUseAnimation),
                            false, InstantEffects.HealthPotion
                    );

                    instantEffect.action(
                            GameStatus.getInstance().getMap().getFields()[NetStatus.getInstance().getLocationXofCaster()][NetStatus.getInstance().getLocationYofCaster()].getPlayerMob(),
                            GameStatus.getInstance().getMap().getFields()[NetStatus.getInstance().getLocationXofDefender()][NetStatus.getInstance().getLocationYofDefender()].getPlayerMob()
                    );

                    instantEffect.setPosition(
                            NetStatus.getInstance().getLocationXofDefender() * Options.tileSize,
                            NetStatus.getInstance().getLocationYofDefender() * Options.tileSize
                    );

                    GameStatus.getInstance().getMapStage().addActor(instantEffect);
                    break;
                // Mana potion use
                case 3:
                    instantEffect = new InstantEffect(
                            AnimationCreator.getInstance().makeAnimation(AnimationTypes.PotionUseAnimation),
                            false, InstantEffects.HealthPotion
                    );

                    instantEffect.action(
                            GameStatus.getInstance().getMap().getFields()[NetStatus.getInstance().getLocationXofCaster()][NetStatus.getInstance().getLocationYofCaster()].getPlayerMob(),
                            GameStatus.getInstance().getMap().getFields()[NetStatus.getInstance().getLocationXofDefender()][NetStatus.getInstance().getLocationYofDefender()].getPlayerMob()
                    );

                    instantEffect.setPosition(
                            NetStatus.getInstance().getLocationXofDefender() * Options.tileSize,
                            NetStatus.getInstance().getLocationYofDefender() * Options.tileSize
                    );

                    GameStatus.getInstance().getMapStage().addActor(instantEffect);
                    break;

                default:
                    instantEffect = new InstantEffect(AnimationSpellCreator.getInstance().makeSpellAnimation(InstantEffects.FiraballDamage), false, InstantEffects.FiraballDamage);
                    instantEffect.setPosition(NetStatus.getInstance().getLocationXofDefender(), NetStatus.getInstance().getLocationYofDefender());
                    GameStatus.getInstance().getMapStage().addActor(instantEffect);
                    break;
            }
            NetStatus.getInstance().setInstantEffectNet(false);
        }

        if (NetStatus.getInstance().isNewPlayerMob()) {

            switch (NetStatus.getInstance().getNewPlayerMobClass()) {
                case 0:
                    BuyPlayerMob.getInstance().buyNewPlayerMob(
                            PlayerMobClasses.Knight,
                            NetStatus.getInstance().getLocationXvarible(),
                            NetStatus.getInstance().getLocationYvarible()
                    );
                    break;
                case 1:
                    BuyPlayerMob.getInstance().buyNewPlayerMob(
                            PlayerMobClasses.Wizard,
                            NetStatus.getInstance().getLocationXvarible(),
                            NetStatus.getInstance().getLocationYvarible()
                    );
                    break;
                default:
                    BuyPlayerMob.getInstance().buyNewPlayerMob(
                            PlayerMobClasses.Wizard,
                            NetStatus.getInstance().getLocationXvarible(),
                            NetStatus.getInstance().getLocationYvarible()
                    );
                    break;
            }

            NetStatus.getInstance().setNewPlayerMob(false);
        }

        if (NetStatus.getInstance().isEquipRemove()) {
            Equip.selectedEquip =
                    GameStatus.getInstance().getMap().getFields()[
                            NetStatus.getInstance().getEquipRemoveLocXofPlayerMob()
                            ][NetStatus.getInstance().getEquipRemoveLocYofPlayerMob()
                            ].getPlayerMob().getEquip().get(
                            NetStatus.getInstance().getEquipIndex()
                    );

            GameStatus.getInstance().getMap().getFields()[
                    NetStatus.getInstance().getEquipRemoveLocXofPlayerMob()
                    ][NetStatus.getInstance().getEquipRemoveLocYofPlayerMob()
                    ].getPlayerMob().getEquip().removeValue(Equip.selectedEquip, true);

            NetStatus.getInstance().setEquipRemove(false);
        }

        if (NetStatus.getInstance().isEquipAssumeCancel()) {
            GameStatus.getInstance().getMap().getFields()[
                    NetStatus.getInstance().getEquipAssumeCancelLocX()
                    ][NetStatus.getInstance().getEquipAssumeCancelLocY()
                    ].getPlayerMob().getEquip().add(Equip.selectedEquip);
            NetStatus.getInstance().setEquipAssumeCancel(false);
        }

        if (NetStatus.getInstance().isEquipAssume()) {

            PlayerMob playerMobTmp;

            playerMobTmp = GameStatus.getInstance().getMap().getFields()[
                    NetStatus.getInstance().getEquipAssumeLocX()
                    ][NetStatus.getInstance().getEquipAssumeLocY()
                    ].getPlayerMob();

            if (Equip.selectedEquip.getEquipType() == EquipTypes.Weapon) {
                if (playerMobTmp.getWeapon().getEquipKind() != EquipKinds.None)
                    playerMobTmp.getEquip().add(playerMobTmp.getWeapon());
                playerMobTmp.setWeapon(Equip.selectedEquip);
            } else if (Equip.selectedEquip.getEquipType() == EquipTypes.Armor) {
                if (playerMobTmp.getArmor().getEquipKind() != EquipKinds.None)
                    playerMobTmp.getEquip().add(playerMobTmp.getArmor());
                playerMobTmp.setArmor(Equip.selectedEquip);
            } else if (Equip.selectedEquip.getEquipType() == EquipTypes.Artifact) {
                if (playerMobTmp.getArtifact().getEquipKind() != EquipKinds.None)
                    playerMobTmp.getEquip().add(playerMobTmp.getArtifact());
                playerMobTmp.setArtifact(Equip.selectedEquip);
            }

            NetStatus.getInstance().setEquipAssume(false);
        }

        /*******************************************************************************************
         * EQUIP CREATE
         ******************************************************************************************/
        if (NetStatus.getInstance().isEquipCreate()) {

            PlayerMob playerMobTmp;

            playerMobTmp = GameStatus.getInstance().getMap().getFields()
                    [NetStatus.getInstance().getEquipCreateLocX()]
                    [NetStatus.getInstance().getEquipCreateLocY()]
                    .getPlayerMob();

            playerMobTmp.getEquip().add(Equip.createEquip(
                    Equip.getEquipKindFromNetwork(NetStatus.getInstance().getEquipCreateEquipKind())
            ));

            GameStatus.getInstance().getMap().getFields()
                    [NetStatus.getInstance().getEquipCreateLocX()]
                    [NetStatus.getInstance().getEquipCreateLocY()]
                    .getTreasure().remove();

            GameStatus.getInstance().getMap().getFields()
                    [NetStatus.getInstance().getEquipCreateLocX()]
                    [NetStatus.getInstance().getEquipCreateLocY()]
                    .setTreasure(null);

            System.out.println("TWORZE EKWIPUNEK!!!!!!!!!!!!!!!!!!!!!!!!!");
            NetStatus.getInstance().setEquipCreate(false);
        }
    }

    /**
     * Waiting window
     *
     * @return Window class object.
     */
    private Window getWaitingWindow() {
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(400, 300);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        Label label = new Label("Oczekiwanie na gracza", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.add(label);

        return window;
    }


    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void show() {
        super.show();
        waitingWindow = getWaitingWindow();
        GameStatus.getInstance().getMainStage().addActor(waitingWindow);
        Network.ClientReadyToStartBattle clientReadyToStartBattle = new Network.ClientReadyToStartBattle();
        clientReadyToStartBattle.enemyId = NetStatus.getInstance().getEnemyId();
        NetStatus.getInstance().getClient().sendTCP(clientReadyToStartBattle);

        ((DefaultCamera) getMapStageCamera()).zoom = 2.5f;
        ((DefaultCamera) getMapStageCamera()).update();

    }
}
