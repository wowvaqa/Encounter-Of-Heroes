package com.mygdx.eoh.Equipment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.gameClasses.GameStatus;

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
    // Description of item.
    private String description;

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
                equip.description = "Drewniany kij";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/woodStickIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;
            case LeatherPants:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/leatherPantsIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.LeatherPants;
                equip.equipType = EquipTypes.Armor;
                equip.description = "Skórzane spodnie";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/leatherPantsIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;
            case GoldRing:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/goldRingIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.GoldRing;
                equip.equipType = EquipTypes.Artifact;
                equip.description = "Złoty pierścień";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/goldRingIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;
            case MagicStaff:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/magicStaffIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.MagicStaff;
                equip.equipType = EquipTypes.Weapon;
                equip.description = "Magiczna laska";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/magicStaffIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;
            case LeatherArmor:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/leatherArmorIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.LeatherArmor;
                equip.equipType = EquipTypes.Armor;
                equip.description = "Skórzana zbroja";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/leatherArmorIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;
            case SphereOfSpeed:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/sphereOfSpeedIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.SphereOfSpeed;
                equip.equipType = EquipTypes.Artifact;
                equip.description = "Kula szybkości";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/sphereOfSpeedIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
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

        equip.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                        new Dialog("", (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json")) {
                            {
                                text(equip.getDescription());
                                button("OK", "ok");
                            }

                            @Override
                            protected void result(Object object) {
                                super.result(object);
                                if (object.equals("ok")) {
                                }
                            }
                        }.show(GameStatus.getInstance().getMainStage());
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

    public String getDescription() {
        return description;
    }
}
