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
//		mongoDB.deleteById("57078ad0889085118d752977", "javadb");
		//6.根据条件删除数据
//		DBObject jim = new BasicDBObject("name","jim");
//		mongoDB.deleteByIds(jim, "javadb");
		//7.更新操作 为集合增加属性
//		DBObject dbobject = new BasicDBObject();
//		
//		dbobject.put("$set", new BasicDBObject("email", "xiyang1321@126.com"));
//		
//		mongoDB.update(new BasicDBObject(), dbobject, false, true, "javadb");
		
		//8.查询集合中的name和age.
//		DBObject keys = new BasicDBObject();
//		keys.put("_id", false);
//		keys.put("name", true);
//		keys.put("age", true);
//		DBCursor cursor = mongoDB.find(null, keys, "javadb");
//	    while (cursor.hasNext()) {
//		    DBObject object = cursor.next();
//		    System.out.println(object.get("name"));
//	    }
	    
	    /**
		 * 8.分页例子
		 */
		DBCursor cursor = mongoDB.find(null, null, 0, 3, "javadb");
	    while (cursor.hasNext()) {
		    DBObject object = cursor.next();
		    System.out.print(object.get("name")+"-->");
		    System.out.print(object.get("age")+"-->");
		    System.out.println(object.get("e"));
	    }		
		//关闭连接对象
		connection.close();
	}

	/**
	 * 查询器(分页)
	 * @param ref
	 * @param keys
	 * @param start
	 * @param limit
	 * @return
	 */
	public DBCursor find(DBObject ref, 
			DBObject keys,
			int start,
			int limit,
			String collName){
		DBCursor cur = find(ref, keys, collName);
		return cur.limit(limit).skip(start);
	}
	/**
	 * 查询器(不分页)
	 * @param ref
	 * @param keys
	 * @param start
	 * @param limit
	 * @param collName
	 * @return
	 */
	public DBCursor find(DBObject ref,
			DBObject keys,
			String collName){
		//1.得到集合
		DBCollection coll = db.getCollection(collName);
		DBCursor cur = coll.find(ref, keys);
		return cur;
	}
	
	/**
	 * @param _find 查询器
	 * @param _dbObject 更新器
	 * @param upsert 更新或者插入
	 * @param multi 是否批量更新
	 * @param _collectionName 集合名称
	 * @return
	 */
	private int update(DBObject _find,DBObject _dbObject, boolean upsert, boolean multi, String _collectionName){
		if (db.collectionExists(_collectionName)) {
			DBCollection dbCollection = db.getCollection(_collectionName);
			dbCollection.update(_find, _dbObject, upsert, multi);
		}
		return 0;
	}
	private int deleteByIds(DBObject _object, String _collectionName) {
		if (db.collectionExists(_collectionName)) {
			DBCollection dbCollection = db.getCollection(_collectionName);
			WriteResult writeResult = dbCollection.remove(_object);
			return writeResult.getN();
		}
		return 0;
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
