package com.github.tjdett.play2crashplugin;

import java.util.Collections;
import java.util.Map;

import play.Application;
import play.Configuration;
import play.api.Plugin;

import org.crsh.plugin.PluginContext;
import org.crsh.plugin.PluginDiscovery;
import org.crsh.plugin.PluginLifeCycle;
import org.crsh.plugin.ServiceLoaderDiscovery;
import org.crsh.vfs.FS;
import org.crsh.vfs.Path;

/**
 * Simple plugin which initializes CRaSH at application startup.
 * 
 * @author Tim Dettrick <t.dettrick@uq.edu.au>
 *
 */
public class CrashPlugin extends PluginLifeCycle implements Plugin {
  
  public static final String CONFIG_KEY = "crash";

  private final Application application;
  private PluginContext context = null;

  public CrashPlugin(Application application) {
    super();
    this.application = application;
  }

  @Override
  public boolean enabled() {
    return application.configuration().getBoolean("crash.enabled", true);
  }

  public PluginContext getContext() {
    // If initialized, return context
    if (context != null) return context;
    try {
      PluginDiscovery discovery = new ServiceLoaderDiscovery(
          application.classloader());
      context = new PluginContext(discovery, getAttributes(),
          (new FS())
            .mount(application.classloader(), Path.get("/crash/commands/"))
            .mount(application.getFile("app/crash")),
          (new FS())
            .mount(application.classloader(), Path.get("/crash/"))
            .mount(application.getFile("conf")),
          application.classloader());
      return context;
    } catch (Exception e) {
      // We can safely call this fatal
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public void onStart() {
    start(getContext());
  }

  @Override
  public void onStop() {
    stop();
  }

  protected Map<String, Object> getAttributes() {
    Configuration config = application.configuration().getConfig(CONFIG_KEY);
    if (config == null)
      return Collections.emptyMap();
    return config.asMap();
  }

}