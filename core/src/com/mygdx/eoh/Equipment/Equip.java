package com.mygdx.eoh.Equipment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.eoh.assets.AssetsGameInterface;

/**
 * Class for equip
 * Created by wowvaqa on 24.03.17.
 */

public class Equip extends Image {

    // Type of equip (armor, weapon etc.)
    private EquipTypes equipType;
    // Kind of equip (wooden stick, swork etc.)
    private EquipKinds equipKind;
    // If equip is put on then TRUE else FALSE
    private boolean putOn;
    // Image that will be displayed when dragging
    private Image dragImage;

    public static Equip selectedEquip;

    private Equip(Texture texture) {
        super(texture);
    }

    /**
     * For create new equip object
     * @return new equip object
     */
    public static Equip createEquip(EquipKinds equipKind){

        final Equip equip;

        switch (equipKind){
            case WoodenStick:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/woodStickIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.WoodenStick;
                equip.equipType = EquipTypes.Weapon;
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/woodStickIcon.png", Texture.class)
                );
                equip.dragImage.setSize(100, 100);
                break;
            default:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/noneIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.None;
                equip.equipType = EquipTypes.None;
                break;
        }

        equip.addListener(new DragListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                Equip.selectedEquip = equip;
            }
        });
        return equip;
    }

    /***********************************************************************************************
     * GETTERS & SETTERS
     **********************************************************************************************/
    public EquipTypes getEquipType() {
        return equipType;
    }

    public EquipKinds getEquipKind() {
        return equipKind;
    }

    public boolean isPutOn() {
        return putOn;
    }

    public void setPutOn(boolean putOn) {
        this.putOn = putOn;
    }

    public Image getDragImage() {
        return dragImage;
    }
}
