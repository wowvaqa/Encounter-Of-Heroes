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
        setZIndex(5);
    }

    public DefaultPlayerColorIcon(TextureRegion region, CastleMob castleMobParent) {
        super(region);
        this.castleMobParent = castleMobParent;
        setZIndex(5);
    }

    public PlayerMob getPlayerMobParent() {
        return playerMobParent;
    }

    public void setPlayerMobParent(PlayerMob playerMobParent) {
        this.playerMobParent = playerMobParent;
    }
}
