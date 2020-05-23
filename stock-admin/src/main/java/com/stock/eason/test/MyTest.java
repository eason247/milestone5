package com.stock.eason.test;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import com.stock.eason.bean.User;

class MyTest {

	@Test
	void test() {

        SessionFactory factory = null;
        Session session = null;
        try {
            // 创建SessionFactory
            factory = new Configuration().configure().buildSessionFactory();
            // 创建Session
            session= factory.openSession();
        	
            session.beginTransaction(); // 开启事务
            
            User user = new User();
            user.setId(1);
            user.setCellphone("18030602535");
            session.saveOrUpdate(user);
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

}
