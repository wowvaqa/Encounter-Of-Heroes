package com.mygdx.eoh.gameClasses;

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

            System.out.println("Nadepnieto na skrzynie ze skarbem :)");
            playerMob.getFieldOfPlayerMob().getTreasure().setStepIn(true);
            GameStatus.getInstance().getMainStage().addActor(playerMob.getFieldOfPlayerMob().getTreasure().getTreasureWindow());
        }
    }

    boolean isCheckStepping() {
        return checkStepping;
    }

    void setCheckStepping(boolean checkStepping) {
        this.checkStepping = checkStepping;
    }
}
