package com.ty.entity;

import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.context.ApplicationContext;

public class PersonImp implements PersonDao {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	@Override
	public void test() {
		//1.创建EntitymanagerFactory
		String persistenceUnitName = "jpa-1";
		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		//2.创建EntityManager
		entityManager = entityManagerFactory.createEntityManager();
		System.out.println(entityManager);
//		Person person = entityManager.find(Person.class, 11);
//		System.out.println(person);
	}
}
