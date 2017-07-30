package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.eoh.assets.AssetsSounds;
import com.mygdx.eoh.net.NetStatus;

/**
 * When player mob step into something then class is needed.
 * Created by v on 2017-01-02.
 */

class StepManager {
    private PlayerMob playerMob;
    //Flag say PlayerMob to check step.
    private boolean checkStepping = false;

    StepManager(PlayerMob playerMob) {
        this.playerMob = playerMob;
    }

    /**
     * Checking step after playerMob move.
     */
    void checkStep() {
        if (playerMob.getFieldOfPlayerMob().getCastleMob() != null) {
            playerMob.getFieldOfPlayerMob().getCastleMob().changeOwner(playerMob.getPlayerOwner());
            checkStepping = false;
        }

        if (playerMob.getFieldOfPlayerMob().getItem() != null) {
            System.out.println("Nadepnięto na item :)");
            playerMob.getFieldOfPlayerMob().getItem().itemAction(playerMob);
        }

        if (playerMob.getFieldOfPlayerMob().getTreasure() != null &&
                !playerMob.getFieldOfPlayerMob().getTreasure().isStepIn()) {

            if (playerMob.getPlayerOwner().equals(GameStatus.getInstance().getCurrentPlayerTurn())) {
                AssetsSounds.getInstance().getManager().get("sounds/boxOpen.wav", Sound.class).play();
            }

            if (NetStatus.getInstance().getClient() == null) {
                System.out.println("Nadepnieto na skrzynie ze skarbem :)");
                playerMob.getFieldOfPlayerMob().getTreasure().setStepIn(true);
                GameStatus.getInstance().getMainStage().addActor(playerMob.getFieldOfPlayerMob().getTreasure().getTreasureWindow(playerMob));
            } else if (playerMob.getPlayerOwner() == GameStatus.getInstance().getCurrentPlayerTurn()) {
                System.out.println("Nadepnieto na skrzynie ze skarbem GRA SIECIOWA :)");
                playerMob.getFieldOfPlayerMob().getTreasure().setStepIn(true);
                GameStatus.getInstance().getMainStage().addActor(playerMob.getFieldOfPlayerMob().getTreasure().getTreasureWindow(playerMob));
            }
        }
    }

    boolean isCheckStepping() {
        return checkStepping;
    }

    void setCheckStepping(boolean checkStepping) {
        this.checkStepping = checkStepping;
    }
}
