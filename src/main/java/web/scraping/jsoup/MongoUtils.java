package web.scraping.jsoup;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.Block;
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
	
	public static void populateDB(List<Proceeding> proceedings, Boolean print) {
		MongoCollection<Proceeding> collection = database.getCollection(MONGO_CL, Proceeding.class);
		
		try {
			collection.insertMany(proceedings);
		} catch(Exception e) {
			System.out.println("\t"+e.getMessage());
		}
		
		if(print) {
			Block<Proceeding> printBlock = new Block<Proceeding>() {
			    @Override
			    public void apply(final Proceeding proceeding) {
			        System.out.println(proceeding);
			    }
			};
	
			collection.find().forEach(printBlock);
		}
		
	}
}
