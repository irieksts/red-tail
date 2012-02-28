package me.irieksts.red.tail.ws.health;

import com.yammer.metrics.core.HealthCheck;

/**
 * @author Isaac Rieksts
 *
 */
public class PingHealthCheck extends HealthCheck{

	public PingHealthCheck() {
		super(PingHealthCheck.class.getSimpleName());
	}

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
