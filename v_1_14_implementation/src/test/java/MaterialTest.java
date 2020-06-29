import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MaterialTest {

  public void testMaterials() {
    Collection<String> c = new ArrayList<>();
    for (Material value : Material.values()) {
      System.out.println(value);
      if(value.toString().contains("WOOL")) {
        c.add(value.toString());
      }
    }
    System.out.println("C OUTPUT");
    for (String s : c) {
      System.out.println(s);
    }
  }

}
