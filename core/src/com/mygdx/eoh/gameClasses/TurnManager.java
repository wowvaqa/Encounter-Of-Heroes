package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * Class resposible for change turn in single and multi player game.
 * Created by v on 2016-12-01.
 */
public class TurnManager {
    private static TurnManager instance = new TurnManager();

    public static TurnManager getInstance() {
        return instance;
    }

    private TurnManager() {
    }

    public void nextSingleTurn(){
        //saveCurrentPlayerCameraPosition();
        MoveManager.turnOffSelectedPlayersMobs();
        MoveManager.unselectCastles(GameStatus.getInstance().getMap());
        changePlayerSingleGame();
        //setCameraPosition();
        //showInfoWindow();
    }

    /**
     * Showing info window about next playser turn.
     */
    private void showInfoWindow(){
        Dialog dialog = new Dialog("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "transparet");
        if (GameStatus.getInstance().getCurrentPlayerTurn().getInedxOfPlayerInArrayOfPlayer() == 0)
            dialog.text("Player 1 turn");
        if (GameStatus.getInstance().getCurrentPlayerTurn().getInedxOfPlayerInArrayOfPlayer() == 1)
            dialog.text("Player 2 turn");
        if (GameStatus.getInstance().getCurrentPlayerTurn().getInedxOfPlayerInArrayOfPlayer() == 2)
            dialog.text("Player 3 turn");
        if (GameStatus.getInstance().getCurrentPlayerTurn().getInedxOfPlayerInArrayOfPlayer() == 3)
            dialog.text("Player 4 turn");
        dialog.button("OK");

        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setKeepWithinStage(false);
        dialog.show(GameStatus.getInstance().getMainStage());
    }

    /**
     * Changing player to next in array of players.
     */
    private void changePlayerSingleGame(){
        int sizeOfPlayersArray = GameStatus.getInstance().getPlayers().size();

        for (int i = 0; i < GameStatus.getInstance().getPlayers().size(); i++) {
            if (GameStatus.getInstance().getCurrentPlayerTurn().equals(GameStatus.getInstance().getPlayers().get(sizeOfPlayersArray - 1))) {
                updatePlayerIcon(GameStatus.getInstance().getCurrentPlayerIcon(), GameStatus.getInstance().getPlayers().get(0).getPlayerIcon());
                GameStatus.getInstance().setCurrentPlayerTurn(GameStatus.getInstance().getPlayers().get(0));
                GameStatus.getInstance().setCurrentPlayerIcon(GameStatus.getInstance().getCurrentPlayerTurn().getPlayerIcon());
                break;
            }
            if (GameStatus.getInstance().getCurrentPlayerTurn().equals(GameStatus.getInstance().getPlayers().get(i))) {
                updatePlayerIcon(GameStatus.getInstance().getCurrentPlayerIcon(), GameStatus.getInstance().getPlayers().get(i + 1).getPlayerIcon());
                GameStatus.getInstance().setCurrentPlayerTurn(GameStatus.getInstance().getPlayers().get(i + 1));
                GameStatus.getInstance().setCurrentPlayerIcon(GameStatus.getInstance().getCurrentPlayerTurn().getPlayerIcon());
                break;
            }
        }

        GameStatus.getInstance().getCurrentPlayerTurn().changeGoldLabel(GameStatus.getInstance().getCurrentPlayerTurn());
    }

    /**
     * Upadte player icon in game screen.
     * @param currentImage current Image of Player Icon.
     * @param imageToReplace image of Player Icon to replace.
     */
    private void updatePlayerIcon(Image currentImage, Image imageToReplace){
        for (Cell cell: GameStatus.getInstance().getUpperBarTable().getCells()){
            if (cell.getActor().equals(currentImage)){
                cell.setActor(imageToReplace);
            }
        }
    }

    private void saveCurrentPlayerCameraPosition(){
        GameStatus.getInstance().getCurrentPlayerTurn().setLastCameraPosition(
                GameStatus.getInstance().getCamera().position
        );
    }

    private void setCameraPosition(){
//        GameStatus.getInstance().getCamera().position = GameStatus.getInstance().getCurrentPlayerTurn().getLastCameraPosition();
        if (GameStatus.getInstance().getCurrentPlayerTurn().getLastCameraPosition() != null)
            GameStatus.getInstance().getCamera().position.set(GameStatus.getInstance().getCurrentPlayerTurn().getLastCameraPosition());
            //GameStatus.getInstance().getCamera().translate(GameStatus.getInstance().getCurrentPlayerTurn().getLastCameraPosition());
    }

    public void nextMultiTurn(Player player){
        Network.NextTurn nextTurn = new Network.NextTurn();
        nextTurn.enemyId = NetStatus.getInstance().getEnemyId();
        nextTurn.playerIndex = player.getInedxOfPlayerInArrayOfPlayer();
        NetStatus.getInstance().getClient().sendTCP(nextTurn);
    }
}
