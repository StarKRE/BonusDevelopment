package bonus.devourerBonuses.bonuses.hybridisation;

import bonus.bonuses.Bonus;
import heroes.abstractHero.skills.Skill;
import javafx.scene.image.ImageView;
import lib.duplexMap.DuplexMap;
import managment.actionManagement.actions.ActionEvent;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.RegularHandleService;
import managment.playerManagement.Player;

import java.util.ArrayList;
import java.util.List;

public final class AHybridisation extends Bonus implements RegularHandleService{

    private HybridisationSkillProxyComponent hybridisationSkillProxyComponent;

    public AHybridisation(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {
        if (hybridisationSkillProxyComponent.packSkill()) {
            wireActionManager(hybridisationSkillProxyComponent.getJustInTimeFireBlastSkill());
        } else {
            hybridisationSkillProxyComponent.invokeSkill(actionManager.getEventEngine(), playerManager);
        }
    }

    private void wireActionManager(final Skill skill){
        skill.setActionManager(actionManager);
    }

    @Override
    public final HandleComponent getInstallHandlerInstance(final Player player) {
        return new HandleComponent() {

            private Player currentPlayer;

            @Override
            public final void setup() {
                this.currentPlayer = player;
                hybridisationSkillProxyComponent = new FireBlastSkillProxyComponent(currentPlayer);
            }

            @Override
            public final void handle(final ActionEvent actionEvent) {
                final List<Skill> garbageList = new ArrayList<>();
                for (final Skill skill: currentPlayer.getHero().getCollectionOfSkills()){
                    if (skill.isReady()){
                        final DuplexMap<Skill, Skill> skillVsProxyMap
                                = hybridisationSkillProxyComponent.getSkillVsProxyMap();
                        final Skill fireBlast = skillVsProxyMap.getProxy(skill);
                        if (fireBlast != null){
                            garbageList.add(fireBlast);
                        }
                    }
                }
                garbageList.forEach(hybridisationSkillProxyComponent::destroy);
            }

            @Override
            public final String getName() {
                return "FireBlast";
            }

            @Override
            public final Player getCurrentPlayer() {
                return currentPlayer;
            }

            @Override
            public final boolean isWorking() {
                return true;
            }

            @Override
            public final void setAble(boolean able) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
