package com.ty.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

//@RepositoryDefinition(domainClass=Person.class,idClass=Integer.class)
public interface PersonRepository extends PagingAndSortingRepository<Person, Integer>,JpaSpecificationExecutor<Person> ,PersonDao{
	
	//通过名字进行查询
	Person getByLastName(String lastName);

	//通过名字的开始字母，和id最大值
	List<Person> getByLastNameStartingWithAndIdLessThan(String lastName, Integer id);

	List<Person> getByLastNameEndingWithAndIdLessThan(String lastName, Integer id);

	//id大于id值的人
	List<Person> getByAddress_IdGreaterThan(Integer id);

	//查询id值最大的人
	@Query("SELECT p FROM Person p WHERE p.id = (SELECT max(p2.id) FROM Person p2)")
	public Person getMaxIdPerson();

	@Query("SELECT p FROM Person p WHERE p.lastName = ?1 AND p.email=?2")
	List<Person> getbyLastNameAndEmail(String lastName, String email);

	@Query("SELECT p FROM Person p WHERE p.lastName = :lastName AND p.email= :email")
	List<Person> getbyLastName1AndEmail1(@Param("email") String email, @Param("lastName") String lastName);

	@Query("SELECT p FROM Person p WHERE p.lastName LIKE %?1% OR p.email LIKE %?2%")
	List<Person> getTestAnnotationLikeParam(String lastName, String email);

	@Query(value = "SELECT count(id) FROM jpa_persons", nativeQuery = true)
	Long getIdCount();

	@Modifying
	@Query("UPDATE Person p SET p.email = :email WHERE p.id = :id")
	public void updatePersonEmail(@Param("email") String email, @Param("id") Integer id);

	@Modifying
	@Query("DELETE Person p WHERE p.id = :id")
	public void deletePerson(@Param("id") Integer id);

	@Modifying
	@Query(value = "insert into jpa_persons(email,lastName) value(?1,?2)", nativeQuery = true)
	public void savePerson(String email,String lastName);
}
