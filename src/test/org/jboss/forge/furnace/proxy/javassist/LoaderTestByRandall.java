/*
 * Copyright (c) Brett Randall 2004. All rights reserved.
 * 
 * Created on Jul 20, 2004
 */

package org.jboss.forge.furnace.proxy.javassist;

import org.jboss.forge.furnace.proxy.javassist.CannotCompileException;
import org.jboss.forge.furnace.proxy.javassist.ClassPool;
import org.jboss.forge.furnace.proxy.javassist.CtClass;
import org.jboss.forge.furnace.proxy.javassist.CtClassType;
import org.jboss.forge.furnace.proxy.javassist.CtMethod;
import org.jboss.forge.furnace.proxy.javassist.CtNewMethod;
import org.jboss.forge.furnace.proxy.javassist.Loader;
import org.jboss.forge.furnace.proxy.javassist.NotFoundException;
import org.jboss.forge.furnace.proxy.javassist.Translator;

import junit.framework.TestCase;

/**
 * @author brandall
 */
public class LoaderTestByRandall extends TestCase {

    ClassPool cp;
    Loader loader;

    public LoaderTestByRandall(String name) {
        super(name);
    }
    
    public void setUp() {
        cp = new ClassPool();
        cp.appendSystemPath();
        loader = new Loader(cp);
    }
    
    public void testLoadGoodClass() throws Exception {
        String name = "org.jboss.forge.furnace.proxy.javassist.LoaderTestByRandall";
        cp.get(name);
        Class clazz = loader.loadClass(name);
        assertEquals("Class not loaded by loader",
                     loader, clazz.getClassLoader());
    }
    
    public void testLoadGoodClassByDelegation() throws Exception {
        Class clazz = loader.loadClass("java.lang.String");
    }
    
    public void testLoadBadClass() {
        try {
            Class clazz = loader.loadClass("never.going.to.find.Class");
            fail("Expected ClassNotFoundException to be thrown");
        } catch (ClassNotFoundException e) {
            // expected
        }
    }
    
    public void testLoadBadClassByDelegation() {
        try {
            Class clazz = loader.loadClass("java.never.going.to.find.Class");
            fail("Expected ClassNotFoundException to be thrown");
        } catch (ClassNotFoundException e) {
            // expected
        }
    }
    
    public void testLoadBadCodeModification() throws Exception {
        String classname = "org.jboss.forge.furnace.proxy.javassist.LoaderTestByRandall";

        Translator trans = new Translator() {
            public void start(ClassPool pool)
                throws NotFoundException, CannotCompileException
            {
            }

            public void onLoad(ClassPool pool, String classname)
                throws NotFoundException, CannotCompileException
            {
                String body = new String("this will never compile");
                CtClass clazz = pool.get(classname);
                CtMethod newMethod = CtNewMethod.make(CtClassType.voidType,
    					"wontCompileMethod",
    					new CtClass[] {},
    					new CtClass[] {},
    					body,
    					clazz);
                clazz.addMethod(newMethod);
            }
        };
        
        loader.addTranslator(cp, trans);
        
        try {
            Class clazz = loader.loadClass(classname);
            fail("Expected loader to throw ClassNotFoundException " +
                    "caused by CannotCompileException");
        } catch (ClassNotFoundException e) {
            // expected
            System.out.println(e);
            assertEquals("ClassNotFoundException was not caused "
                         + "by CannotCompileException",
                          CannotCompileException.class,
                          e.getCause().getClass());
        }
    }
}
