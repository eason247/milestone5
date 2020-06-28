package com.stock.eason.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.stock.eason.bean.User;

public class DBUtil {
	
	public static void saveOrUpdate(Object obj) {
        SessionFactory factory = null;
        Session session = null;
        try {
            // 创建SessionFactory
            factory = new Configuration().configure().buildSessionFactory();
            // 创建Session
            session= factory.openSession();
        	
            session.beginTransaction(); // 开启事务
            
            session.saveOrUpdate(obj);
            session.getTransaction().commit(); // 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();; // 事务滚
        } finally {
            if (session != null) {
                session.close();
            }
            if (factory != null) {
                factory.close();
            }
        }

	}

	public static Object selectById(Integer id,Class clazz) {
        SessionFactory factory = null;
        Session session = null;
        try {
            // 创建SessionFactory
            factory = new Configuration().configure().buildSessionFactory();
            // 创建Session
            session= factory.openSession();
        	
            session.beginTransaction(); // 开启事务
            
            Object o=session.get(clazz, id);
            
            session.getTransaction().commit(); // 提交事务
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();; // 事务滚
        } finally {
            if (session != null) {
                session.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
		return null;

	}
	
	public static ArrayList selectByParam(String sql, ArrayList<String> params) {
        SessionFactory factory = null;
        Session session = null;
        try {
            // 创建SessionFactory
            factory = new Configuration().configure().buildSessionFactory();
            // 创建Session
            session= factory.openSession();
        	
            session.beginTransaction(); // 开启事务
            
            //HRL查询，查询全部信息，注意HRL查询的是实体类的名称，不是数据表的名称，特别注意这一点
            //Query q=session.createQuery("from User");
            Query q=session.createSQLQuery(sql).addEntity(User.class);
            for(int i=0;i<params.size();i++) {
            	q.setParameter(i, params.get(i));
            }

            ArrayList list = (ArrayList) q.list();
            System.out.println(list);
         
            
            session.getTransaction().commit(); // 提交事务
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();; // 事务滚
        } finally {
            if (session != null) {
                session.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
		return null;

	}
	
	
	public static Object deleteById(Integer id,Object obj) {
        SessionFactory factory = null;
        Session session = null;
        try {
            // 创建SessionFactory
            factory = new Configuration().configure().buildSessionFactory();
            // 创建Session
            session= factory.openSession();
        	
            session.beginTransaction(); // 开启事务
            
            Object o=session.load(Object.class, id);
            session.delete(o);
            session.getTransaction().commit(); // 提交事务
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();; // 事务滚
        } finally {
            if (session != null) {
                session.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
		return null;

	}
	
}
