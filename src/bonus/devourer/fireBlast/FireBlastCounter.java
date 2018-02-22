package bonus.devourer.fireBlast;

import heroes.abstractHero.skills.Skill;

import java.util.List;

final class FireBlastCounter {
    
    private static double MAIN_DAMAGE = 40;
    
    private static double ADDITIONAL_DAMAGE = 20;
    
    static double formDamage(List<Skill> skills){
        double damage = MAIN_DAMAGE;
        for (final Skill skill: skills){
            if (skill.getName().equals("fireBlast")){
                damage += ADDITIONAL_DAMAGE;
            }
        }
        return damage;
    }
}
