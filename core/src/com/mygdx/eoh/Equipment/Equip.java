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
import com.mygdx.eoh.effects.EquipModificator;
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
    // Kind of equip (wooden stick, swork etc.)
    private EquipKinds equipKind;
    // Image that will be displayed when dragging
    private Image dragImage;
    // Description of item.
    private String description;
    // Equipment modificators of player mob statistics
    private SnapshotArray<EquipModificator> equipModificators;

    private Equip(Texture texture) {
        super(texture);
        equipModificators = new SnapshotArray<EquipModificator>();
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
                equip.equipModificators.add(EquipModificator.createEquipModificator(equipKind));
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
        Label labelAtc = new Label("" + EquipModificator.getAttackModificators(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).pad(5).size(50, 50);
        window.add(labelAtc);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/defenceIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelDef = new Label("" + EquipModificator.getDefenceModificators(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelDef);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/speedIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelSpd = new Label("" + EquipModificator.getSpeedModificators(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelSpd);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/powerIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelPwr = new Label("" + EquipModificator.getPowerModificators(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelPwr);
        window.row();

        window.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/wisdomIcon.png", Texture.class))).pad(5).size(50, 50);
        Label labelWsd = new Label("" + EquipModificator.getWisdomModificators(equip), (Skin) AssetsGameInterface.getInstance().getManager().get("styles/skin.json"));
        window.add(labelWsd);
        window.row();

        window.add(textButton).pad(5).colspan(2).size(100, 50);

        Positioning.setWindowToCenter(window);

        return window;
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

    public SnapshotArray<EquipModificator> getEquipModificators() {
        return equipModificators;
    }
}
