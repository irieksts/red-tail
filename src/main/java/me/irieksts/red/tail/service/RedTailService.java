/**
 * 
 */
package me.irieksts.red.tail.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.irieksts.red.tail.ws.config.RedTailConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import edu.lehigh.swat.hawk.core.Document;
import edu.lehigh.swat.hawk.core.Ontology;
import edu.lehigh.swat.hawk.owl.OwlRdfReader;
import edu.lehigh.swat.hawk.owl.OwlRdfWriter;
import edu.lehigh.swat.hawk.storage.Result;
import edu.lehigh.swat.hawk.storage.ResultSet;
import edu.lehigh.swat.hawk.storage.ontper.dldb.DldbStorage;
import edu.lehigh.swat.hawk.storage.simpledb.SparqlQuery;

/**
 * @author Isaac Rieksts
 *
 */
public class RedTailService {
	private static Logger logger = LoggerFactory.getLogger(RedTailService.class.getCanonicalName());
	
	private String jdbc;
	private DldbStorage storage;
	
	public RedTailService(RedTailConfig conf) {
		jdbc = conf.getJdbc();
	}
	
	public void load(String url) throws Exception {
		Client c = Client.create();
		WebResource x = c.resource(url);
		InputStream is = x.get(InputStream.class);
		
		getStorage().open();
		OwlRdfReader reader = new OwlRdfReader(getStorage());
		reader.read(is);
		Document doc = reader.read(is);
		System.out.println(doc);
		if(doc instanceof Ontology) {
			System.out.println("onto");
			((edu.lehigh.swat.hawk.storage.simpledb.Ontology) doc).setModel(getStorage());
			((Ontology) doc).flush();
		}
		OwlRdfWriter w = new OwlRdfWriter();
		w.write(System.out, doc);
		getStorage().close();
		setStorage(null);
	}
	
	public List<Map<String, String>> query(String query) throws Exception {
		logger.info("q: " + query);
		logger.info("p: " + new SparqlQuery(query).getPerspective());
		
		
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		Map<String, String> resultSet = null;
		getStorage().open();
		ResultSet results = getStorage().issueQuery(new SparqlQuery(query));
		String[] header = results.getHeaders();
		while(results.hasMoreResults()) {
			resultSet = new HashMap<String, String>();
			Result result = results.getNextResult();
			String[] values = result.getValues();
			int i = 0;
			for(String key : header) {
				resultSet.put(key, values[i]);
				i++;
			}			
			resultList.add(resultSet);
		}
		getStorage().close();
		setStorage(null);
		return resultList;
	}

	public String getJdbc() {
		return jdbc;
	}

	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}

	public DldbStorage getStorage() {
		return storage = storage != null? storage : new DldbStorage(jdbc);
	}

	public void setStorage(DldbStorage storage) {
		this.storage = storage;
	}
}
