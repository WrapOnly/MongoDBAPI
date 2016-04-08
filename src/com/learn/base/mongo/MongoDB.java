package com.learn.base.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class MongoDB {

	//1.创建一个Mongo的数据库连接对象
	public static Mongo connection = null;
	
	public static DB db = null;
	
	public MongoDB(String _databaseName) throws UnknownHostException{
		connection 	= new Mongo("127.0.0.1:27017");
		db 			= connection.getDB(_databaseName);
	}
	
	
	public static void main(String[] args) throws UnknownHostException {
		//1.实例化
		MongoDB mongoDB = new MongoDB("foobar");
		
		//2.创建一个名叫javadb的数据库集合
//		mongoDB.createCollection("javadb");
		
		//3.为这个集合添加一条数据
//		DBObject dbObject = new BasicDBObject(0);
//		dbObject.put("name", "tian");
//		dbObject.put("age", 24);
//		ArrayList<String> books = new ArrayList<String>(0);
//		books.add("Java");
//		books.add("Android");
//		books.add("IOS");
//		dbObject.put("books", books);
//		mongoDB.insert(dbObject, "javadb");
		
		//4.批量插入
//		ArrayList<DBObject> dbObjects = new ArrayList<DBObject>(0);
//		
//		DBObject jimObject = new BasicDBObject("name", "jim");
//		DBObject tomObject = new BasicDBObject("name", "tom");
//		dbObjects.add(jimObject);
//		dbObjects.add(tomObject);
//		mongoDB.insertBatch(dbObjects, "javadb");
		
		//5.根据Id进行删除
		mongoDB.deleteById("57078ad0889085118d752977", "javadb");
		
	}

	private int deleteById(String _objectId, String _collectionName) {
		if (db.collectionExists(_collectionName)) {
			DBCollection dbCollection = db.getCollection(_collectionName);
			DBObject dbObject = new BasicDBObject("_id", new ObjectId(_objectId));
			WriteResult writeResult = dbCollection.remove(dbObject);
			return writeResult.getN();
		}
		return 0;
	}


	/**
	 * 创建一个数据库集合
	 * @param _collectionName 数据库集合名字
	 * @return 数据库实例
	 */
	public void createCollection(String _collectionName){
		DBObject dbObject = new BasicDBObject();
		db.createCollection(_collectionName, dbObject);
	}
	
	public void insert(DBObject _dbObject, String _collectionName){
		if (db.collectionExists(_collectionName)) {
			DBCollection dbCollection = db.getCollection(_collectionName);
			dbCollection.insert(_dbObject);
			System.out.println(_collectionName +" success");
		}
		else {
			System.out.println(_collectionName +"不存在");
		}
		
	}
	
	public void insertBatch(List<DBObject> _dbObjects, String _collectionName){
		if (db.collectionExists(_collectionName)) {
			DBCollection dbCollection = db.getCollection(_collectionName);
			dbCollection.insert(_dbObjects);
			System.out.println(_collectionName +" success");
		}
		else {
			System.out.println(_collectionName +"不存在");
		}
	}
}
