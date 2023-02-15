package enemies;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import enemies.Boss;
import game.IcyGuy;
import levels.GameLevel;
import levels.Level6;
import projectiles.Ice;
import view.GameView;

public class EnemyCollision implements CollisionListener {

    private final IcyGuy icyGuy;
    private final GameLevel currentLevel;

    public EnemyCollision(IcyGuy i, GameLevel g ){

        this.icyGuy = i;
        currentLevel = g;
    }
    @Override
    public void collide(CollisionEvent c) {
        // if other body is ice
        if (c.getOtherBody() instanceof Ice){
            c.getOtherBody().destroy(); // ice gets destroyed on contact
            if(c.getReportingBody() instanceof Boss){
                // bosses health gets reduced by 40 if contact with ice
                ((Level6)currentLevel).getBoss().setHealth(((Level6)currentLevel).getBoss().getHealth() - 40);
                Boss.getBossHurt().play(); // sound when boss gets hurt
                ((Level6)currentLevel).getBoss().healthLoss(); // queries to find out if boss lost all lives
            }else {
                // increases enemy's defeated of icy guy
                currentLevel.getIcyGuy().setEnemysDefeated(currentLevel.getIcyGuy().getEnemysDefeated() + 1);
                c.getOtherBody().destroy(); // ice removed
                c.getReportingBody().destroy(); // enemy dies
            }

        }

        else if (c.getOtherBody() instanceof IcyGuy ){
            if(!currentLevel.getGame().isMuted()) { // checks if game is muted
                IcyGuy.getIcyGuyHurt().play();
            }
            if(c.getOtherBody() instanceof Boss) {
                // if icy guy collides with boss, health is reduced by 10
                currentLevel.getIcyGuy().setHealth(currentLevel.getIcyGuy().getHealth() - 10);
                GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth()); // healthbar updated
                currentLevel.getIcyGuy().healthLoss();
            }else{
                currentLevel.getIcyGuy().setHealth(currentLevel.getIcyGuy().getHealth() - 50); // when in contact with other enemys
                GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth());
                currentLevel.getIcyGuy().healthLoss();

        }

        }

    }

}
