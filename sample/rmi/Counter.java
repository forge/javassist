package sample.rmi;

import org.jboss.forge.furnace.proxy.javassist.tools.rmi.AppletServer;
import java.io.IOException;
import org.jboss.forge.furnace.proxy.javassist.CannotCompileException;
import org.jboss.forge.furnace.proxy.javassist.NotFoundException;

public class Counter {
    private int count = 0;

    public int get() {
	return count;
    }

    synchronized public int increase() {
	count += 1;
	return count;
    }

    public static void main(String[] args)
	throws IOException, NotFoundException, CannotCompileException
    {
	if (args.length == 1) {
	    AppletServer web = new AppletServer(args[0]);
	    web.exportObject("counter", new Counter());
	    web.run();
	}
	else
	    System.err.println(
			"Usage: java sample.rmi.Counter <port number>");
    }
}
