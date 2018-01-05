package web.scraping.jsoup;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import web.scraping.jsoup.vo.Proceeding;

public class MongoUtils {
	
	private static final String MONGO_DB = "mongodb://andjimrio:andjimrio@ds257245.mlab.com:57245/si1718-ajr-proceedings";
	private static final String MONGO_CL = "proceedings";
	
	public static MongoDatabase database= null; 
	
	public static void initialize() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		MongoClientURI uri = new MongoClientURI(MONGO_DB);
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient(uri);
		database = mongoClient.getDatabase(uri.getDatabase()).withCodecRegistry(pojoCodecRegistry);
	}
	
	public static void updateDB(List<Proceeding> proceedings) {
		MongoCollection<Proceeding> collection = database.getCollection(MONGO_CL, Proceeding.class);
		
		for(Proceeding proceeding: proceedings) {
			BasicDBObject query = new BasicDBObject("idProceeding", proceeding.getIdProceeding());
			
			try {
				if(collection.find(query).first() == null) {
					try {
						System.out.println("\tSe crea en DB - "+proceeding.getIdProceeding());
						collection.insertOne(proceeding);
					} catch(Exception e) {
						System.out.println("\t"+e.getMessage());
					}
				} else {
					System.out.println("\tYa está en DB - "+proceeding.getIdProceeding());					
				}
			} catch (Exception e) {
				System.out.println("#ERROR: "+e.getMessage());
			}
		}
	}
}
