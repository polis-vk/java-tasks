package ru.mail.polis.homework.io.objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SerializerTest {

    private final List<Animal> animals = new ArrayList<>(Arrays.asList(
            Animal.newBuilder().setAge(15).setAnimalKind(AnimalKind.CAT).build(),
            Animal.newBuilder().setName("Yarik").setAge(13).build(),
            Animal.newBuilder().addLocations("Tokyo", "St.Petersburg").build(),
            Animal.newBuilder().setName("Vadim").setAge(15).setColour(Colour.BLUE).build()
            ));

    private final List<AnimalExternalizable> animalsExternalizable = new ArrayList<>(Arrays.asList(
            AnimalExternalizable.newBuilder().setAge(15).setAnimalKind(AnimalKind.ELEPHANT).build(),
            AnimalExternalizable.newBuilder().setName("Yarik").setAge(13).build(),
            AnimalExternalizable.newBuilder().addLocations("Tokyo", "St.Petersburg").build(),
            AnimalExternalizable.newBuilder().setName("Vadim").setAge(15).setColour(Colour.BLUE).build()
    ));

    private final Serializer serializer = new Serializer();

    private final String BIN_DEFAULT = "default.bin";
    private final String BIN_EXTERNAL = "externalizable.bin";

    @Test
    @Order(1)
    public void defaultSerialize() throws IOException {
        serializer.defaultSerialize(animals, BIN_DEFAULT);
    }

    @Test
    @Order(2)
    public void defaultDeserialize() throws IOException, ClassNotFoundException {
        assertEquals(animals, serializer.defaultDeserialize(BIN_DEFAULT));
    }

    @Test
    @Order(3)
    public void serializeWithMethods() {
    }

    @Test
    @Order(4)
    public void deserializeWithMethods() {
    }

    @Test
    @Order(5)
    public void serializeWithExternalizable() throws IOException {
        serializer.serializeWithExternalizable(animalsExternalizable, BIN_EXTERNAL);
    }

    @Test
    @Order(6)
    public void deserializeWithExternalizable() throws IOException, ClassNotFoundException {
        assertEquals(animalsExternalizable, serializer.deserializeWithExternalizable(BIN_EXTERNAL));
    }

    /**
     * Если weight > 100, то при десериализации выбрасывается IllegalArgumentException
     * {@link ru.mail.polis.homework.io.objects.AnimalExternalizable#readExternal(ObjectInput)}
     */
    @Test
    @Order(7)
    public void deserializeWithExternalizableException() throws IOException {
        List<AnimalExternalizable> animalsExternalizableException = Collections.singletonList(
                AnimalExternalizable.newBuilder().setName("Oleg").setColour(Colour.RED).setAnimalKind(AnimalKind.ELEPHANT).setWeight(101).build()
        );
        serializer.serializeWithExternalizable(animalsExternalizableException, BIN_EXTERNAL);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                serializer.deserializeWithExternalizable(BIN_EXTERNAL)
        );
    }

    @Test
    @Order(8)
    public void customSerialize() throws IOException {
        serializer.customSerialize(animals, "custom.bin");
    }

    @Test
    @Order(9)
    public void customDeserialize() throws IOException, ClassNotFoundException {
        List<Animal> list = serializer.customDeserialize("custom.bin");
        assertEquals(animals, list);
    }
}