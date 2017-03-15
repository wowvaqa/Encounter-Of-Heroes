package com.mygdx.eoh.net;

/**
 *
 * Created by v on 2016-12-08.
 */

public class NetPlayer {
    private String playerLogin;
    private int playerId;
    private int playerNumber;

    public NetPlayer(String playerLogin, int playerId, int playerNumber){
        this.playerId = playerId;
        this.playerLogin = playerLogin;
        this.playerNumber = playerNumber;
    }

    public String getPlayerLogin() {
        return playerLogin;
    }

    public void setPlayerLogin(String playerLogin) {
        this.playerLogin = playerLogin;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
