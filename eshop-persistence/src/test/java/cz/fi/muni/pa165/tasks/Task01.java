package cz.fi.muni.pa165.tasks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;


@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task01 extends AbstractTestNGSpringContextTests {
	
	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void categoryTest() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Category cat = new Category();
		cat.setName("Test");
		em.persist(cat);
		em.getTransaction().commit();
		em.close();
		//TODO under this line: create a second entity manager in categoryTest, use find method to find the category and assert its name.
                em = emf.createEntityManager();
                Category fCat = em.find(Category.class, cat.getId());
                Assert.assertEquals(fCat.getName(), "Test");
                em.close();
	}

}
