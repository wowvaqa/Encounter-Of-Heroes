package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.defaultClasses.DefaultMob;
import com.mygdx.eoh.defaultClasses.DefaultPlayerColorIcon;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.mob.FreeMob;

/**
 * Representation of castle.
 * Created by v on 2016-12-29.
 */
public class CastleMob extends DefaultMob {

    private Map map;
    private Player playerOwner;
    private DefaultPlayerColorIcon playerColorImage;

//    private Table infoPlayerMobTable;
//    private Label hpLabel;
//    private Label apLabel;
//    private Label manaLabel;

    private boolean isSelected = false;

    public CastleMob(Animation animation, boolean isLooped, Map map, Player playerOwner) {
        super(animation, isLooped);

        setZIndex(6);
        this.map = map;
        this.playerOwner = playerOwner;
        addListener(this);
        createCastleMobColorImage();
        //createInfoTable();
    }

    private void createCastleMobColorImage() {
        playerColorImage = new DefaultPlayerColorIcon(playerOwner.getPlayerColor(), this);
        playerColorImage.setSize(Options.tileSize, Options.tileSize);
    }

    private void addListener(final CastleMob castleMob) {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameStatus.getInstance().getEquipmentTable().clear();

                if (!castleMob.isSelected) {

                    MoveManager.turnOffSelectedPlayersMobs();
                    MoveManager.unselectCastles(map);
                    FreeMob.unselectFreeMobs();

                    if (castleMob.playerOwner == GameStatus.getInstance().getCurrentPlayerTurn()) {
                        GameStatus.getInstance().getEquipmentTable().add(
                                GameStatus.getInstance().getCastleMobTable()
                        );
                        GameStatus.getInstance().getEquipmentTable().setVisible(true);
                        GameStatus.getInstance().setSelectedCastleMob(castleMob);
                    }

                    castleMob.isSelected = true;
                    castleMob.setAnimation(AnimationCreator.getInstance().makeAnimation(AnimationTypes.CastleSelectedAnimation));
                }
            }
        });
    }

    /**
     * Zwraca pole na którym stoi zamek.
     *
     * @return Pole z zamkiem.
     */
    public Field getFieldOfCastleMob() {
        return GameStatus.getInstance().getMap().getFields()[this.getCoordinateXonMap()][this.getCoordinateYonMap()];
    }

    /**
     * Changing owner of castle.
     * @param player Player who takes castle.
     */
    void changeOwner(Player player){
        playerOwner.getCastleMobs().remove(this);
        playerOwner.chceckLoseCondition();
        playerOwner = player;

        playerColorImage.remove();

        playerColorImage = new DefaultPlayerColorIcon(
                player.getPlayerColor(),
                this
        );

        playerColorImage.setSize(Options.tileSize, Options.tileSize);
        playerColorImage.setPosition(this.getX(), this.getY());

        GameStatus.getInstance().getMapStage().addActor(
                this.getPlayerColorImage()
        );

        GameStatus.getInstance().getMap().getFields()[getCoordinateXonMap()][getCoordinateYonMap()].getCastleMob().toFront();
        GameStatus.getInstance().getMap().getFields()[getCoordinateXonMap()][getCoordinateYonMap()].getPlayerMob().toFront();

        if (!playerOwner.getCastleMobs().contains(this)) {
            playerOwner.getCastleMobs().add(this);
        }
    }

    void changeAnimationToUnselected(){
        this.setAnimation(AnimationCreator.getInstance().makeAnimation(AnimationTypes.CastleAnimation));
    }

    public DefaultPlayerColorIcon getPlayerColorImage() {
        return playerColorImage;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Player getPlayerOwner() {
        return playerOwner;
    }
}
