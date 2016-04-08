package com.learn.base;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class DataBase {

	public static void main(String[] args) {
		/**
		 * 1.建立一个Mongo的数据库连接对象
		 * 2.查询所有的DatabaseName
		 * 3.创建相关数据库（foobar）的连接
		 * 4.查询数据库所有的集合的名字
		 */
		try {
			//1.建立一个Mongo的数据库连接对象
			Mongo mongo = new Mongo("127.0.0.1:27017");
			
			//2.查询所有的DatabaseName
			List<String> databaseName = mongo.getDatabaseNames();
			for (String string : databaseName) {
				System.out.println(string);
			}
			System.out.println("------");
			//3.创建相关数据库（foobar）的连接
			DB db = mongo.getDB("foobar"); 
			//4.查询数据库所有的集合的名字
			Set<String> collectionNames = db.getCollectionNames();
			for (String string : collectionNames) {
				System.out.println(string);
			}
			System.out.println("------");
			
			//5.查询所有的数据
			DBCollection users = db.getCollection("foobar");
			
			DBCursor dbCursor = users.find();
			while (dbCursor.hasNext()) {
				DBObject dbObject = dbCursor.next();
				System.out.println(dbObject.get("name"));
			}
			//6.其他操作
			System.out.println("------");
			System.out.println(dbCursor.count());
			System.out.println(dbCursor.getCursorId());
			System.out.println(JSON.serialize(dbCursor));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
