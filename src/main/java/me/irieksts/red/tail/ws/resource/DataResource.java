/**
 * 
 */
package me.irieksts.red.tail.ws.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import me.irieksts.red.tail.service.RedTailService;
import me.irieksts.red.tail.ws.config.RedTailConfig;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Isaac Rieksts
 *
 */
@Path("red-tail/data")
public class DataResource {

	private static Logger logger = LoggerFactory.getLogger(DataResource.class.getCanonicalName());
	private RedTailConfig conf;
	private RedTailService redTailService;
	
	public DataResource(RedTailConfig conf) {
		this.conf = conf;
	}
	
	@POST
	@Path("/load")
	@Produces(MediaType.TEXT_PLAIN)
	public Object load(String url) throws Exception {
		logger.debug("load");
		try {
		getRedTailService().load(url);
		} catch(Throwable t) {
			logger.warn("DataResource" + ExceptionUtils.getStackTrace(t));
		}
		return "loaded: " + url;
	}
	
	@POST
	@Path("/query")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Object query(String query) throws Exception {
		logger.debug("query");
		return getRedTailService().query(query);
//		HashMap<String, String> s = new HashMap<String, String>();
//		s.put("q", query);
//		return s;
//		return "result: " + query;
	}

	public RedTailConfig getConf() {
		return conf;
	}

	public void setConf(RedTailConfig conf) {
		this.conf = conf;
	}

	public RedTailService getRedTailService() {
		return redTailService = redTailService != null ? redTailService : new RedTailService(conf);
	}

	public void setRedTailService(RedTailService redTailService) {
		this.redTailService = redTailService;
	}
}
