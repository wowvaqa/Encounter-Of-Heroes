package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.defaultClasses.DefaultCamera;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.magic.CastButton;
import com.mygdx.eoh.mapEditor.MapFile;
import com.mygdx.eoh.screens.GameInterface;

import java.util.ArrayList;

/**
 *
 * Created by v on 2016-10-17.
 */
public class GameStatus {
    private SnapshotArray<MoveButton> moveButtons;
    private SnapshotArray<AttackButton> attackButtons;
    private SnapshotArray<CastButton> castButtons;

    private Player currentPlayerTurn;
    private Image currentPlayerIcon;
    private PlayerMob selectedPlayerMob;
    private CastleMob selectedCastleMob;
    private GameInterface gameInterface;

    private Map map;
    // Map from file or network
    private MapFile mapFile;
    private Table equipmentTable;
    private Table playerMobTable;
    private Table castleMobTable;
    private Table heroTable;
    private Table upperBarTable;
    private Table upperBarRightTable;
    private Table spellEffectsTable;
    //ArrayList with all players
    private ArrayList<Player> players;
    //Varible used when new player mob will be created.
    private PlayerMobClasses newPlayerMobClass = PlayerMobClasses.Knight;


    // MainStage
    private Stage mainStage;
    // MapStage
    private Stage mapStage;

    private DefaultCamera camera;

    private static GameStatus instance = new GameStatus();

    public static GameStatus getInstance() {
        return instance;
    }

    private GameStatus() {
        moveButtons = new SnapshotArray<MoveButton>();
        attackButtons = new SnapshotArray<AttackButton>();
        castButtons = new SnapshotArray<CastButton>();
    }

    /**
     * Clears all varibles
     */
    static public void clearVariblesOfGameStatus(){
        GameStatus.getInstance().setCurrentPlayerIcon(null);
        GameStatus.getInstance().setPlayers(null);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Table getSpellEffectsTable() {
        return spellEffectsTable;
    }

    public void setSpellEffectsTable(Table spellEffectsTable) {
        this.spellEffectsTable = spellEffectsTable;
    }

    public Table getEquipmentTable() {
        return equipmentTable;
    }

    public void setEquipmentTable(Table equipmentTable) {
        this.equipmentTable = equipmentTable;
    }

    public Table getPlayerMobTable() {
        return playerMobTable;
    }

    public void setPlayerMobTable(Table playerMobTable) {
        this.playerMobTable = playerMobTable;
    }

    public Table getCastleMobTable() {
        return castleMobTable;
    }

    public void setCastleMobTable(Table castleMobTable) {
        this.castleMobTable = castleMobTable;
    }

    public Table getHeroTable() {
        return heroTable;
    }

    public void setHeroTable(Table heroTable) {
        this.heroTable = heroTable;
    }

    public Table getUpperBarTable() {
        return upperBarTable;
    }

    public Table getUpperBarRightTable() {
        return upperBarRightTable;
    }

    public void setUpperBarRightTable(Table upperBarRightTable) {
        this.upperBarRightTable = upperBarRightTable;
    }

    public void setUpperBarTable(Table upperBarTable) {
        this.upperBarTable = upperBarTable;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public MapFile getMapFile() {
        return mapFile;
    }

    public void setMapFile(MapFile mapFile) {
        this.mapFile = mapFile;
    }

    public void setCurrentPlayerTurn(Player currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public Player getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public GameInterface getGameInterface() {
        return gameInterface;
    }

    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }

    public Image getCurrentPlayerIcon() {
        return currentPlayerIcon;
    }

    public void setCurrentPlayerIcon(Image currentPlayerIcon) {
        this.currentPlayerIcon = currentPlayerIcon;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public Stage getMapStage() {
        return mapStage;
    }

    public void setMapStage(Stage mapStage) {
        this.mapStage = mapStage;
    }

    public DefaultCamera getCamera() {
        return camera;
    }

    public void setCamera(DefaultCamera camera) {
        this.camera = camera;
    }

    public SnapshotArray<MoveButton> getMoveButtons() {
        return moveButtons;
    }

    public void setMoveButtons(SnapshotArray<MoveButton> moveButtons) {
        this.moveButtons = moveButtons;
    }

    public SnapshotArray<AttackButton> getAttackButtons() {
        return attackButtons;
    }

    public void setAttackButtons(SnapshotArray<AttackButton> attackButtons) {
        this.attackButtons = attackButtons;
    }

    public SnapshotArray<CastButton> getCastButtons() {
        return castButtons;
    }

    public PlayerMob getSelectedPlayerMob() {
        return selectedPlayerMob;
    }

    public void setSelectedPlayerMob(PlayerMob selectedPlayerMob) {
        this.selectedPlayerMob = selectedPlayerMob;
    }

    public CastleMob getSelectedCastleMob() {
        return selectedCastleMob;
    }

    public void setSelectedCastleMob(CastleMob selectedCastleMob) {
        this.selectedCastleMob = selectedCastleMob;
    }

    public PlayerMobClasses getNewPlayerMobClass() {
        return newPlayerMobClass;
    }

    public void setNewPlayerMobClass(PlayerMobClasses newPlayerMobClass) {
        this.newPlayerMobClass = newPlayerMobClass;
    }
}
