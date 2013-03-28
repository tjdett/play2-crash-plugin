package com.github.tjdett.play2crashplugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crsh.plugin.ResourceKind;
import org.junit.*;

import play.Play;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

/**
 * Tests for the plugin.
 * 
 * @author Tim Dettrick <t.dettrick@uq.edu.au>
 *
 */
public class CrashPluginTest {

  @Test 
  public void pluginMustStart() {
    running(fakeApplication(), new Runnable() {
      public void run() {
        final CrashPlugin plugin = Play.application().plugin(CrashPlugin.class);
        assertThat(plugin).isNotNull();
        assertThat(plugin.enabled()).isTrue();
        assertThat(plugin.getContext().getLoader())
          .isSameAs(Play.application().classloader());
        final List<String> commandResourceIds = 
            plugin.getContext().listResourceId(ResourceKind.COMMAND);
        assertThat("help").isIn(commandResourceIds);
      }
    });
  }
  
  @Test 
  public void pluginCanBeDisabled() {
    final Map<String, Object> config = new HashMap<String, Object>();
    config.put("crash.enabled", false);
    running(fakeApplication(config), new Runnable() {
      public void run() {
        final CrashPlugin plugin = Play.application().plugin(CrashPlugin.class);
        assertThat(plugin).isNull();
      }
    });
  }

}