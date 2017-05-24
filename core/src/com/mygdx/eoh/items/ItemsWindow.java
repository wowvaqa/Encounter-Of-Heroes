package com.mygdx.eoh.items;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.mygdx.eoh.Equipment.Equip;
import com.mygdx.eoh.Equipment.EquipKinds;
import com.mygdx.eoh.Equipment.EquipTypes;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.gameClasses.Positioning;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * Items Window
 * Created by wowvaqa on 22.03.17.
 */

public class ItemsWindow {
    private static final ItemsWindow instance = new ItemsWindow();
    public static boolean itemWindowActive = false;

    private ItemsWindow() {
    }

    public static ItemsWindow getInstance() {
        return instance;
    }

    /**
     * Gets window with items.
     *
     * @return Items of selected player mob.
     */
    public Window getItemsWindow(final PlayerMob playerMob) {
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(750, 550);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        Table itemsTable = new Table();
        final Table equipTable = new Table();
        final Table equippedTable = new Table();

        equippedTable.add(new Label("Weapon: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
        equippedTable.add(playerMob.getWeapon()).size(100, 100).pad(5);
        equippedTable.add(new Label("Armor: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
        equippedTable.add(playerMob.getArmor()).size(100, 100).pad(5);
        equippedTable.add(new Label("Artifact: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
        equippedTable.add(playerMob.getArtifact()).size(100, 100).pad(5);

        for (Equip equip : playerMob.getEquip()) {
            equipTable.add(equip).size(100, 100).pad(5).padBottom(25).padTop(25);
        }

        for (Item item : playerMob.getItems()) {
            itemsTable.add(item.getButton()).size(100, 100).pad(5);
            item.getButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ItemsWindow.itemWindowActive = false;
                    window.remove();
                }
            });
        }


        itemsTable.row();

        for (Item item : playerMob.getItems()) {
            itemsTable.add(new Label(item.getItemName(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16")).pad(5);
        }

        TextButton tbCancel = new TextButton("CLOSE", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ItemsWindow.itemWindowActive = false;
                window.remove();
            }
        });

        ScrollPane scrollPane = new ScrollPane(equipTable, AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        window.add(equippedTable);
        window.row();
        window.add(scrollPane);
        window.row();
        window.add(itemsTable).align(Align.bottom);
        window.row();
        window.add(tbCancel).pad(5).size(175, 50);

        DragAndDrop dnd = new DragAndDrop();
        dnd.addSource(new DragAndDrop.Source(equipTable) {
            final DragAndDrop.Payload payload = new DragAndDrop.Payload();

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                payload.setObject(Equip.selectedEquip);

                for (int i = 0; i < playerMob.getEquip().size; i++) {
                    if (payload.getObject() == playerMob.getEquip().get(i)) {
                        Equip.selectedEquipIndex = i;
                        //System.out.println("Indks zaznaczonego ekwipunku: " + i);
                    }
                }

                playerMob.getEquip().removeValue((Equip) payload.getObject(), true);


                if (NetStatus.getInstance().getClient() != null) {
                    Network.EquipRemove equipRemove = new Network.EquipRemove();
                    equipRemove.enemyId = NetStatus.getInstance().getEnemyId();
                    equipRemove.equipIndex = Equip.selectedEquipIndex;
                    equipRemove.locationXofPlayerMob = playerMob.getCoordinateXonMap();
                    equipRemove.locationYofPlayerMob = playerMob.getCoordinateYonMap();
                    NetStatus.getInstance().getClient().sendTCP(equipRemove);
                }

                payload.setDragActor(Equip.selectedEquip.getDragImage());
                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                if (target == null) {
                    playerMob.getEquip().add((Equip) payload.getObject());
                    equipTable.reset();
                    for (Equip equip : playerMob.getEquip()) {
                        equipTable.add(equip).size(100, 100).pad(5).padTop(25).padBottom(25);
                    }

                    if (NetStatus.getInstance().getClient() != null) {
                        Network.EquipAssumeCancel equipAssumeCancel = new Network.EquipAssumeCancel();
                        equipAssumeCancel.enemyId = NetStatus.getInstance().getEnemyId();
                        equipAssumeCancel.locationXofPlayerMob = GameStatus.getInstance().getSelectedPlayerMob().getCoordinateXonMap();
                        equipAssumeCancel.locationYofPlayerMob = GameStatus.getInstance().getSelectedPlayerMob().getCoordinateYonMap();
                        NetStatus.getInstance().getClient().sendTCP(equipAssumeCancel);
                    }
                }
            }
        });

        dnd.addTarget(new DragAndDrop.Target(equippedTable) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                if (((Equip) payload.getObject()).getEquipType().equals(EquipTypes.Weapon)) {

                    if (playerMob.getWeapon().getEquipKind() != EquipKinds.None) {
                        playerMob.getEquip().add(playerMob.getWeapon());
                    }
                    playerMob.setWeapon((Equip) payload.getObject());

                } else if (((Equip) payload.getObject()).getEquipType().equals(EquipTypes.Armor)) {
                    if (playerMob.getArmor().getEquipKind() != EquipKinds.None) {
                        playerMob.getEquip().add(playerMob.getArmor());
                    }
                    playerMob.setArmor((Equip) payload.getObject());
                } else if (((Equip) payload.getObject()).getEquipType().equals(EquipTypes.Artifact)) {
                    if (playerMob.getArtifact().getEquipKind() != EquipKinds.None) {
                        playerMob.getEquip().add(playerMob.getArtifact());
                    }
                    playerMob.setArtifact((Equip) payload.getObject());
                }

                if (NetStatus.getInstance().getClient() != null) {
                    Network.EquipAssume equipAssume = new Network.EquipAssume();
                    equipAssume.enemyId = NetStatus.getInstance().getEnemyId();
                    equipAssume.locationXofPlayerMob = GameStatus.getInstance().getSelectedPlayerMob().getCoordinateXonMap();
                    equipAssume.locationYofPlayerMob = GameStatus.getInstance().getSelectedPlayerMob().getCoordinateYonMap();
                    NetStatus.getInstance().getClient().sendTCP(equipAssume);
                }


                equippedTable.reset();

                equippedTable.add(new Label("Weapon: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
                equippedTable.add(playerMob.getWeapon()).size(100, 100).pad(5);
                equippedTable.add(new Label("Armor: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
                equippedTable.add(playerMob.getArmor()).size(100, 100).pad(5);
                equippedTable.add(new Label("Artifact: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
                equippedTable.add(playerMob.getArtifact()).size(100, 100).pad(5);

                equipTable.reset();
                for (Equip equip : playerMob.getEquip()) {
                    equipTable.add(equip).size(100, 100).pad(5).padBottom(25).padTop(25);
                }

            }
        });
        //window.setTouchable(Touchable.enabled);

        return window;
    }
}
