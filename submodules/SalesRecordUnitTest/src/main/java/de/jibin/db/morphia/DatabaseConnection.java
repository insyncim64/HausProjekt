package de.jibin.db.morphia;

import java.util.Arrays;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class DatabaseConnection 
{
	final Morphia morphia;
	final Datastore datastore;
	
	public DatabaseConnection(String ip, String dbName)
	{
		morphia = new Morphia();
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("de.jibin.db.entity");
		// create the Datastore connecting to the default port on the local host
		datastore = morphia.createDatastore(new MongoClient(), dbName);
		datastore.ensureIndexes();
	}
	
	public DatabaseConnection(String ip, int port, String dbName, String user, String pw)
	{
		morphia = new Morphia();
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("de.jibin.db.entity");
		// create the Datastore connecting to the default port on the local host
		MongoCredential credential = MongoCredential.createCredential(user, dbName, pw.toCharArray());
		ServerAddress server = new ServerAddress(ip, port);
		MongoClient client = new MongoClient(server,  Arrays.asList(credential));
		datastore = morphia.createDatastore(client, dbName);
		datastore.ensureIndexes();
	}
	
	public Datastore getDataStore()
	{
		return this.datastore;
	}
	
	public void close()
	{
		datastore.getMongo().close();
	}
}
