package bonus.generalBonuses.bonuses;

import bonus.bonuses.Bonus;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;
import heroes.abstractHero.hero.Hero;
import javafx.scene.image.ImageView;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.actions.ActionType;
import managment.playerManagement.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HStrengthenTheArmor extends Bonus implements DynamicHandleService {

    private static final Logger log = LoggerFactory.getLogger(HStrengthenTheArmor.class);

    private static final double ARMOR_COEFFICIENT = 0.4;

    public HStrengthenTheArmor(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        final HandleComponent handler = getHandlerInstance();
        actionManager.getEventEngine().addHandler(handler);
        log.info("ARMOR UP");
    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return new HandleComponent() {

            private Player player;

            private Player alternativePlayer;

            private double hitPoints;

            private boolean isWorking = true;

            @Override
            public final void setup() {
                this.player = playerManager.getCurrentTeam().getCurrentPlayer();
                this.hitPoints = player.getHero().getHitPoints();
                this.alternativePlayer = playerManager.getCurrentTeam().getAlternativePlayer();
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final Hero currentHero = player.getHero();
                final double comparison = hitPoints - currentHero.getHitPoints();
                log.info("ARMOR HANDLE");
                if (comparison > 0) {
                    log.info("COMPARISON: " + comparison);
                    final double ARMOR = comparison * ARMOR_COEFFICIENT;
                    currentHero.setHitPoints(currentHero.getHitPoints() + ARMOR);
                    log.info("ARMOR: " + ARMOR);
                }
                this.hitPoints = currentHero.getHitPoints();
                if (actionEvent.getActionType() == ActionType.START_TURN
                        && (actionEvent.getPlayer() == player || actionEvent.getPlayer() == alternativePlayer)) {
                    isWorking = false;
                    log.info("ARMOR DOWN");
                }
            }

            @Override
            public final String getName() {
                return "StrengthenTheArmor";
            }

            @Override
            public final Player getCurrentPlayer() {
                return player;
            }

            @Override
            public final boolean isWorking() {
                return isWorking;
            }

            @Override
            public final void setAble(final boolean able) {
                this.isWorking = able;
            }
        };
    }
}