package ru.mail.polis.homework.collections.structure;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

class Product {
    private int id;
    private String name;
    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product)) {
            return false;
        }
        Product objAsProduct = (Product) obj;
        return getId() == objAsProduct.getId() && getName().equals(objAsProduct.getName());
    }

    @Override
    public int hashCode() {
        int hash = id;
        for (int i = 0; i < name.length(); i++) {
            hash += name.charAt(i);
        }
        return hash;
    }
}

public class CustomArrayListTest {
    private static final List<Product> DEFAULT_PRODUCTS;
    static {
        DEFAULT_PRODUCTS = new ArrayList<>();
        DEFAULT_PRODUCTS.add(new Product(1, "first"));
        DEFAULT_PRODUCTS.add(new Product(2, "second"));
        DEFAULT_PRODUCTS.add(new Product(234, "wow"));
        DEFAULT_PRODUCTS.add(new Product(74, "lol"));
    }

    private static final Random RANDOM = new Random();

    private <T> List<T> createCustomArrayList(Collection<T> products) {
        List<T> list = new CustomArrayList<>();
        for (T product : products) {
            list.add(product);
        }
        return list;
    }

    @Test
    public void constructor() {
        List<Integer> list = new CustomArrayList<Integer>();
    }
    @Test
    public void contains() {
        Product p1 = new Product(1, "ficko");
        Product p2 = new Product(2, "kranz");
        Product p3 = new Product(3, "bread");
        List<Product> products = new CustomArrayList<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
        assertTrue(products.contains(new Product(3, "bread")));
        assertFalse(products.contains(new Product(4, "ficko")));
        assertFalse(products.contains(Integer.valueOf(3)));
    }
    @Test
    public void add() {
        List<Product> list = new CustomArrayList<>();
        for (int i = 0; i < DEFAULT_PRODUCTS.size(); i++) {
            assertTrue(list.add(DEFAULT_PRODUCTS.get(i)));
        }
        assertEquals(DEFAULT_PRODUCTS.size(), list.size());
    }

    @Test
    public void addByIndex() {

        Product pr1 = new Product(1, "first");
        Product pr2 = new Product(2, "second");
        Product pr3 = new Product(3, "third");
        Product pr4 = new Product(4, "fourth");
        Product pr5 = new Product(5, "fifth");

        List<Product> list1 = new CustomArrayList<>();
        list1.add(pr1);
        list1.add(pr2);
        list1.add(pr3);
        list1.add(pr4);
        list1.add(pr5);

        List<Product> list2 = new CustomArrayList<>();
        list2.add(pr1);
        list2.add(pr2);
        list2.add(pr4);
        list2.add(pr5);

        list2.add(2, pr3);

        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
    }

    @Test
    public void addByIndexToEnd() {
        List<Product> list1 = createCustomArrayList(DEFAULT_PRODUCTS);
        List<Product> list2 = createCustomArrayList(DEFAULT_PRODUCTS);
        Product pr = new Product(13, "test_name");
        list1.add(list1.size(), pr);
        list2.add(pr);
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addByNotExistingIndex() {
        List<Product> list = new CustomArrayList<>();
        list.add(1, new Product(1, "test_name"));
    }

    @Test
    public void get() {
        List<Product> products = new CustomArrayList<>();
        for (int i = 0; i < DEFAULT_PRODUCTS.size(); i++) {
            products.add(DEFAULT_PRODUCTS.get(i));
            assertEquals(DEFAULT_PRODUCTS.get(i), products.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getOutOfSize() {
        CustomArrayList<Product> products = new CustomArrayList<>();
        products.get(0);
    }

    @Test
    public void set() {
        List<Product> products = createCustomArrayList(DEFAULT_PRODUCTS);
        final int indexToSet = 3;
        assertEquals(
                DEFAULT_PRODUCTS.get(indexToSet),
                products.set(indexToSet, new Product(4, "test"))
        );
        assertEquals(new Product(4, "test"), products.get(indexToSet));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setOutOfSize() {
        List<Product> products = new CustomArrayList<>();
        products.set(0, new Product(4, "test"));
    }

    @Test
    public void remove() {
        Product pr1 = new Product(1, "first");
        Product pr2 = new Product(2, "second");
        Product pr3 = new Product(3, "third");
        List<Product> list1 = new CustomArrayList<>();

        list1.add(pr1);
        list1.add(pr3);

        List<Product> list2 = new CustomArrayList<>();
        list2.add(pr1);
        list2.add(pr2);
        list2.add(pr3);

        assertEquals(list2.remove(1), pr2);
        assertEquals(list2.size(), 2);

        for (int i = 0; i < list2.size(); i++) {
            assertEquals(list1.get(i), list2.get(i));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeFromEmptyList() {
        List<Product> products = new CustomArrayList<>();
        products.remove(0);
    }

    @Test
    public void containsAll() {
        List<Product> list1 = createCustomArrayList(DEFAULT_PRODUCTS);
        List<Product> list2 = new ArrayList<>(DEFAULT_PRODUCTS);
        list1.add(new Product(219, "random"));
        assertTrue(list1.containsAll(list2));
        list2.add(new Product(9124, "random 2"));
        assertFalse(list1.containsAll(list2));
    }

    @Test
    public void indexOf() {
        List<Product> products = createCustomArrayList(DEFAULT_PRODUCTS);
        int index = RANDOM.nextInt(DEFAULT_PRODUCTS.size());
        assertEquals(index, products.indexOf(DEFAULT_PRODUCTS.get(index)));
    }

    @Test
    public void addAll() {
        List<Product> products = new CustomArrayList<>();
        for (int i = 0; i < 2; i++) {
            products.add(DEFAULT_PRODUCTS.get(i));
        }
        products.add(DEFAULT_PRODUCTS.get(DEFAULT_PRODUCTS.size() - 1));
        assertTrue(products.addAll(2, DEFAULT_PRODUCTS.subList(2, DEFAULT_PRODUCTS.size() - 1)));
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i), DEFAULT_PRODUCTS.get(i));
        }
    }

    @Test
    public void removeObject() {
        List<Product> products = createCustomArrayList(DEFAULT_PRODUCTS);
        assertTrue(products.remove(DEFAULT_PRODUCTS.get(0)));
        for (int i = 1; i < DEFAULT_PRODUCTS.size(); i++) {
            assertEquals(products.get(i - 1), DEFAULT_PRODUCTS.get(i));
        }
    }

    @Test
    public void removeAll() {
        List<Product> products = createCustomArrayList(DEFAULT_PRODUCTS);
        assertTrue(products.removeAll(DEFAULT_PRODUCTS));
        assertTrue(products.isEmpty());
    }

    @Test
    public void iterator() {
        List<Product> products = createCustomArrayList(DEFAULT_PRODUCTS);
        int i = 0;
        for (Product product : products) {
            assertEquals(product, DEFAULT_PRODUCTS.get(i));
            i++;
        }
    }

    @Test
    public void subList() {
        List<Product> list1 = createCustomArrayList(DEFAULT_PRODUCTS);
        List<Product> list1Sublist = list1.subList(0, 3);
        List<Product> defaultProductsSublist = DEFAULT_PRODUCTS.subList(0, 3);
        for (int i = 0; i < list1Sublist.size(); i++) {
            assertEquals(list1Sublist.get(i), defaultProductsSublist.get(i));
        }
    }

    @Test
    public void retainAll() {
        List<Product> products = createCustomArrayList(DEFAULT_PRODUCTS);
        List<Product> productsToSave = DEFAULT_PRODUCTS.subList(0, 3);
        assertTrue(products.retainAll(productsToSave));
        assertTrue(DEFAULT_PRODUCTS.retainAll(productsToSave));
        for (int i = 0; i < DEFAULT_PRODUCTS.size(); i++) {
            assertEquals(products.get(i), DEFAULT_PRODUCTS.get(i));
        }
    }
}
