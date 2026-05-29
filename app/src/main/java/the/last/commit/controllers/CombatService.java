package the.last.commit.controllers;

import the.last.commit.models.Enemy;
import the.last.commit.models.Hero;
import javafx.scene.image.ImageView;
import java.util.List;


public interface CombatService {


    void executeHeroAction(
        String type,
        Enemy target,
        Hero hero,
        BattleViewBridge view,
        ImageView hView,
        ImageView eView,
        List<Enemy> enemies,
        BattleControllerContext context
    );
}
