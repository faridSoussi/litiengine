package de.gurkenlabs.litiengine.environment.tilemap.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class CustomPropertyProviderTests {

  @Test
  public void testSetCustomProperty() {
    CustomPropertyProvider propProvider = new CustomPropertyProvider();
    propProvider.setCustomProperty("test", "testvalue");

    assertEquals("testvalue", propProvider.getCustomProperty("test"));
    assertNull(propProvider.getCustomProperty("test2"));
    assertEquals(1, propProvider.getCustomProperties().size());

    propProvider.setCustomProperty("test", "testvalue2");

    assertEquals("testvalue2", propProvider.getCustomProperty("test"));

    ArrayList<Property> props = new ArrayList<>();
    props.add(new Property("test2", "testvalue3"));
    props.add(new Property("test3", "testvalue4"));

    propProvider.setCustomProperties(props);

    assertEquals(2, propProvider.getCustomProperties().size());
    assertEquals("testvalue3", propProvider.getCustomProperty("test2"));
    assertEquals("testvalue4", propProvider.getCustomProperty("test3"));

    propProvider.setCustomProperties(null);
    assertNotNull(propProvider.getCustomProperties());
    assertEquals(0, propProvider.getCustomProperties().size());
  }
}
