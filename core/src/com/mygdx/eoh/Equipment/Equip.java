package com.mygdx.eoh.Equipment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.effects.EquipModifier;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Positioning;

/**
 * Class for equip
 * Created by wowvaqa on 24.03.17.
 */

public class Equip extends Image {

    public static Equip selectedEquip;
    public static int selectedEquipIndex;
    // Type of equip (armor, weapon etc.)
    private EquipTypes equipType;
    // Kind of equip (wooden stick, sword etc.)
    private EquipKinds equipKind;
    // Image that will be displayed when dragging
    private Image dragImage;
    // Description of item.
    private String description;
    // Equipment modifiers of player mob statistics
    private SnapshotArray<EquipModifier> equipModifiers;

    private Equip(Texture texture) {
        super(texture);
        equipModifiers = new SnapshotArray<EquipModifier>();
    }

    /**
     * For create new equip object
     *
     * @return new equip object
     */
    public static Equip createEquip(EquipKinds equipKind) {

        final Equip equip;

        switch (equipKind) {
            case WoodenStick:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/woodStickIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.WoodenStick;
                equip.equipType = EquipTypes.Weapon;
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "WOODEN STICK";
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
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "LEATHER PANTS";
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
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "GOLDEN RING";
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
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "MAGIC STAFF";
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
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "LEATHER ARMOR";
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
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "SPHERE OF SPEED";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/sphereOfSpeedIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;

            case Sword:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/swordIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.Sword;
                equip.equipType = EquipTypes.Weapon;
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "SWORD";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/swordIcon.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;

            case GreenStoneStaff:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/greenStoneStaff.png", Texture.class)
                );
                equip.equipKind = EquipKinds.GreenStoneStaff;
                equip.equipType = EquipTypes.Weapon;
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "GREEN STONE STAFF";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/greenStoneStaff.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;

            case SteelArmor:
                equip = new Equip(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/steelArmor.png", Texture.class)
                );
                equip.equipKind = EquipKinds.SteelArmor;
                equip.equipType = EquipTypes.Armor;
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "STEEL ARMOR";
                equip.dragImage = new Image(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/steelArmor.png", Texture.class)
                );
                equip.dragImage.setSize(50, 50);
                break;

            default:
                equip = new Equip(

                        AssetsGameInterface.getInstance().getManager().get("game/interface/equipmentIcons/noneIcon.png", Texture.class)
                );
                equip.equipKind = EquipKinds.None;
                equip.equipType = EquipTypes.None;
                equip.equipModifiers.add(EquipModifier.createEquipModifier(equipKind));
                equip.description = "none";
                break;
        }

        equip.addListener(new DragListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                Equip.selectedEquip = equip;
            }
        });

        equip.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStatus.getInstance().getMainStage().addActor(getDescriptionWindow(equip));
            }
        });
        return equip;
    }

    private static Window getDescriptionWindow(Equip equip) {
        final Window window = new Window("", (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.setSize(600, 600);
        window.setModal(true);
        window.add(new Label("" + equip.getDescription(), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"))).pad(5).colspan(2);

        TextButton textButton = new TextButton("OK", (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));

        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        window.row();
        Label labelAtc = new Label("" + EquipModifier.getAttackModifiers(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).pad(5).size(50, 50);
        window.add(labelAtc);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/defenceIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelDef = new Label("" + EquipModifier.getDefenceModifiers(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelDef);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/speedIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelSpd = new Label("" + EquipModifier.getSpeedModifiers(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelSpd);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/powerIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelPwr = new Label("" + EquipModifier.getPowerModifiers(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelPwr);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/wisdomIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelWsd = new Label("" + EquipModifier.getWisdomModifiers(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelWsd);
        window.row();

        window.add(textButton).pad(5).colspan(2).size(100, 50);

        Positioning.setWindowToCenter(window);

        return window;
    }

    /**
     * Change equip Kind into network understandable equip kind
     *
     * @param equipKinds equip kind to change
     * @return equip kind network number
     */
    public static int getNetworkEquipKind(EquipKinds equipKinds) {
        switch (equipKinds) {
            case WoodenStick:
                return 0;
            case LeatherPants:
                return 1;
            case GoldRing:
                return 2;
            case MagicStaff:
                return 3;
            case LeatherArmor:
                return 4;
            case SphereOfSpeed:
                return 5;
            case Sword:;
                return 6;
            case GreenStoneStaff:
                return 7;
            case SteelArmor:
                return 8;
        }
        return 0;
    }

    /**
     * Change network equip kind into game equip kind
     *
     * @param equipKind equip kind network number
     * @return game equip kind
     */
    public static EquipKinds getEquipKindFromNetwork(int equipKind) {
        switch (equipKind) {
            case 0:
                return EquipKinds.WoodenStick;
            case 1:
                return EquipKinds.LeatherPants;
            case 2:
                return EquipKinds.GoldRing;
            case 3:
                return EquipKinds.MagicStaff;
            case 4:
                return EquipKinds.LeatherArmor;
            case 5:
                return EquipKinds.SphereOfSpeed;
            case 6:
                return EquipKinds.Sword;
            case 7:
                return EquipKinds.GreenStoneStaff;
            case 8:
                return EquipKinds.SteelArmor;
        }
        return EquipKinds.WoodenStick;
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

    public Image getDragImage() {
        return dragImage;
    }

    public String getDescription() {
        return description;
    }

    public SnapshotArray<EquipModifier> getEquipModifiers() {
        return equipModifiers;
    }
}
