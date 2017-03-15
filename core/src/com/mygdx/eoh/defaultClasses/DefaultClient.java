package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Player;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.mapEditor.MapFile;
import com.mygdx.eoh.net.NetPlayer;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;
import com.mygdx.eoh.screens.ScreenManager;

/**
 *
 * Created by v on 2016-12-19.
 */

public class DefaultClient extends Client {

    public DefaultClient(int writeBufferSize, int objectBufferSize) {
        super(writeBufferSize, objectBufferSize);
        addListeners();
    }

    private void addListeners(){
        this.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);
                if (object instanceof Network.LoginSuccessAnswer) {
                    System.out.println("Recived 'Login success answer'");
                    Network.LoginSuccessAnswer loginSuccessAnswer = (Network.LoginSuccessAnswer) object;
                    boolean success = loginSuccessAnswer.loginSucces;
                    if (success) {
                        Network.login = loginSuccessAnswer.login;
                        //screenNewNetGame.getLblStatusOfLogin().setText("" + Network.login);
                        NetStatus.getInstance().setNetLogin(Network.login);
                        NetStatus.getInstance().setLoginSuccess(true);
                        NetStatus.getInstance().playerStatusRequest();
//                        Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
//                        dialog.text("Logowanie udane");
//                        dialog.button("OK");
//                        dialog.show(screenNewNetGame.getMainStage());
                    } else {
//                        Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
//                        dialog.text("Logowanie nieudane");
//                        dialog.button("OK");
//                        dialog.show(screenNewNetGame.getMainStage());
                        NetStatus.getInstance().setLoginUnsuccess(true);
                    }
                    return;
                }

                if (object instanceof Network.RegisterUserAnswer) {
                    System.out.println("Recived 'RegisterUser success answer'");
                    Network.RegisterUserAnswer registerUserAnswer = (Network.RegisterUserAnswer) object;
                    boolean success = registerUserAnswer.registerSucces;

                    if (success) {
//                        Dialog dialog = new Dialog("Register status", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
//                        dialog.text("Rejestracja pomyślna");
//                        dialog.button("OK");
//                        dialog.show(screenNewNetGame.getMainStage());
                        NetStatus.getInstance().setRegisterSuccess(true);
                    } else {
//                        Dialog dialog = new Dialog("Register status", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
//                        dialog.text("Rejestracja nieudana");
//                        dialog.button("OK");
//                        dialog.show(screenNewNetGame.getMainStage());
                        NetStatus.getInstance().setRegisterFail(true);
                    }
                    return;
                }

                if (object instanceof MapFile) {
                    GameStatus.getInstance().setMapFile((MapFile) object);
                    System.out.println("Recived map");
                    return;
                }

                if (object instanceof Network.FoundEnemy) {
                    System.out.println("Found enemy. Enemy login: " + ((Network.FoundEnemy) object).enemyLogin);
                    System.out.println("Found enemy. EnemyID: " + ((Network.FoundEnemy) object).enemyId);
                    System.out.println("Found enemy. playerNumber: " + ((Network.FoundEnemy) object).playerNumber);
                    System.out.println("Found enemy. player mob class: " + ((Network.FoundEnemy) object).playerMobClass);
                    NetStatus.getInstance().setEnemy(((Network.FoundEnemy) object).enemyLogin);
                    NetStatus.getInstance().setEnemyId(((Network.FoundEnemy) object).enemyId);
                    NetStatus.getInstance().setNewPlayerMobEnemyClass(((Network.FoundEnemy) object).playerMobClass);
                    NetStatus.getInstance().setNetGamePlayerNumber(((Network.FoundEnemy) object).playerNumber);

                    NetStatus.getInstance().getNetPlayers().clear();
                    NetStatus.getInstance().getNetPlayers().add(new NetPlayer(
                            ((Network.FoundEnemy) object).enemyLogin,
                            ((Network.FoundEnemy) object).enemyId,
                            ((Network.FoundEnemy) object).playerNumber
                    ));
                    return;
                }

                if (object instanceof Network.MovePlayerMob) {
                    System.out.println("Enemy ID: " + ((Network.MovePlayerMob) object).enemyId);
                    System.out.println("X: " + ((Network.MovePlayerMob) object).amountXmove);
                    System.out.println("Y: " + ((Network.MovePlayerMob) object).amountYmove);
                    System.out.println("Index of PlayerMob: " + ((Network.MovePlayerMob) object).indexInArray);
                    System.out.println("Index of Player: " + ((Network.MovePlayerMob) object).inedxPlayerOwner);

                    int indexOfPlayer = ((Network.MovePlayerMob) object).inedxPlayerOwner;
                    int indexOfPlayerMob = ((Network.MovePlayerMob) object).indexInArray;

                    Player player = GameStatus.getInstance().getPlayers().get(indexOfPlayer);
                    PlayerMob playerMob = player.getPlayerMobs().get(indexOfPlayerMob);

                    if (((Network.MovePlayerMob) object).amountXmove == 0 && ((Network.MovePlayerMob) object).amountYmove == 1)
                        playerMob.getMoveManager().movePlayerMobNorthRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == 0 && ((Network.MovePlayerMob) object).amountYmove == -1)
                        playerMob.getMoveManager().movePlayerMobSouthRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == -1 && ((Network.MovePlayerMob) object).amountYmove == 0)
                        playerMob.getMoveManager().movePlayerMobWestRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == 1 && ((Network.MovePlayerMob) object).amountYmove == 0)
                        playerMob.getMoveManager().movePlayerMobEastRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == -1 && ((Network.MovePlayerMob) object).amountYmove == 1)
                        playerMob.getMoveManager().movePlayerMobNorthEastRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == 1 && ((Network.MovePlayerMob) object).amountYmove == 1)
                        playerMob.getMoveManager().movePlayerMobNorthWestRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == -1 && ((Network.MovePlayerMob) object).amountYmove == -1)
                        playerMob.getMoveManager().movePlayerMobSouthEastRecivedFromNET(playerMob);
                    if (((Network.MovePlayerMob) object).amountXmove == 1 && ((Network.MovePlayerMob) object).amountYmove == -1)
                        playerMob.getMoveManager().movePlayerMobSouthWestRecivedFromNET(playerMob);


                    for (Player player1: GameStatus.getInstance().getPlayers()) {
                        for (PlayerMob playerMob1: player1.getPlayerMobs()) {
                            playerMob.getMoveManager().redrawButtons(playerMob1.getStage(), playerMob1);
                        }
                    }
                    return;
                }

                if (object instanceof Network.AttackPlayerMob) {
                    System.out.println("Enemy ID: " + ((Network.AttackPlayerMob) object).enemyId);
                    System.out.println("Location X of enemy: " + ((Network.AttackPlayerMob) object).locationXofEnemy);
                    System.out.println("Location Y of enemy: " + ((Network.AttackPlayerMob) object).locationYofEnemy);
                    System.out.println("Location X of attacker: " + ((Network.AttackPlayerMob) object).locationXofAttacker);
                    System.out.println("Location Y of attacker: " + ((Network.AttackPlayerMob) object).locationYofAttacker);
                    System.out.println("Index of PlayerMob: " + ((Network.AttackPlayerMob) object).indexInArray);
                    System.out.println("Index of Player: " + ((Network.AttackPlayerMob) object).indexPlayerOwner);
                    System.out.println("Damage: " + ((Network.AttackPlayerMob) object).damage);
                    System.out.println("HP left: " + ((Network.AttackPlayerMob) object).hpLeft);

                    int locX = ((Network.AttackPlayerMob) object).locationXofEnemy;
                    int locY = ((Network.AttackPlayerMob) object).locationYofEnemy;
                    int locXofAttacker = ((Network.AttackPlayerMob) object).locationXofAttacker;
                    int locYofAttacker = ((Network.AttackPlayerMob) object).locationYofAttacker;

                    GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob().setActualhp(
                            ((Network.AttackPlayerMob) object).hpLeft
                    );

                    if (GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob().getActualhp() < 1){
                        GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob().addFadeOutActionWhenPlayerMobIsDead(
                                GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob()
                        );
                    }

                    GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob().getMoveManager().showDamageLabel(
                            ((Network.AttackPlayerMob) object).damage,
                            locX, locY,
                            GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob().getStage()
                    );

                    GameStatus.getInstance().getMap().getFields()[locX][locY].getPlayerMob().changeToAttackAnimation(
                            GameStatus.getInstance().getMap().getFields()[locXofAttacker][locYofAttacker].getPlayerMob(),
                            locX, locY
                    );

                    FightManager.decreseAP(
                            GameStatus.getInstance().getMap().getFields()[locXofAttacker][locYofAttacker].getPlayerMob(), 1);
                    return;
                }

                if (object instanceof Network.PlayersOnlineAnswer) {
                    System.out.println("Liczba graczy: " + ((Network.PlayersOnlineAnswer) object).amountOfOnlinePlayers);
                    NetStatus.getInstance().setLoggedPlayers(((Network.PlayersOnlineAnswer) object).amountOfOnlinePlayers);
                    NetStatus.getInstance().setLoggedPlayersChange(true);
                    return;
                }

                if (object instanceof Network.DisconnonectedFromBattle){
                    System.out.println("Przeciwnik rozłączył się");
                    NetStatus.getInstance().setEnemyDisconnected(true);
                    return;
                }

                if (object instanceof Network.Victory){
                    System.out.println("Odebrano zgłoszenie wygranej gracza");

                    new Dialog("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class)){
                        {
                            text("Zwycięstwo");
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

                    Network.ChangePlayerStatistic changePlayerStatistic = new Network.ChangePlayerStatistic();
                    changePlayerStatistic.gamesLost = 0;
                    changePlayerStatistic.gamesPlayed = 1;
                    changePlayerStatistic.gamesWon = 1;
                    changePlayerStatistic.Login = NetStatus.getInstance().getNetLogin();
                    NetStatus.getInstance().getClient().sendTCP(changePlayerStatistic);

                    return;
                }

                if (object instanceof Network.Unlog){
                    System.out.println("Odebrano wylogowanie od klienta.");
                    NetStatus.getInstance().setNetLogin(null);
                    NetStatus.getInstance().setUnLogin(true);
                    return;
                }

                /***********************************************************************************
                 * RESPOND WITH STATISTIC OF LOGGED PLAYER
                 **********************************************************************************/
                if (object instanceof Network.PlayerStatsRequest){
                    System.out.println("Otrzymano odpowiedź z statystykami gracza ");

                    NetStatus.getInstance().setGamesPlayed(
                            ((Network.PlayerStatsRequest) object).gamesPlayed
                    );

                    NetStatus.getInstance().setGamesWon(
                            ((Network.PlayerStatsRequest) object).gamesWon
                    );

                    NetStatus.getInstance().setGamesLost(
                            ((Network.PlayerStatsRequest) object).gamesLost
                    );

                    NetStatus.getInstance().setRank(
                            ((Network.PlayerStatsRequest) object).rank
                    );

                    NetStatus.getInstance().setStatisticReceive(true);
                    return;
                }

                /***********************************************************************************
                 * REQUEST FOR CHANGE OF PLAYER STATISTIC
                 ***********************************************************************************/
                if (object instanceof Network.BuyPlayerMob){
                    System.out.println("Otrzymano info o tworzeniu nowego bohatera");
                    System.out.println("Lokacja X zamku na mapie: " + ((Network.BuyPlayerMob)object).locXofCastleOnMap);
                    System.out.println("Lokacja Y zamku na mapie: " + ((Network.BuyPlayerMob)object).locYofCastleOnMap);
                    System.out.println("EnemyID: " + ((Network.BuyPlayerMob)object).enemyID);
                    System.out.println("Enemy class: " + ((Network.BuyPlayerMob)object).enemyClass);

                    NetStatus.getInstance().setLocationXvarible(((Network.BuyPlayerMob)object).locXofCastleOnMap);
                    NetStatus.getInstance().setLocationYvarible(((Network.BuyPlayerMob)object).locYofCastleOnMap);
                    NetStatus.getInstance().setNewPlayerMob(true);
                    NetStatus.getInstance().setNewPlayerMobClass(((Network.BuyPlayerMob)object).enemyClass);

                    return;
                }

                /***********************************************************************************
                 * INSTANT EFFECT
                 **********************************************************************************/
                if (object instanceof Network.InstantEffectNet){
                    System.out.println("Otrzymanie danych z natychmiastowym efektem działania czaru");
                    System.out.println("Lokacja X obiektu dotkniętego efektem: " + ((Network.InstantEffectNet)object).locationXofDefender);
                    System.out.println("Lokacja Y obiektu dotkniętego efektem: " + ((Network.InstantEffectNet)object).locationYofDefender);
                    System.out.println("Lokacja X obiektu rzucającego efekt: " + ((Network.InstantEffectNet)object).locationXofCaster);
                    System.out.println("Lokacja Y obiektu rzucającego efekt: " + ((Network.InstantEffectNet)object).locationYofCaster);
                    System.out.println("EnemyID: " + ((Network.InstantEffectNet)object).enemyId);
                    System.out.println("Numer efektu: " + ((Network.InstantEffectNet)object).instantEffectNumber);

                    NetStatus.getInstance().setLocationXofDefender(((Network.InstantEffectNet)object).locationXofDefender);
                    NetStatus.getInstance().setLocationYofDefender(((Network.InstantEffectNet)object).locationYofDefender);

                    NetStatus.getInstance().setLocationXofCaster(((Network.InstantEffectNet)object).locationXofCaster);
                    NetStatus.getInstance().setLocationYofCaster(((Network.InstantEffectNet)object).locationYofCaster);

                    NetStatus.getInstance().setIntVarible(((Network.InstantEffectNet)object).damage);
                    NetStatus.getInstance().setEnumIntVarible(((Network.InstantEffectNet)object).instantEffectNumber);

                    NetStatus.getInstance().setInstantEffectNet(true);

                    return;
                }

                /***********************************************************************************
                 * SPELL CAST
                 **********************************************************************************/
                if (object instanceof Network.SpellCastNet){
                    System.out.println("Otrzymano rzucenie czaru.");
                    System.out.println("Lokacja X rzucającego czar: " + ((Network.SpellCastNet)object).locationXofCaster);
                    System.out.println("Lokacja Y rzucającego czar: " + ((Network.SpellCastNet)object).locationYofCaster);
                    System.out.println("Koszt czaru: " + ((Network.SpellCastNet)object).spellManaCost);
                    System.out.println("EnemyID: " + ((Network.SpellCastNet)object).enemyId);

                    NetStatus.getInstance().setSpellCastNet(true);
                    NetStatus.getInstance().setLocationXofSpellCaster(((Network.SpellCastNet)object).locationXofCaster);
                    NetStatus.getInstance().setLocationYofSpellCaster(((Network.SpellCastNet)object).locationYofCaster);
                    NetStatus.getInstance().setSpellManaCost(((Network.SpellCastNet)object).spellManaCost);
                }

                /***********************************************************************************
                 * CLIENT READY TO START BATTLE
                 **********************************************************************************/
                if (object instanceof Network.ClientReadyToStartBattle){
                    System.out.println("Otrzymano zgłoszenie gotowości do rozpoczęcia potyczki.");
                    System.out.println("EnemyID: " + ((Network.ClientReadyToStartBattle)object).enemyId);
                    NetStatus.getInstance().setGameReady(true);
                }
            }
        });
    }
}
