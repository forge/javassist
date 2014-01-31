package org.jboss.forge.furnace.proxy.javassist.tools.reflect;

import org.jboss.forge.furnace.proxy.javassist.tools.reflect.Loader;

/**
 * @author Brett Randall
 */
public class ClassMetaobjectTest {
    public static void main(String[] args) throws Throwable {
        Loader loader = new Loader();
        loader.makeReflective("org.jboss.forge.furnace.proxy.javassist.tools.reflect.Person",
                              "org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                              "org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject");
        loader.run("org.jboss.forge.furnace.proxy.javassist.tools.reflect.Person", new String[] {});
    }
}
