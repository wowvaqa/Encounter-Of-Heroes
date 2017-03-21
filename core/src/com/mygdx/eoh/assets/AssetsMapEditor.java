package com.mygdx.eoh.assets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.defaultClasses.DefaultAssets;

import java.util.ArrayList;

/**
 * Class is used for loading and managing resoursces.
 * Created by v on 2016-09-28.
 */
public class AssetsMapEditor extends DefaultAssets {

    private static AssetsMapEditor instance;
    /**
     * Array of pixmaps to delete, when Assets will be disposed.
     */
    private ArrayList<Pixmap> pixmaps;

    private AssetsMapEditor() {
        pixmaps = new ArrayList<Pixmap>();
    }

    public static AssetsMapEditor getInstance() {
        if (instance == null) {
            instance = new AssetsMapEditor();
        }
        return instance;
    }

    /**
     * Load testures into the asset manager which parametrs.
     */
    @Override
    public void createAssets() {
        super.createAssets();

        getManager().load("styles/skin.json", Skin.class);
        /*******************************************************************************************
         * TERRAINS
         ******************************************************************************************/
        getManager().load("game/terrains/terrain.atlas", TextureAtlas.class);
        /*******************************************************************************************
         * MAIN INTERFACE
         ******************************************************************************************/
        getManager().load("mapEditor/interface/newButtonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/newButtonDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/loadButtonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/loadButtonDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/saveButtonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/saveButtonDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/exitButtonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/exitButtonDown.png", Texture.class, getParameter());
        /*******************************************************************************************
         * BRUSHES BUTTONS
         ******************************************************************************************/
        getManager().load("mapEditor/interface/brushWindow/terrainBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/terrainBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/mobsBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/mobsBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/mixtureBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/mixtureBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/playerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/playerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/buldingBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/buldingBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/tresureBoxBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/tresureBoxBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/rubberButtonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/rubberButtonDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/cancelBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushWindow/cancelBrushDown.png", Texture.class, getParameter());
        /*******************************************************************************************
         * PLAYER BUTTONS
         ******************************************************************************************/
        getManager().load("mapEditor/interface/playerWindow/player01BrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player01BrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player02BrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player02BrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player03BrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player03BrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player04BrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/playerWindow/player04BrushDown.png", Texture.class, getParameter());
        /*******************************************************************************************
         * TERRAINS
         ******************************************************************************************/
        getManager().load("mapEditor/terrains/grass.png", Texture.class, getParameter());
        getManager().load("mapEditor/terrains/forestC.png", Texture.class, getParameter());
        getManager().load("mapEditor/terrains/mountainC.png", Texture.class, getParameter());
        getManager().load("mapEditor/terrains/river.png", Texture.class, getParameter());
        /*******************************************************************************************
         * TERRAINS BUTTONS
         ******************************************************************************************/
        getManager().load("mapEditor/interface/terrainWindow/forestBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/forestBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/mountainBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/mountainBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/riverBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/riverBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/grassBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/terrainWindow/grassBrushDown.png", Texture.class, getParameter());
        /*******************************************************************************************
         * TRESURE BOX BUTTONS & GOLD
         ******************************************************************************************/
        getManager().load("mapEditor/interface/tresureBoxWindow/tresureBoxLvl1BrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/tresureBoxWindow/tresureBoxLvl1BrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/tresureBoxWindow/tresureBoxLvl2BrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/tresureBoxWindow/tresureBoxLvl2BrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/tresureBoxWindow/goldBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/tresureBoxWindow/goldBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/items/goldCoins.png", Texture.class, getParameter());
        getManager().load("mapEditor/mixtures/healthPotion.png", Texture.class, getParameter());
        getManager().load("mapEditor/mixtures/manaPotion.png", Texture.class, getParameter());
        /*******************************************************************************************
         * BULDING BUTTONS
         ******************************************************************************************/
        getManager().load("mapEditor/interface/buldingsWindow/attackTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/attackTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/defenceTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/defenceTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/hospitalTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/hospitalTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/hpTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/hpTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/magicTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/magicTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/speedTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/speedTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/wellTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/wellTowerBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/wisdomTowerBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/buldingsWindow/wisdomTowerBrushDown.png", Texture.class, getParameter());
        /*******************************************************************************************
         * MOBS BUTTONS
         ******************************************************************************************/
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushLvl1Up.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushLvl1Down.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushLvl2Up.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushLvl2Down.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushRandomUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushRandomDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushSkeletonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushSkeletonDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushSpiderUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushSpiderDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushWolfUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushWolfDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushZombieUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mobsWindow/mobsBrushZombieDown.png", Texture.class, getParameter());

        getManager().load("mapEditor/players/player1.png", Texture.class, getParameter());
        getManager().load("mapEditor/players/player2.png", Texture.class, getParameter());
        getManager().load("mapEditor/players/player3.png", Texture.class, getParameter());
        getManager().load("mapEditor/players/player4.png", Texture.class, getParameter());

        getManager().load("mapEditor/boxes/texTresureBoxLvl1.png", Texture.class, getParameter());
        getManager().load("mapEditor/boxes/texTresureBoxLvl2.png", Texture.class, getParameter());

        getManager().load("mapEditor/buldings/towerOfMagic.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/towerOfWisdom.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/towerOfDefence.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/towerOfSpeed.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/towerOfAttack.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/towerOfHp.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/well.png", Texture.class, getParameter());
        getManager().load("mapEditor/buldings/hospital.png", Texture.class, getParameter());

        getManager().load("mapEditor/mobs/mobSpider.png", Texture.class, getParameter());
        getManager().load("mapEditor/mobs/mobSkeleton.png", Texture.class, getParameter());
        getManager().load("mapEditor/mobs/mobWolf.png", Texture.class, getParameter());
        getManager().load("mapEditor/mobs/mobZombie.png", Texture.class, getParameter());
        getManager().load("mapEditor/mobs/mobRandomLevel1.png", Texture.class, getParameter());
        getManager().load("mapEditor/mobs/mobRandomLevel2.png", Texture.class, getParameter());

        getManager().load("mapEditor/background/frameLeft.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameRight.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameDown.png", Texture.class, getParameter());


        getManager().load("mapEditor/background/frameRightDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameLeftDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameLeftUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/background/frameRightUp.png", Texture.class, getParameter());

        getManager().load("mapEditor/background/starTile.png", Texture.class, getParameter());

        getManager().load("mapEditor/interface/cancelBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/cancelBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushButtonUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/brushButtonDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/zoomButtonOutDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/zoomButtonOutUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/zoomButtonInDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/zoomButtonInUp.png", Texture.class, getParameter());
        /*******************************************************************************************
         * MIXTURES BUTTONS
         ******************************************************************************************/
        getManager().load("mapEditor/interface/mixtureWindow/healthPotionBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mixtureWindow/healthPotionBrushDown.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mixtureWindow/manaPotionBrushUp.png", Texture.class, getParameter());
        getManager().load("mapEditor/interface/mixtureWindow/manaPotionBrushDown.png", Texture.class, getParameter());

        getManager().finishLoading();
    }

    /**
     * Disposing resources
     */
    @Override
    public void dispose() {
        super.dispose();
        disposePixmaps();
        instance = null;
    }

    /**
     * Disposing Pixmaps
     */
    public void disposePixmaps() {
        while (pixmaps.size() != 0) {
            pixmaps.get(pixmaps.size() - 1).dispose();
            pixmaps.remove(pixmaps.size() - 1);
        }
    }

    /**
     * Get access to pixmap arraylist.
     *
     * @return ArrayList of Pixmaps
     */
//    public ArrayList<Pixmap> getPixmaps() {
//        return pixmaps;
//    }
}
