package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.ai.AI;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.creators.PlayerMobCreator;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * class for buying new player mob.
 * Created by v on 2017-01-30.
 */
public class BuyPlayerMob {
    private static BuyPlayerMob instance = new BuyPlayerMob();

    private BuyPlayerMob() {
    }

    public static BuyPlayerMob getInstance() {
        return instance;
    }

    /**
     * Returns window to buy new player mob.
     * @param castleMob object where new player will come.
     * @return Window object
     */
    public Window getBuyPlayerMobWindow(final CastleMob castleMob) {
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(500, 300);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        Label lblAsk = new Label("Do you want to buy a new hero?", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        Label lblPrice = new Label("Price: 100 GOLD", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        final Label lblClass = new Label("Class: Knight", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        TextButton tbNext = new TextButton("NEXT", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        TextButton tbYes = new TextButton("YES", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        TextButton tbNo = new TextButton("NO", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        GameStatus.getInstance().setNewPlayerMobClass(PlayerMobClasses.Knight);

        tbYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (castleMob.getPlayerOwner().getGold() > 99) {

                    PlayerMob playerMob = PlayerMobCreator.getInstance().createPlayerMob(GameStatus.getInstance().getNewPlayerMobClass(),
                            castleMob.getPlayerOwner().getInedxOfPlayerInArrayOfPlayer(),
                            castleMob.getCoordinateXonMap(),
                            castleMob.getCoordinateYonMap(),
                            GameStatus.getInstance().getMap().getFields()[castleMob.getCoordinateXonMap()][castleMob.getCoordinateYonMap()]);

                    castleMob.getPlayerOwner().decreaseGold(100);
                    castleMob.getPlayerOwner().changeGoldLabel(castleMob.getPlayerOwner());

                    GameStatus.getInstance().getMapStage().addActor(playerMob.getPlayerColorImage());
                    GameStatus.getInstance().getMapStage().addActor(playerMob);

                    MoveManager.unselectCastles(GameStatus.getInstance().getMap());

                    if (NetStatus.getInstance().getClient() != null) {
                        Network.BuyPlayerMob buyPlayerMob = new Network.BuyPlayerMob();
                        buyPlayerMob.enemyID = NetStatus.getInstance().getEnemyId();
                        buyPlayerMob.locXofCastleOnMap = playerMob.getCoordinateXonMap();
                        buyPlayerMob.locYofCastleOnMap = playerMob.getCoordinateYonMap();

                        if (GameStatus.getInstance().getNewPlayerMobClass().equals(PlayerMobClasses.Knight))
                            buyPlayerMob.enemyClass = 0;
                        else
                            buyPlayerMob.enemyClass = 1;

                        NetStatus.getInstance().getClient().sendTCP(buyPlayerMob);
                    }
                } else {
                    Dialog dialog = new Dialog("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
                    dialog.text("Not enough gold");
                    dialog.button("CLOSE");
                    dialog.show(GameStatus.getInstance().getMainStage());
                }

                window.remove();
            }
        });

        tbNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        tbNext.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (GameStatus.getInstance().getNewPlayerMobClass().equals(PlayerMobClasses.Knight)){
                    GameStatus.getInstance().setNewPlayerMobClass(PlayerMobClasses.Wizard);
                    lblClass.setText("Class: Wizard");
                } else if (GameStatus.getInstance().getNewPlayerMobClass().equals(PlayerMobClasses.Wizard)){
                    GameStatus.getInstance().setNewPlayerMobClass(PlayerMobClasses.Knight);
                    lblClass.setText("Class: Knight");
                }
            }
        });

        window.add(lblAsk).pad(5).colspan(2);
        window.row();
        window.add(lblPrice).pad(5).colspan(2);
        window.row();
        window.add(lblClass).pad(5).colspan(2);
        window.row();
        window.add(tbNext).pad(5).colspan(2);
        window.row();
        window.add(tbYes).pad(5).size(200, 50);
        window.add(tbNo).pad(5).size(200, 50);

        return window;
    }

    public void buyNewPlayerMob(PlayerMobClasses playerMobClasses, int locationXofCastle, int locationYofCastle, boolean isAI) {
        CastleMob castleMob = GameStatus.getInstance().getMap().getFields()[locationXofCastle][locationYofCastle].getCastleMob();
        PlayerMob playerMob = PlayerMobCreator.getInstance().createPlayerMob(
                playerMobClasses,
                castleMob.getPlayerOwner().getInedxOfPlayerInArrayOfPlayer(),
                castleMob.getCoordinateXonMap(),
                castleMob.getCoordinateYonMap(),
                GameStatus.getInstance().getMap().getFields()[castleMob.getCoordinateXonMap()][castleMob.getCoordinateYonMap()]);

        if (isAI) {
            playerMob.setAi(new AI());
            playerMob.getPlayerOwner().setGold(playerMob.getPlayerOwner().getGold() - 100);
        }

        GameStatus.getInstance().getMapStage().addActor(playerMob.getPlayerColorImage());
        GameStatus.getInstance().getMapStage().addActor(playerMob);

        for (Field field : GameStatus.getInstance().getFields()) {
            field.getFog().toFront();
        }
    }
}
