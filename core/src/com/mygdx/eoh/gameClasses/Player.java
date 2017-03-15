package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;
import com.mygdx.eoh.screens.ScreenManager;

import java.util.ArrayList;

/**
 * Player class
 * Created by v on 2016-11-01.
 */
public class Player {
    private ArrayList<PlayerMob> playerMobs;
    private ArrayList<CastleMob> castleMobs;
    private int numberOfPlayer;
    private int inedxOfPlayerInArrayOfPlayer;
    private TextureRegion playerColor;
    private Image playerIcon;
    private int gold;
    private boolean endTurn;
    private Vector3 lastCameraPosition;

    public Player(int numberOfPlayer){
        this.playerMobs = new ArrayList<PlayerMob>();
        this.castleMobs = new ArrayList<CastleMob>();
        this.numberOfPlayer = numberOfPlayer;
        this.inedxOfPlayerInArrayOfPlayer = numberOfPlayer - 1;

        createPlayerColor();
    }

    private void createPlayerColor(){
        switch (numberOfPlayer){
            case 1:
                playerIcon = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/playersIcon/redPlayerIcon.png", Texture.class));
                playerColor = AssetsGameScreen.getInstance().getManager().get("game/players/playersColors.atlas", TextureAtlas.class).findRegion("player1Color");
                break;
            case 2:
                playerIcon = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/playersIcon/bluePlayerIcon.png", Texture.class));
                playerColor = AssetsGameScreen.getInstance().getManager().get("game/players/playersColors.atlas", TextureAtlas.class).findRegion("player2Color");
                break;
            case 3:
                playerIcon = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/playersIcon/greenPlayerIcon.png", Texture.class));
                playerColor = AssetsGameScreen.getInstance().getManager().get("game/players/playersColors.atlas", TextureAtlas.class).findRegion("player3Color");
                break;
            case 4:
                playerIcon = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/playersIcon/yellowPlayerIcon.png", Texture.class));
                playerColor = AssetsGameScreen.getInstance().getManager().get("game/players/playersColors.atlas", TextureAtlas.class).findRegion("player4Color");
                break;
        }
    }

    /**
     * Checking loose condition of player.
     * @return True if player hasn't any castles and PlayerMobs.
     */
    public boolean chceckLoseCondition(){

        if (this.playerMobs.size() == 0 && this.castleMobs.size() == 0){
            System.out.println("Stwierdzono warunki przegranej dla playera: " + this.numberOfPlayer);

            if (this.equals(GameStatus.getInstance().getCurrentPlayerTurn())){
                System.out.println("Przegrałeś :-( :-( :-(");
                if (NetStatus.getInstance().getClient() != null){
                    Network.Victory victory = new Network.Victory();
                    victory.enemyID = NetStatus.getInstance().getEnemyId();
                    NetStatus.getInstance().getClient().sendTCP(victory);

                    Network.ChangePlayerStatistic changePlayerStatistic = new Network.ChangePlayerStatistic();
                    changePlayerStatistic.gamesLost = 1;
                    changePlayerStatistic.gamesPlayed = 1;
                    changePlayerStatistic.gamesWon = 0;
                    changePlayerStatistic.Login = NetStatus.getInstance().getNetLogin();
                    NetStatus.getInstance().getClient().sendTCP(changePlayerStatistic);
                }

                new Dialog("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class)){
                    {
                        text("Porażka");
                        this.row();
                        button("Wyjście", "wyjscie");
                    }
                    @Override
                    protected void result(Object object) {
                        super.result(object);
                        if (object.equals("wyjscie")){
                            this.remove();
                            ScreenManager.getInstance().setScreen(Screens.ScreenNewNetGame);
                        }
                    }
                }.show(GameStatus.getInstance().getMainStage());
            }

            GameStatus.getInstance().getPlayers().remove(this);

            return true;
        }
        return false;
    }

    /**
     *
     * @param amount
     */
    public void riseGold(int amount){
        this.gold += amount;
    }

    /**
     *
     * @param amount
     */
    public void decreaseGold(int amount){
        this.gold -= amount;
    }

    /**
     * Changing amount of gold in label.
     * @param
     */
    public void changeGoldLabel(Player player){
        ((Label) GameStatus.getInstance().getUpperBarTable().getCells().get(2).getActor()).setText("" + player.getGold());
    }

    public ArrayList<PlayerMob> getPlayerMobs() {
        return playerMobs;
    }

    public TextureRegion getPlayerColor() {
        return playerColor;
    }

    public int getInedxOfPlayerInArrayOfPlayer() {
        return inedxOfPlayerInArrayOfPlayer;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public Image getPlayerIcon() {
        return playerIcon;
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public Vector3 getLastCameraPosition() {
        return lastCameraPosition;
    }

    public void setLastCameraPosition(Vector3 lastCameraPosition) {
        this.lastCameraPosition = lastCameraPosition;
    }

    public ArrayList<CastleMob> getCastleMobs() {
        return castleMobs;
    }

    public void setCastleMobs(ArrayList<CastleMob> castleMobs) {
        this.castleMobs = castleMobs;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
