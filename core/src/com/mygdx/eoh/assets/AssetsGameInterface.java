package com.mygdx.eoh.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.defaultClasses.DefaultAssets;

/**
 * Game assets
 * Created by v on 2016-10-13.
 */
public class AssetsGameInterface extends DefaultAssets {

    private static AssetsGameInterface instance;

    private AssetsGameInterface() {
        //super();
        //createAssets();
    }

    public static AssetsGameInterface getInstance() {
        if (instance == null) {
            instance = new AssetsGameInterface();
        }
        return instance;
    }

    @Override
    public void createAssets() {
        super.createAssets();
        getManager().load("styles/skin.json", Skin.class);
        /*******************************************************************************************
         * BUTTONS
         ******************************************************************************************/
        getManager().load("game/interface/buttonBagUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonBagDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonBookUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonBookDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonExitUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonExitDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonHeroUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonHeroDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonNewHeroUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonNewHeroDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonNextTurnUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonNextTurnDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonPromotionUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonPromotionDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonSettingsUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonSettingsDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonZoomInUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonZoomInDown.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonZoomOutUp.png", Texture.class, getParameter());
        getManager().load("game/interface/buttonZoomOutDown.png", Texture.class, getParameter());

        /*******************************************************************************************
         * INTERFACE ELEMENTS
         ******************************************************************************************/
        getManager().load("game/interface/coin.png", Texture.class, getParameter());

        /*******************************************************************************************
         * SPELLS BUTTONS
         ******************************************************************************************/
        getManager().load("game/interface/spellButtons/fireballButtonUP.png", Texture.class, getParameter());
        getManager().load("game/interface/spellButtons/fireballButtonDOWN.png", Texture.class, getParameter());
        getManager().load("game/interface/spellButtons/AttackUpgradeButtonUP.png", Texture.class, getParameter());
        getManager().load("game/interface/spellButtons/AttackUpgradeButtonDOWN.png", Texture.class, getParameter());
        getManager().load("game/interface/spellButtons/CureButtonUP.png", Texture.class, getParameter());
        getManager().load("game/interface/spellButtons/CureButtonDOWN.png", Texture.class, getParameter());
        /*******************************************************************************************
         * SPELLS BUTTONS
         ******************************************************************************************/
        getManager().load("game/interface/mixturesButtons/healthPotionButtonDOWN.png", Texture.class, getParameter());
        getManager().load("game/interface/mixturesButtons/healthPotionButtonUP.png", Texture.class, getParameter());
        getManager().load("game/interface/mixturesButtons/manaPotionButtonDOWN.png", Texture.class, getParameter());
        getManager().load("game/interface/mixturesButtons/manaPotionButtonUP.png", Texture.class, getParameter());

        /*******************************************************************************************
         * EFFECTS ANIMATIONS
         ******************************************************************************************/
        getManager().load("game/interface/spellEffectsAnimations/attackUpgradeEffectIcon.atlas", TextureAtlas.class);

        /*******************************************************************************************
         * EQUIPMENT ICONS
         ******************************************************************************************/
        getManager().load("game/interface/equipmentIcons/noneIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/woodStickIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/leatherPantsIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/goldRingIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/magicStaffIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/leatherArmorIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/sphereOfSpeedIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/swordIcon.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/greenStoneStaff.png", Texture.class, getParameter());
        getManager().load("game/interface/equipmentIcons/steelArmor.png", Texture.class, getParameter());


        getManager().finishLoading();
    }

    @Override
    public void dispose() {
        super.dispose();
        instance = null;
    }
}
