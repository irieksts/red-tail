/**
 * 
 */
package me.irieksts.red.tail.ws;

import me.irieksts.red.tail.ws.config.RedTailConfig;
import me.irieksts.red.tail.ws.health.PingHealthCheck;
import me.irieksts.red.tail.ws.resource.DataResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

/**
 * @author Isaac Rieksts
 * 
 */
public class StartService extends Service<RedTailConfig> {

	public static void main(String[] args) throws Exception {
		new StartService().run(args);
	}

	protected StartService() {
		super("Red Tail");
	}

	@Override
	protected void initialize(RedTailConfig conf, Environment env)
			throws Exception {
		env.addHealthCheck(new PingHealthCheck());

		env.addResource(new DataResource(conf));
	}
}
