package org.jboss.forge.furnace.proxy.javassist.tools.reflect;

import org.jboss.forge.furnace.proxy.javassist.*;
import org.jboss.forge.furnace.proxy.javassist.tools.reflect.CannotReflectException;
import org.jboss.forge.furnace.proxy.javassist.tools.reflect.Loader;

import junit.framework.*;

public class LoaderTest extends TestCase {
    private Loader loader;

    public LoaderTest(String name) {
         super(name);
    }

    public void setUp() throws Exception {
        loader = new Loader();
    }

    public void testAttemptReflectInterface() throws Exception {
        try {
            loader.makeReflective("org.jboss.forge.furnace.proxy.javassist.ClassPath",
                                  "org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                                  "org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject");
            fail("Attempting to reflect an interface should throw a CannotReflectException");
        } catch (CannotReflectException e) {
            // expected
        }
    }

    public void testAttemptReflectClassMetaobject() throws Exception {
        try {
            loader.makeReflective("org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject",
                                  "org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                                  "org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject");
            fail("Attempting to reflect a ClassMetaobject should throw a CannotReflectException");
        } catch (CannotReflectException e) {
            // expected
        }
    }

    public void testAttemptReflectMetaobject() throws Exception {
        try {
            loader.makeReflective("org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                                  "org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                                  "org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject");
            fail("Attempting to reflect a Metaobject should throw a CannotReflectException");
        } catch (CannotReflectException e) {
            // expected
        }
    }

    public void testFinalMethod() throws Throwable {
        loader.makeReflective("org.jboss.forge.furnace.proxy.javassist.tools.reflect.SuperClass",
                              "org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                              "org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject");

        ClassPool cp = ClassPool.getDefault();

        CtClass cc2 = cp.get("org.jboss.forge.furnace.proxy.javassist.tools.reflect.SuperClass");
        cc2.debugWriteFile("reflected/");

        CtClass cc = cp.get("org.jboss.forge.furnace.proxy.javassist.tools.reflect.SubClass");

        CtMethod[] ms = cc.getMethods();
        for (int i = 0; i < ms.length; ++i)
            System.out.println(ms[i] + " in "
                               + ms[i].getDeclaringClass().getName());

        loader.makeReflective("org.jboss.forge.furnace.proxy.javassist.tools.reflect.SubClass",
                              "org.jboss.forge.furnace.proxy.javassist.tools.reflect.Metaobject",
                              "org.jboss.forge.furnace.proxy.javassist.tools.reflect.ClassMetaobject");

        loader.run("org.jboss.forge.furnace.proxy.javassist.tools.reflect.SubClass", new String[] {});
    }
}
