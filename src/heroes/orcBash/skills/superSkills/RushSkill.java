package heroes.orcBash.skills.superSkills;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import heroes.abstractHero.hero.Hero;
import heroes.abstractHero.skills.abstractSkill.AbstractSkill;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import managment.actionManagement.actions.ActionEventFactory;
import managment.battleManagement.BattleManager;
import managment.playerManagement.Player;
import managment.playerManagement.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public final class RushSkill extends AbstractSkill {

    private static final Logger log = LoggerFactory.getLogger(RushSkill.class);

    @Inject
    @Named("RUSH_NAME")
    private static String NAME;

    @Inject
    @Named("RUSH_RELOAD")
    private static int RELOAD;

    @Inject
    @Named("RUSH_REQUIRED_LEVEL")
    private static int REQUIRED_LEVEL;

    @Inject
    @Named("RUSH_DAMAGE_SKILL_COEFFICIENT")
    private static double DAMAGE_SKILL_COEFFICIENT;

    private static final List<Double> SKILL_COEFFICIENTS = Collections.singletonList(DAMAGE_SKILL_COEFFICIENT);

    public RushSkill(final ImageView sprite, final ImageView description, final List<Media> voiceList) {
        super(NAME, RELOAD, REQUIRED_LEVEL, SKILL_COEFFICIENTS
                , sprite, description, voiceList);
    }

    @Override
    public final void use(final BattleManager battleManager, final PlayerManager playerManager) {
        final double damage = getParent().getHealthSupply() * coefficients.get(0);
        final Player currentPlayer = playerManager.getCurrentTeam().getCurrentPlayer();
        final Player opponentPlayer = playerManager.getOpponentATeam().getCurrentPlayer();
        final Hero opponentHero = opponentPlayer.getHero();
        if (opponentHero.getDamage(damage)) {
            actionEvents.add(ActionEventFactory.getDealDamage(currentPlayer));
        }
    }

    @Override
    public final void showAnimation() {

    }
}