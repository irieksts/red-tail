/**
 * 
 */
package me.irieksts.red.tail.ws.config;

import com.yammer.dropwizard.config.Configuration;

/**
 * @author Isaac Rieksts
 * 
 */
public class RedTailConfig extends Configuration {
	private String jdbc;

	public String getJdbc() {
		return jdbc;
	}

	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}
}
