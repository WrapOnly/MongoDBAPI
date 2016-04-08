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
		 * 1.����һ��Mongo�����ݿ����Ӷ���
		 * 2.��ѯ���е�DatabaseName
		 * 3.����������ݿ⣨foobar��������
		 * 4.��ѯ���ݿ����еļ��ϵ�����
		 */
		try {
			//1.����һ��Mongo�����ݿ����Ӷ���
			Mongo mongo = new Mongo("127.0.0.1:27017");
			
			//2.��ѯ���е�DatabaseName
			List<String> databaseName = mongo.getDatabaseNames();
			for (String string : databaseName) {
				System.out.println(string);
			}
			System.out.println("------");
			//3.����������ݿ⣨foobar��������
			DB db = mongo.getDB("foobar"); 
			//4.��ѯ���ݿ����еļ��ϵ�����
			Set<String> collectionNames = db.getCollectionNames();
			for (String string : collectionNames) {
				System.out.println(string);
			}
			System.out.println("------");
			
			//5.��ѯ���е�����
			DBCollection users = db.getCollection("foobar");
			
			DBCursor dbCursor = users.find();
			while (dbCursor.hasNext()) {
				DBObject dbObject = dbCursor.next();
				System.out.println(dbObject.get("name"));
			}
			//6.��������
			System.out.println("------");
			System.out.println(dbCursor.count());
			System.out.println(dbCursor.getCursorId());
			System.out.println(JSON.serialize(dbCursor));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
