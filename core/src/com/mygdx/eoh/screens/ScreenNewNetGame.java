package com.mygdx.eoh.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.eoh.assets.AssetsMainMenu;
import com.mygdx.eoh.defaultClasses.DefaultClient;
import com.mygdx.eoh.defaultClasses.DefaultScreen;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Positioning;
import com.mygdx.eoh.net.NetPlayer;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by v on 2016-11-14.
 */
public class ScreenNewNetGame extends DefaultScreen {

    //public Label labelAmountOfOnlinePlayers;
    //private Label lblStatusOfLogin;
    private int requiredNumberOfPlayers = -1;
    private Interface interfaceManager;
    private Client client;

    ScreenNewNetGame() {
        interfaceManager = new Interface();

        runClient();

        Table upperTable = new Table();
        Table middleTable = new Table();
        Table bottomTable = new Table();
        Table playerTable = new Table();

        upperTable.row();
        upperTable.add(interfaceManager.tbLogin).pad(5).size(175, 50);
        upperTable.add(interfaceManager.tbRegister).pad(5).size(175, 50);

        middleTable.add(interfaceManager.lblPlayerMobClass).pad(5);
        middleTable.row();
        middleTable.add(interfaceManager.tbNextClass).padBottom(25);
        middleTable.row();
        middleTable.add(interfaceManager.tbPlay).pad(5).size(300, 100);

        bottomTable.add(interfaceManager.tbExit).pad(5).size(175, 50).align(Align.bottom).colspan(5);
        bottomTable.row();
        bottomTable.add(interfaceManager.lblServerStatus).pad(5).align(Align.left);
        bottomTable.add(interfaceManager.lblOnlinePlayers).pad(5).align(Align.left);
        bottomTable.add(interfaceManager.lblOnlinePlayersAmount).pad(5).padRight(25).align(Align.left);
        bottomTable.add(interfaceManager.lblLoginStatusDiscription).pad(5).align(Align.left);
        bottomTable.add(interfaceManager.lblLoginStatus).pad(5).align(Align.left);

        playerTable.add(interfaceManager.lblgamesPlayed).padRight(20);
        playerTable.add(interfaceManager.lblgamesWon).padRight(20);
        playerTable.add(interfaceManager.lblgamesLost).padRight(20);
        playerTable.add(interfaceManager.lblrank).padRight(20);

        super.getMainTable().add(upperTable).align(Align.left);
        super.getMainTable().row();
        super.getMainTable().add(playerTable);
        super.getMainTable().row();
        super.getMainTable().add(middleTable).expandY();
        super.getMainTable().row();
        super.getMainTable().add(bottomTable);
    }

    private void runClient() {
        if (NetStatus.getInstance().getClient() == null) {
            client = new DefaultClient(1024 * 10, 1024 * 10);
            NetStatus.getInstance().setClient((DefaultClient) client);
            Network.register(client);
        } else {
            client = NetStatus.getInstance().getClient();
            interfaceManager.lblLoginStatus.setText(NetStatus.getInstance().getNetLogin());
            Network.PlayersOnlineAnswer playersOnlineAnswer = new Network.PlayersOnlineAnswer();
            NetStatus.getInstance().getClient().sendTCP(playersOnlineAnswer);
        }
    }

    @Override
    public void show() {
        super.show();

        runClient();

        if (!client.isConnected()) {
            client.start();
            try {
                client.connect(10000, "85.255.9.69", 54555, 54777);
                //client.connect(10000, "192.168.1.7", 54555, 54777);
                interfaceManager.lblServerStatus.setText("Server: CONNECTED");
                //Gdx.app.log("Client", "Connected");
                NetStatus.getInstance().playerStatusRequest();
            } catch (IOException e) {
                Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
                interfaceManager.lblServerStatus.setText("Server: NOT CONNECTED");
                dialog.text("Failed connection to server");
                dialog.button("CLOSE");
                dialog.show(getMainStage());
                //Gdx.app.log("Client", "Error with connection");
                e.printStackTrace();
            }
        } else {
            interfaceManager.lblServerStatus.setText("Server: CONNECTED");
            NetStatus.getInstance().playerStatusRequest();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (NetStatus.getInstance().getNetPlayers() != null) {
            if (NetStatus.getInstance().getNetPlayers().size() == requiredNumberOfPlayers) {
                ScreenLoading.destinationScreen = Screens.ScreenNetGame;
                ScreenManager.getInstance().setScreen(Screens.ScreenLoading);
            }
        }

        if (NetStatus.getInstance().isLoginSuccess()) {
            Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            dialog.text("Successful login");
            dialog.button("CLOSE");
            dialog.show(this.getMainStage());
            NetStatus.getInstance().setLoginSuccess(false);
            interfaceManager.lblLoginStatus.setText(NetStatus.getInstance().getNetLogin());
        }

        if (NetStatus.getInstance().isLoginUnsuccess()) {
            Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            dialog.text("Failed login");
            dialog.button("CLOSE");
            dialog.show(this.getMainStage());
            NetStatus.getInstance().setLoginUnsuccess(false);
        }

        if (NetStatus.getInstance().isRegisterSuccess()) {
            Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            dialog.text("Successful registration");
            dialog.button("CLOSE");
            dialog.show(this.getMainStage());
            NetStatus.getInstance().setRegisterSuccess(false);
        }

        if (NetStatus.getInstance().isRegisterFail()) {
            Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            dialog.text("Failed registration");
            dialog.button("OK");
            dialog.show(this.getMainStage());
            NetStatus.getInstance().setRegisterFail(false);
        }

        if (NetStatus.getInstance().getLoggedPlayersChange()) {
            interfaceManager.lblOnlinePlayersAmount.setText("" + NetStatus.getInstance().getLoggedPlayers());
            NetStatus.getInstance().setLoggedPlayersChange(false);
        }

        if (NetStatus.getInstance().isUnLogin()) {
            Dialog dialog = new Dialog("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            dialog.text("Logged out");
            dialog.button("OK");
            dialog.show(this.getMainStage());
            NetStatus.getInstance().setUnLogin(false);
            interfaceManager.lblLoginStatus.setText("Not logged in");
        }

        if (NetStatus.getInstance().isStatisticReceive()) {
            interfaceManager.lblgamesPlayed.setText("Games played: " + Integer.toString(NetStatus.getInstance().getGamesPlayed()));
            interfaceManager.lblgamesWon.setText("Games won: " + Integer.toString(NetStatus.getInstance().getGamesWon()));
            interfaceManager.lblgamesLost.setText("Games lost: " + Integer.toString(NetStatus.getInstance().getGamesLost()));
            String rank;

            switch (NetStatus.getInstance().getRank()) {
                case 0:
                    rank = "PRIVATE";
                    break;
                default:
                    rank = "-";
            }
            interfaceManager.lblrank.setText("Rank: " + rank);
            NetStatus.getInstance().setStatisticReceive(false);
        }
    }

    @Override
    public void dispose() {
        AssetsMainMenu.getInstance().dispose();
        super.dispose();
    }

    /**
     * Return login window
     *
     * @return Window object
     */
    private Window getLoginWindow() {
        final Window window = new Window("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(600, 250);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        TextButton tbCancel = new TextButton("CANCEL", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        final TextField tfLogin = new TextField("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        final TextField tfPassword = new TextField("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

        final TextButton tbLogIn = new TextButton("LOG IN", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbLogIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (client.isConnected()) {
                    Network.LoginUser loginUser = new Network.LoginUser();
                    String tfText = tfLogin.getText();
                    if (tfText.equals("")) {
                        System.out.println("");
                    }
                    loginUser.login = tfLogin.getText();
                    loginUser.password = tfPassword.getText();
                    client.sendTCP(loginUser);
                    window.remove();
                }
            }
        });

        Label lblLogin = new Label("Login:", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
        Label lblPassword = new Label("Password:", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

        Table buttonTable = new Table();
        buttonTable.add(tbLogIn).pad(5);
        buttonTable.add(tbCancel).pad(5);

        window.add(lblLogin).padBottom(5).padRight(5);
        window.add(tfLogin).padBottom(5);
        window.add(lblPassword).padBottom(5).padLeft(20).padRight(5);
        window.add(tfPassword).padBottom(5);
        window.row();
        window.add(buttonTable).colspan(4);

        return window;
    }

    /**
     * Return new player registration window.
     *
     * @return window object.
     */
    private Window getRegistrationWindow() {
        final Window window = new Window("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(500, 400);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        TextButton tbCancel = new TextButton("Cancel", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        final TextField tfLogin = new TextField("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        final TextField tfPassword = new TextField("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

        TextButton tbLogIn = new TextButton("Sign up", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbLogIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (client.isConnected()) {
                    if (client.isConnected()) {
                        Network.RegisterUser registerUser = new Network.RegisterUser();
                        registerUser.login = tfLogin.getText();
                        registerUser.password = tfPassword.getText();
                        client.sendTCP(registerUser);
                    }
                    window.remove();
                }
            }
        });

        Label lblLogin = new Label("Login:", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
        Label lblPassword = new Label("Password:", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

        window.add(lblLogin).colspan(2);
        window.row();
        window.add(tfLogin).colspan(2);
        window.row();
        window.add(lblPassword).colspan(2);
        window.row();
        window.add(tfPassword).colspan(2);
        window.row();
        window.add(tbLogIn).pad(5).size(175, 50);
        window.add(tbCancel).pad(5).size(175, 50);

        return window;
    }

//    public Label getLblStatusOfLogin() {
//        return lblStatusOfLogin;
//    }

    private class Interface {
        Label lblLoginStatusDiscription;
        Label lblLoginStatus;
        Label lblServerStatus;
        Label lblOnlinePlayers;
        Label lblOnlinePlayersAmount;

        Label lblgamesWon;
        Label lblgamesLost;
        Label lblgamesPlayed;
        Label lblrank;

        Label lblPlayerMobClass;
        TextButton tbNextClass;

        TextButton tbLogin;
        TextButton tbRegister;
        TextButton tbPlay;
        TextButton tbExit;

        Interface() {
            createButtons();
            addListeners();
        }

        private void createButtons() {
            lblgamesPlayed = new Label("Games played: -", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            lblgamesWon = new Label("Games won: -", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            lblgamesLost = new Label("Games lost: -", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            lblrank = new Label("Rank: -", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");

            lblLoginStatusDiscription = new Label("Login: ", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            lblLoginStatus = new Label("Not logged in", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            lblServerStatus = new Label("Server: NOT CONNECTED", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            //lblStatusOfLogin = lblLoginStatus;
            lblOnlinePlayers = new Label("Players online: ", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            lblOnlinePlayersAmount = new Label("", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16");
            //labelAmountOfOnlinePlayers = lblOnlinePlayersAmount;

            lblPlayerMobClass = new Label("Class: Knight", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
            tbNextClass = new TextButton("Next class", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

            tbLogin = new TextButton("Log In", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            tbRegister = new TextButton("Sign up", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            tbPlay = new TextButton("PLAY", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            tbExit = new TextButton("EXIT", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        }

        /**
         * Adding listeners to buttons.
         */
        private void addListeners() {

            tbPlay.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (NetStatus.getInstance().getNetLogin() == null) {
                        new Dialog("", (Skin) AssetsMainMenu.getInstance().getManager().get("styles/skin.json")) {
                            {
                                text("Player not logged in");
                                button("OK", "ok");
                            }

                            @Override
                            protected void result(Object object) {
                                super.result(object);
                                if (object.equals("ok")) {
                                }
                            }
                        }.show(getMainStage());
                    } else {

                        Network.StartBattle startBattle = new Network.StartBattle();
                        startBattle.gameTypes = Network.GameTypes.freeForAll;
                        startBattle.countOfPlayers = Network.CountOfPlayers.two;
                        if (GameStatus.getInstance().getNewPlayerMobClass().equals(PlayerMobClasses.Knight)) {
                            startBattle.playerMobClass = 0;
                        } else {
                            startBattle.playerMobClass = 1;
                        }
                        requiredNumberOfPlayers = 1;

                        NetStatus.getInstance().setNetPlayers(new ArrayList<NetPlayer>());

                        client.sendTCP(startBattle);

                        new Dialog("", (Skin) AssetsMainMenu.getInstance().getManager().get("styles/skin.json")) {
                            {
                                text("Looking for enemy...");
                                button("CANCEL", "ANULUJ");
                            }

                            @Override
                            protected void result(Object object) {
                                super.result(object);
                                if (object.equals("ANULUJ")) {
                                    client.sendTCP(new Network.CancleBattle());
                                }
                            }
                        }.show(getMainStage());
                    }
                }
            });

            tbNextClass.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (GameStatus.getInstance().getNewPlayerMobClass().equals(PlayerMobClasses.Knight)) {
                        GameStatus.getInstance().setNewPlayerMobClass(PlayerMobClasses.Wizard);
                        lblPlayerMobClass.setText("Class: Wizard");
                    } else if (GameStatus.getInstance().getNewPlayerMobClass().equals(PlayerMobClasses.Wizard)) {
                        GameStatus.getInstance().setNewPlayerMobClass(PlayerMobClasses.Knight);
                        lblPlayerMobClass.setText("Class: Knight");
                    }
                }
            });

            tbLogin.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (tbLogin.getText().equals("Log out")) {
                        tbLogin.setText("Log In");
                        lblLoginStatus.setText("Not Loged In");
                    } else {
                        getMainStage().addActor(getLoginWindow());
                    }
                }
            });

            tbRegister.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    getMainStage().addActor(getRegistrationWindow());
                }
            });

            tbExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    NetStatus.getInstance().setNetLogin(null);

                    if (client != null) {
                        client.stop();
                        //client.close();
//                        try {
//                            client.dispose();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        client = null;
                    }
                    ScreenManager.getInstance().setScreen(com.mygdx.eoh.enums.Screens.ScreenMainMenu);
                }
            });
        }
    }
}
