package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.eoh.gameClasses.CastleMob;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 *
 * Created by v on 2016-11-01.
 */
public class DefaultPlayerColorIcon extends Image {

    private PlayerMob playerMobParent;
    private CastleMob castleMobParent;

    public DefaultPlayerColorIcon(TextureRegion region, PlayerMob playerMobParent) {
        super(region);
        this.playerMobParent = playerMobParent;

        this.setSize(32, 32);
        this.setPosition(playerMobParent.getX() + playerMobParent.getWidth() - 50, playerMobParent.getHeight() - 50);
    }

    public DefaultPlayerColorIcon(TextureRegion region, CastleMob castleMobParent) {
        super(region);
        this.castleMobParent = castleMobParent;

        this.setPosition(castleMobParent.getX() + castleMobParent.getWidth() - 50, castleMobParent.getHeight() - 50);

        this.setSize(64, 64);
    }

//    public PlayerMob getPlayerMobParent() {
//        return playerMobParent;
//    }
//
//    public void setPlayerMobParent(PlayerMob playerMobParent) {
//        this.playerMobParent = playerMobParent;
//    }

    private void changePosition() {
        if (playerMobParent != null)
            this.setPosition(playerMobParent.getX() + playerMobParent.getWidth() - 64 , playerMobParent.getY() + playerMobParent.getHeight() - 64);
        if (castleMobParent != null)
            this.setPosition(castleMobParent.getX() + castleMobParent.getWidth() - 64, castleMobParent.getY() + castleMobParent.getHeight() - 64);
        this.setSize(64, 64);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (playerMobParent != null) {
            if (this.getX() != playerMobParent.getX() + playerMobParent.getWidth() - 64)
                changePosition();
            if (this.getY() != playerMobParent.getY() + playerMobParent.getHeight() - 64)
                changePosition();
        }

        if (castleMobParent != null){
            if (this.getX() != castleMobParent.getX() + castleMobParent.getWidth() - 64)
                changePosition();
            if (this.getY() != castleMobParent.getY() + castleMobParent.getHeight() - 64)
                changePosition();
        }
    }
}
