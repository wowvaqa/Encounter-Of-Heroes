package com.mygdx.eoh.creators;

import com.mygdx.eoh.enums.Spells;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.magic.Spell;

/**
 *
 * Created by v on 2017-02-21.
 */
public class SpellCreator {
    private static SpellCreator instance = new SpellCreator();

    public static SpellCreator getInstance() {
        return instance;
    }

    private SpellCreator() {
    }

    public Spell createSpell(PlayerMob playerMob, Spells spellType){
        Spell spell = new Spell(playerMob, spellType);
        return spell;
    }
}
