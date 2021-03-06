package org.jboss.forge.furnace.proxy.javassist;

import junit.framework.*;

import java.lang.reflect.Method;

import org.jboss.forge.furnace.proxy.javassist.ClassPool;
import org.jboss.forge.furnace.proxy.javassist.Loader;

public class JvstTestRoot extends TestCase {
    // the directory where all compiled class files are found.
    public static final String PATH = "../target/test-classes/";

    // the directory where org.jboss.forge.furnace.proxy.javassist.jar is found.
    public static final String JAR_PATH = "../";

    ClassPool sloader, dloader;
    Loader cloader;

    public JvstTestRoot(String name) {
        super(name);
    }

    protected void print(String msg) {
        System.out.println(msg);
    }

    protected void print(Exception e) {
        e.printStackTrace();
    }

    protected void setUp() throws Exception {
        sloader = ClassPool.getDefault();
        dloader = new ClassPool(null);
        dloader.appendSystemPath();
        dloader.insertClassPath(".");
        cloader = new Loader(dloader);
    }

    protected Object make(String name) throws Exception {
        return cloader.loadClass(name).newInstance();
    }

    protected int invoke(Object target, String method) throws Exception {
        Method m = target.getClass().getMethod(method, new Class[0]);
        Object res = m.invoke(target, new Object[0]);
        return ((Integer)res).intValue();
    }

    protected int invoke(Object target, String method, int arg)
        throws Exception {
        Method m =
            target.getClass().getMethod(method, new Class[] { int.class });
        Object res = m.invoke(target, new Object[] { new Integer(arg)});
        return ((Integer) res).intValue();
    }
}
