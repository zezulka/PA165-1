package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Category electro = new Category();
    private Category kitchen = new Category();
    private Product flashlight = new Product();
    private Product robot = new Product();
    private Product plate = new Product();

    @BeforeClass
    private void setup() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        electro.setName("Electro");
        kitchen.setName("Kitchen");

        em.persist(electro);
        em.persist(kitchen);

        flashlight.setName("Flashlight");
        robot.setName("Kitchen robot");
        plate.setName("Plate");

        flashlight.addCategory(electro);
        robot.addCategory(electro);
        robot.addCategory(kitchen);
        plate.addCategory(kitchen);

        em.persist(flashlight);
        em.persist(robot);
        em.persist(plate);

        em.getTransaction().commit();

        em.close();
    }

    //task 5
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testDoesntSaveNullName() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product p = new Product();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    private void assertContainsCategoryWithName(Set<Category> categories,
            String expectedCategoryName) {
        for (Category cat : categories) {
            if (cat.getName().equals(expectedCategoryName)) {
                return;
            }
        }

        Assert.fail("Couldn't find category " + expectedCategoryName + " in collection " + categories);
    }

    private void assertContainsProductWithName(Set<Product> products,
            String expectedProductName) {

        for (Product prod : products) {
            if (prod.getName().equals(expectedProductName)) {
                return;
            }
        }

        Assert.fail("Couldn't find product " + expectedProductName + " in collection " + products);
    }

    @Test
    public void electroCat() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Category c = em.find(Category.class, electro.getId());
        assertContainsProductWithName(c.getProducts(), flashlight.getName());
        assertContainsProductWithName(c.getProducts(), robot.getName());
        em.close();
    }

    @Test
    public void kitchenCat() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Category c = em.find(Category.class, kitchen.getId());
        assertContainsProductWithName(c.getProducts(), plate.getName());
        assertContainsProductWithName(c.getProducts(), robot.getName());
        em.close();
    }

    @Test
    public void flashlight() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product p = em.find(Product.class, flashlight.getId());
        Assert.assertEquals(p.getCategories().size(), 1);
        assertContainsCategoryWithName(p.getCategories(), electro.getName());
        em.close();
    }

    @Test
    public void robot() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product p = em.find(Product.class, robot.getId());
        Assert.assertEquals(p.getCategories().size(), 2);
        assertContainsCategoryWithName(p.getCategories(), electro.getName());
        assertContainsCategoryWithName(p.getCategories(), kitchen.getName());
        em.close();
    }

    @Test
    public void plate() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product p = em.find(Product.class, robot.getId());
        Assert.assertEquals(p.getCategories().size(), 2);
        assertContainsCategoryWithName(p.getCategories(), electro.getName());
        assertContainsCategoryWithName(p.getCategories(), kitchen.getName());
        em.close();
    }
}
