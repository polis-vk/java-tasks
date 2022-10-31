package ru.mail.polis.homework.io.objects;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import static org.junit.Assert.*;

public class SerializerTest {

    private static final AnimalExternalizable ANIMAL_EXAMPLE = new AnimalExternalizable("COLOR");

    @Test
    public void testSerializeWithExternalizable() throws IOException {
        FileOutputStream fos = new FileOutputStream("file.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(ANIMAL_EXAMPLE);

        oos.flush();
        oos.close();
        fos.close();
    }

    @Test
    public void testDeserializeWithExternalizable() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("file.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        AnimalExternalizable animal = (AnimalExternalizable) ois.readObject();

        assertEquals(ANIMAL_EXAMPLE, animal);

        ois.close();
        fis.close();
    }
}