package com.mygdx.eoh.gameClasses;

import com.mygdx.eoh.effects.LongEffect;

/**
 *
 * Created by v on 2017-03-09.
 */

public class ModificatorGetter {

    public static int getAttackModificator(PlayerMob playerMob){
        int attackModificator = 0;
        for (LongEffect longEffect: playerMob.getLongEffects()){
            attackModificator += longEffect.getAttackModificator();
        }
        return attackModificator;
    }

}
