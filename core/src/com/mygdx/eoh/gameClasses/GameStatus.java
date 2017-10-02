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
    private static GameStatus instance = new GameStatus();
    // All Fields
    ArrayList<Field> fields;
    // Jeżeli TRUE gra jest sieciowa, jeżeli FALSE - gra single
    private boolean netGame = false;
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
    // Needed to create new single game.
    private PlayerMobClasses singleGamePlayerOneMobClass = PlayerMobClasses.Knight;
    private PlayerMobClasses singleGamePlayerTwoMobClass = PlayerMobClasses.Knight;
    // Określa czy dany gracz jest sterowany przez AI
    private boolean playerOneAI = false;
    private boolean playerTwoAI = true;
    // Określa opóźnienie w wykonaniu czynności przez AI
    private float playerOneDifficultyTime = 0.5f;
    private float playerTwoDifficultyTime = 0.5f;
    // MainStage
    private Stage mainStage;
    // MapStage
    private Stage mapStage;
    private DefaultCamera camera;

    private GameStatus() {
        moveButtons = new SnapshotArray<MoveButton>();
        attackButtons = new SnapshotArray<AttackButton>();
        castButtons = new SnapshotArray<CastButton>();
    }

    public static GameStatus getInstance() {
        return instance;
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

    public void setUpperBarTable(Table upperBarTable) {
        this.upperBarTable = upperBarTable;
    }

    public Table getUpperBarRightTable() {
        return upperBarRightTable;
    }

    public void setUpperBarRightTable(Table upperBarRightTable) {
        this.upperBarRightTable = upperBarRightTable;
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

    public Player getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(Player currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
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

    public PlayerMobClasses getSingleGamePlayerOneMobClass() {
        return singleGamePlayerOneMobClass;
    }

    public void setSingleGamePlayerOneMobClass(PlayerMobClasses singleGamePlayerOneMobClass) {
        this.singleGamePlayerOneMobClass = singleGamePlayerOneMobClass;
    }

    public PlayerMobClasses getSingleGamePlayerTwoMobClass() {
        return singleGamePlayerTwoMobClass;
    }

    public void setSingleGamePlayerTwoMobClass(PlayerMobClasses singleGamePlayerTwoMobClass) {
        this.singleGamePlayerTwoMobClass = singleGamePlayerTwoMobClass;
    }

    public boolean isPlayerOneAI() {
        return playerOneAI;
    }

    public void setPlayerOneAI(boolean playerOneAI) {
        this.playerOneAI = playerOneAI;
    }

    public boolean isPlayerTwoAI() {
        return playerTwoAI;
    }

    public void setPlayerTwoAI(boolean playerTwoAI) {
        this.playerTwoAI = playerTwoAI;
    }

    public float getPlayerOneDifficultyTime() {
        playerOneDifficultyTime += 0.01f;
        return playerOneDifficultyTime;
    }

    public void setPlayerOneDifficultyTime(float playerOneDifficultyTime) {
        this.playerOneDifficultyTime = playerOneDifficultyTime;
    }

    public float getPlayerTwoDifficultyTime() {
        playerTwoDifficultyTime += 0.01f;
        return playerTwoDifficultyTime;
    }

    public void setPlayerTwoDifficultyTime(float playerTwoDifficultyTime) {
        this.playerTwoDifficultyTime = playerTwoDifficultyTime;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public boolean isNetGame() {
        return netGame;
    }

    public void setNetGame(boolean netGame) {
        this.netGame = netGame;
    }
}
