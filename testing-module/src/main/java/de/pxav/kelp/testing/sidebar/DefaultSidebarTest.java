package de.pxav.kelp.testing.sidebar;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.animation.TextAnimationFactory;
import de.pxav.kelp.core.sidebar.CreateSidebar;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import de.pxav.kelp.core.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.type.SidebarFactory;
import org.bukkit.entity.Player;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class DefaultSidebarTest {

  private SidebarFactory sidebarFactory;
  private SidebarComponentFactory sidebarComponentFactory;
  private SidebarRepository sidebarRepository;
  private TextAnimationFactory textAnimationFactory;

  @Inject
  public DefaultSidebarTest(SidebarFactory sidebarFactory,
                            SidebarComponentFactory sidebarComponentFactory,
                            SidebarRepository sidebarRepository,
                            TextAnimationFactory textAnimationFactory) {
    this.sidebarFactory = sidebarFactory;
    this.sidebarComponentFactory = sidebarComponentFactory;
    this.sidebarRepository = sidebarRepository;
    this.textAnimationFactory = textAnimationFactory;
  }

  @CreateSidebar(identifier = "default",
          setOnJoin = true,
          titleAnimationInterval = 700)
  public AnimatedSidebar defaultSidebar(Player player) {
    return this.sidebarFactory.newAnimatedSidebar()
            .withTitle(textAnimationFactory
                    .newBuildingTextAnimation()
                    .text("This is a test!")
                    .ignoreSpaces())
            .addComponent(sidebarComponentFactory.lineSeparatorComponent(10))
            .addComponent(sidebarComponentFactory.emptyLineComponent(9))
            .addComponent(sidebarComponentFactory
                    .simpleTextComponent()
                    .text("§aWelcome, ")
                    .line(8))
            .addComponent(sidebarComponentFactory
                    .simpleTextComponent()
                    .text(player.getName())
                    .line(7));
  }
}
