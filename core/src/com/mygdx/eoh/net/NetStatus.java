package com.mygdx.eoh.net;

import com.mygdx.eoh.defaultClasses.DefaultClient;

import java.util.ArrayList;

/**
 * Status of networking, hold networking varibles.
 * Created by v on 2017-01-29.
 */
public class NetStatus {
    private static NetStatus instance = new NetStatus();

    private DefaultClient client;
    private ArrayList<NetPlayer> netPlayers;
    //Login of enemy form network game.
    private String enemy = null;
    //EnemyID in network game.
    private int enemyId;
    private String netLogin = null;
    //If enemy disconnect from game then TRUE
    private boolean enemyDisconnected = false;
    //If client do not recived information from enemy about game ready then False
    private boolean gameReady = false;
    //If logging process was success then TRUE
    private boolean loginSuccess = false;
    //If logging process was failed then TRUE
    private boolean loginFail = false;
    //If registering process was success then TRUE
    private boolean registerSuccess = false;
    //If registering process was failed then TRUE
    private boolean registerFail = false;
    //If new player mob was create then TRUE
    private boolean newPlayerMob = false;
    //If spell cast recived from server then TRUE
    private boolean spellCastNet;
    //If instant effect recived from server then TRUE
    private boolean instantEffectNet = false;
    //Coordinates where new player mob will be create or where instant effect start
    private int locationXvarible;
    private int locationYvarible;
    //Spell cast net varibles
    private int locationXofSpellCaster;
    private int locationYofSpellCaster;
    private int spellManaCost;
    //Instant effect varibles
    private int locationXofCaster;
    private int locationYofCaster;
    private int locationXofDefender;
    private int locationYofDefender;
    //INT varible to send information from default client class object, Instant effect - damage
    private int intVarible;
    //INT varible (Instant effect - instant effect number)
    private int enumIntVarible;
    //Which class new player mob become.
    private int newPlayerMobClass;
    //Which class new player mob of enemy become.
    private int newPlayerMobEnemyClass;
    //Which player will be current player in net game.
    private int netGamePlayerNumber;
    //Amount of logged player on server.
    private int loggedPlayers;
    //If amount of logged players was changed then TURE
    private boolean loggedPlayersChange = false;
    //If game client is host of network game then TRUE
    private boolean host;
    //If game client isn't host of network game then FALSE
    private boolean guest;
    //If Equip removed from equipment then TURE:
    private boolean equipRemove;
    //Varibles for remove equip
    private int equipRemoveLocXofPlayerMob;
    private int equipRemoveLocYofPlayerMob;
    private int equipIndex;
    //Varibles for cancel assume eqip
    private boolean equipAssumeCancel;
    private int equipAssumeCancelLocX;
    private int equipAssumeCancelLocY;
    //Varibles for equip assume
    private boolean equipAssume;
    private int equipAssumeLocX;
    private int equipAssumeLocY;

    //Statistic of player
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int Rank;

    //If client receive answer with statistic of player then TRUE
    private boolean statisticReceive;

    //When user login from other machine when he is logged then TRUE
    private boolean unLogin = false;

    private NetStatus() {
    }

    public static NetStatus getInstance() {
        return instance;
    }

    public static void setInstance(NetStatus instance) {
        NetStatus.instance = instance;
    }

    /**
     * Send request for player statistic.
     */
    public void playerStatusRequest() {
        if (NetStatus.getInstance().getNetLogin() != null) {
            Network.PlayerStatsRequest playerStatsRequest = new Network.PlayerStatsRequest();
            playerStatsRequest.Login = NetStatus.getInstance().getNetLogin();
            NetStatus.getInstance().getClient().sendTCP(playerStatsRequest);
        }
    }

    public DefaultClient getClient() {
        return client;
    }

    public void setClient(DefaultClient client) {
        this.client = client;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public String getNetLogin() {
        return netLogin;
    }

    public void setNetLogin(String netLogin) {
        this.netLogin = netLogin;
    }

    public ArrayList<NetPlayer> getNetPlayers() {
        return netPlayers;
    }

    public void setNetPlayers(ArrayList<NetPlayer> netPlayers) {
        this.netPlayers = netPlayers;
    }

    public boolean isEnemyDisconnected() {
        return enemyDisconnected;
    }

    public void setEnemyDisconnected(boolean enemyDisconnected) {
        this.enemyDisconnected = enemyDisconnected;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public boolean isLoginUnsuccess() {
        return loginFail;
    }

    public void setLoginUnsuccess(boolean loginFail) {
        this.loginFail = loginFail;
    }

    public boolean isRegisterFail() {
        return registerFail;
    }

    public void setRegisterFail(boolean registerFail) {
        this.registerFail = registerFail;
    }

    public boolean isRegisterSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(boolean registerSuccess) {
        this.registerSuccess = registerSuccess;
    }

    public int getLoggedPlayers() {
        return loggedPlayers;
    }

    public void setLoggedPlayers(int loggedPlayers) {
        this.loggedPlayers = loggedPlayers;
    }

    public boolean getLoggedPlayersChange() {
        return loggedPlayersChange;
    }

    public void setLoggedPlayersChange(boolean loggedPlayersChange) {
        this.loggedPlayersChange = loggedPlayersChange;
    }

    public boolean isUnLogin() {
        return unLogin;
    }

    public void setUnLogin(boolean unLogin) {
        this.unLogin = unLogin;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public boolean isStatisticReceive() {
        return statisticReceive;
    }

    public void setStatisticReceive(boolean statisticReceive) {
        this.statisticReceive = statisticReceive;
    }

    public boolean isNewPlayerMob() {
        return newPlayerMob;
    }

    public void setNewPlayerMob(boolean newPlayerMob) {
        this.newPlayerMob = newPlayerMob;
    }

    public int getNewPlayerMobClass() {
        return newPlayerMobClass;
    }

    public void setNewPlayerMobClass(int newPlayerMobClass) {
        this.newPlayerMobClass = newPlayerMobClass;
    }

    public int getNewPlayerMobEnemyClass() {
        return newPlayerMobEnemyClass;
    }

    public void setNewPlayerMobEnemyClass(int newPlayerMobEnemyClass) {
        this.newPlayerMobEnemyClass = newPlayerMobEnemyClass;
    }

    public int getNetGamePlayerNumber() {
        return netGamePlayerNumber;
    }

    public void setNetGamePlayerNumber(int netGamePlayerNumber) {
        this.netGamePlayerNumber = netGamePlayerNumber;
    }

    public boolean isInstantEffectNet() {
        return instantEffectNet;
    }

    public void setInstantEffectNet(boolean instantEffectNet) {
        this.instantEffectNet = instantEffectNet;
    }

    public int getIntVarible() {
        return intVarible;
    }

    public void setIntVarible(int intVarible) {
        this.intVarible = intVarible;
    }

    public int getEnumIntVarible() {
        return enumIntVarible;
    }

    public void setEnumIntVarible(int enumIntVarible) {
        this.enumIntVarible = enumIntVarible;
    }

    public int getLocationXvarible() {
        return locationXvarible;
    }

    public void setLocationXvarible(int locationXvarible) {
        this.locationXvarible = locationXvarible;
    }

    public int getLocationYvarible() {
        return locationYvarible;
    }

    public void setLocationYvarible(int locationYvarible) {
        this.locationYvarible = locationYvarible;
    }

    public boolean isSpellCastNet() {
        return spellCastNet;
    }

    public void setSpellCastNet(boolean spellCastNet) {
        this.spellCastNet = spellCastNet;
    }

    public boolean isGameReady() {
        return gameReady;
    }

    public void setGameReady(boolean gameReady) {
        this.gameReady = gameReady;
    }

    public int getLocationXofSpellCaster() {
        return locationXofSpellCaster;
    }

    public void setLocationXofSpellCaster(int locationXofSpellCaster) {
        this.locationXofSpellCaster = locationXofSpellCaster;
    }

    public int getLocationYofSpellCaster() {
        return locationYofSpellCaster;
    }

    public void setLocationYofSpellCaster(int locationYofSpellCaster) {
        this.locationYofSpellCaster = locationYofSpellCaster;
    }

    public int getSpellManaCost() {
        return spellManaCost;
    }

    public void setSpellManaCost(int spellManaCost) {
        this.spellManaCost = spellManaCost;
    }

    public int getLocationXofCaster() {
        return locationXofCaster;
    }

    public void setLocationXofCaster(int locationXofCaster) {
        this.locationXofCaster = locationXofCaster;
    }

    public int getLocationXofDefender() {
        return locationXofDefender;
    }

    public void setLocationXofDefender(int locationXofDefender) {
        this.locationXofDefender = locationXofDefender;
    }

    public int getLocationYofCaster() {
        return locationYofCaster;
    }

    public void setLocationYofCaster(int locationYofCaster) {
        this.locationYofCaster = locationYofCaster;
    }

    public int getLocationYofDefender() {
        return locationYofDefender;
    }

    public void setLocationYofDefender(int locationYofDefender) {
        this.locationYofDefender = locationYofDefender;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public boolean isEquipRemove() {
        return equipRemove;
    }

    public void setEquipRemove(boolean equipRemove) {
        this.equipRemove = equipRemove;
    }

    public int getEquipRemoveLocXofPlayerMob() {
        return equipRemoveLocXofPlayerMob;
    }

    public void setEquipRemoveLocXofPlayerMob(int equipRemoveLocXofPlayerMob) {
        this.equipRemoveLocXofPlayerMob = equipRemoveLocXofPlayerMob;
    }

    public int getEquipRemoveLocYofPlayerMob() {
        return equipRemoveLocYofPlayerMob;
    }

    public void setEquipRemoveLocYofPlayerMob(int equipRemoveLocYofPlayerMob) {
        this.equipRemoveLocYofPlayerMob = equipRemoveLocYofPlayerMob;
    }

    public int getEquipIndex() {
        return equipIndex;
    }

    public void setEquipIndex(int equipIndex) {
        this.equipIndex = equipIndex;
    }

    public boolean isEquipAssumeCancel() {
        return equipAssumeCancel;
    }

    public void setEquipAssumeCancel(boolean equipAssumeCancel) {
        this.equipAssumeCancel = equipAssumeCancel;
    }

    public int getEquipAssumeCancelLocX() {
        return equipAssumeCancelLocX;
    }

    public void setEquipAssumeCancelLocX(int equipAssumeCancelLocX) {
        this.equipAssumeCancelLocX = equipAssumeCancelLocX;
    }

    public int getEquipAssumeCancelLocY() {
        return equipAssumeCancelLocY;
    }

    public void setEquipAssumeCancelLocY(int equipAssumeCancelLocY) {
        this.equipAssumeCancelLocY = equipAssumeCancelLocY;
    }

    public boolean isEquipAssume() {
        return equipAssume;
    }

    public void setEquipAssume(boolean equipAssume) {
        this.equipAssume = equipAssume;
    }

    public int getEquipAssumeLocX() {
        return equipAssumeLocX;
    }

    public void setEquipAssumeLocX(int equipAssumeLocX) {
        this.equipAssumeLocX = equipAssumeLocX;
    }

    public int getEquipAssumeLocY() {
        return equipAssumeLocY;
    }

    public void setEquipAssumeLocY(int equipAssumeLocY) {
        this.equipAssumeLocY = equipAssumeLocY;
    }
}
