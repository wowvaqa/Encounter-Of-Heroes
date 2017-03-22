package com.mygdx.eoh.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.defaultClasses.DefaultAssets;

/**
 * Game Screen Assets
 * Created by v on 2016-10-12.
 */
public class AssetsGameScreen extends DefaultAssets {

    private static AssetsGameScreen instance;

    private AssetsGameScreen() {
    }

    public static AssetsGameScreen getInstance() {
        if (instance == null) {
            instance = new AssetsGameScreen();
        }
        return instance;
    }

    @Override
    public void createAssets() {
        super.createAssets();

        getManager().load("styles/skin.json", Skin.class);
        getManager().load("game/terrains/terrain.atlas", TextureAtlas.class);
        getManager().load("mapEditor/background/starTile.png", Texture.class);

        /*******************************************************************************************
         * Animaton of interface elements
         ******************************************************************************************/
        getManager().load("game/interface/buttonsAnimation/arrowN.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowNE.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowE.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowSE.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowS.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowSW.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowW.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/arrowNW.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/cancelMove.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/attackMove.atlas", TextureAtlas.class);
        getManager().load("game/interface/buttonsAnimation/castMove.atlas", TextureAtlas.class);

        /*******************************************************************************************
         * Frames around map
         ******************************************************************************************/
        getManager().load("mapEditor/background/frameLeft.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameRight.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameRightDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameLeftDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameLeftUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameRightUp.png", Texture.class, getParameter());

        /*******************************************************************************************
         * Animations of bulding.
         ******************************************************************************************/
        getManager().load("game/buldingAnimations/hospitalAnimation.png", Texture.class, getParameter());

        /*******************************************************************************************
         * Animations of mobs.
         ******************************************************************************************/
        // KNIGHT
        getManager().load("game/mobsAnimations/knightAnimations/knightStanding.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightSelected.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightCast.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightWalkN.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightWalkS.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightWalkW.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightWalkE.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightAttackN.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightAttackS.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightAttackE.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/knightAnimations/knightAttackW.atlas", TextureAtlas.class);
        // WIZARD
        getManager().load("game/mobsAnimations/wizardAnimations/wizardStanding.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardSelected.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardWalkN.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardWalkS.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardWalkW.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardWalkE.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardAttackN.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardAttackS.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardAttackE.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/wizardAnimations/wizardAttackW.atlas", TextureAtlas.class);

        /*******************************************************************************************
         * Castle animation.
         ******************************************************************************************/
        getManager().load("game/mobsAnimations/castleAnimations/castle.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/castleAnimations/castleSelected.atlas", TextureAtlas.class);

        /*******************************************************************************************
         * Frames around map
         ******************************************************************************************/
        getManager().load("game/players/playersColors.atlas", TextureAtlas.class);

        /*******************************************************************************************
         * Players Icons
         ******************************************************************************************/
        getManager().load("game/interface/playersIcon/bluePlayerIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/playersIcon/greenPlayerIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/playersIcon/redPlayerIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/playersIcon/yellowPlayerIcon.png", Texture.class, getParameter());

        /*******************************************************************************************
         * Players Icons of HP, AP and MANA
         ******************************************************************************************/
        getManager().load("game/interface/apIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/hpIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/manaIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/attackIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/defenceIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/powerIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/speedIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/wisdomIcon.png", Texture.class, getParameter());

        /*******************************************************************************************
         * Spells animations
         ******************************************************************************************/
        getManager().load("game/spellsAnimations/fireballAnimation.atlas", TextureAtlas.class);
        getManager().load("game/spellsAnimations/attackUpgradeAnimation.atlas", TextureAtlas.class);

        /*******************************************************************************************
         * Hex grid graphics
         ******************************************************************************************/
        getManager().load("game/map/hexGrid.png", Texture.class, getParameter());
        /*******************************************************************************************
         * AP & MANA bar animation
         ******************************************************************************************/
        getManager().load("game/mobsAnimations/apBarAnimation/apBarAnimation.atlas", TextureAtlas.class);
        getManager().load("game/mobsAnimations/manaBarAnimation/manaBarAnimation.atlas", TextureAtlas.class);
        /*******************************************************************************************
         * Items
         ******************************************************************************************/
        getManager().load("game/items/gold/itemGold.atlas", TextureAtlas.class);
        /*******************************************************************************************
         * Mixtures
         ******************************************************************************************/
        getManager().load("game/items/mixtures/healthPotion.atlas", TextureAtlas.class);
        getManager().load("game/items/mixtures/manaPotion.atlas", TextureAtlas.class);
        getManager().load("game/items/mixtures/potionUseAnimation.atlas", TextureAtlas.class);

        getManager().finishLoading();
    }

    @Override
    public void dispose() {
        super.dispose();
        instance = null;
    }
}
