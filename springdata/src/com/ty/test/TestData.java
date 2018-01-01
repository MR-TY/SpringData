package com.ty.test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import com.ty.entity.Person;
import com.ty.entity.PersonRepository;
import com.ty.service.PersonService;

public class TestData {
	private PersonRepository personRepository;
	private PersonService personService;
	private ApplicationContext ctx;

	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		personRepository = ctx.getBean(PersonRepository.class);
		personService = ctx.getBean(PersonService.class);
	}

	@Test
	public void TestEntityManagerFactory() {

	}

	@Test
	public void TestHelloWorld() {
		Person person = personRepository.getByLastName("rr");
		System.out.println(person);
	}

	@Test
	public void TestKeyWords() {
		List<Person> persons = personRepository.getByLastNameStartingWithAndIdLessThan("a", 6);
		System.out.println(persons);
	}

	@Test
	public void test() {
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		System.out.println(dataSource);
	}

	@Test
	public void testEndWith() {
		List<Person> persons = personRepository.getByLastNameEndingWithAndIdLessThan("f", 6);
		System.out.println(persons);
	}

	@Test
	public void testGreater() {
		List<Person> persons = personRepository.getByAddress_IdGreaterThan(0);
		System.out.println(persons.size());
	}

	@Test
	public void testMax() {
		Person person = personRepository.getMaxIdPerson();
		System.out.println(person);
	}

	@Test
	public void testPerson() {
		List<Person> persons = personRepository.getbyLastNameAndEmail("aa", "@234");
		System.out.println(persons);
	}

	@Test
	public void testPerson1() {
		List<Person> persons = personRepository.getbyLastName1AndEmail1("@234", "aa");
		System.out.println(persons);
	}

	@Test
	public void testLikePerson() {
		List<Person> persons = personRepository.getTestAnnotationLikeParam("a", "");
		System.out.println(persons);
	}

	@Test
	public void testIdCount() {
		System.out.println(personRepository.getIdCount());
	}

	@Test
	public void testUpdateById() {
		personService.updatePerson("123", 1);
	}

	@Test
	public void deletePersonById() {
		personService.deletePerson(5);
	}

	@Test
	public void testSavePerson() {
		personService.savePerson("@36955", "唐宇");
	}

	@Test
	public void testSave() {
		List<Person> persons = new ArrayList<>();
		for (int i = 'a'; i < 'z'; i++) {
			Person person = new Person();
			person.setId(i + 1);
			person.setEmail("@" + i + "1123");
			person.setBirth(new Date());
			person.setLastName((char) i + "" + (char) i);
			persons.add(person);
		}
		personService.crudPerson(persons);
	}

	@Test
	public void testPage() {
		// -----------排序---------
		// 1.当前的页数
		int pageNo = 3-1;
		// 2.每页的大小
		int pageSize = 5;

		// Order是针对属性是升序还是降序
		Order order1 = new Order(Direction.DESC, "id");// 按照id降序的方式
		Order order2 = new Order(Direction.DESC, "email");// 按照email降序的方式
		Sort sort = new Sort(order1, order2);

		// pRequest是实现类通常封装了分页的信息
		PageRequest pRequest = new PageRequest(pageNo, pageSize, sort);
		Page<Person> page = personRepository.findAll(pRequest);
		System.out.println("当前的页数:" + (page.getNumber() + 1));
		System.out.println("总记录数：" + page.getTotalElements());
		System.out.println("总的页数：" + page.getTotalPages());
	}
	//---------------带查询的分页------------
	@Test
	public void testQueryCondiction() {
		// 1.当前的页数
		int pageNo = 3-1;
		// 2.每页的大小
		int pageSize = 5;
		PageRequest pRequest = new PageRequest(pageNo, pageSize);
		Specification<Person> specification = new Specification<Person>() {

			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> arg1, CriteriaBuilder arg2) {
				Path path = (Path) root.get("id");
				Predicate predicate = arg2.gt((Expression<? extends Number>) path, 5);
				return predicate;
			}
		};
		Page<Person> page = personRepository.findAll(specification,pRequest);
		
		// pRequest是实现类通常封装了分页的信息
				System.out.println("当前的页数:" + (page.getNumber() + 1));
				System.out.println("总记录数：" + page.getTotalElements());
				System.out.println("总的页数：" + page.getTotalPages());
	}
	@Test
	public void testCustomer(){
		personRepository.test();
	}
}
