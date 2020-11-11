package ru.mail.polis.homework.io.objects;


import jdk.vm.ci.meta.Local;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 10 разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        File f = new File(fileName);
        try{
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f, true);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            for(Animal animal : animals)
                objOut.writeObject(animal);
            objOut.writeByte(0x00);
            objOut.close();
            fOut.close();
        } catch(Exception ignored){};
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        try(FileInputStream fIn = new FileInputStream(fileName)){
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            List<Animal> ret = new ArrayList<>();
            try {
                Object obj = objIn.readObject();
                while (true) {
                    ret.add((Animal) obj);
                    obj = objIn.readObject();
                }
            } catch(Exception ignored){};
            objIn.close();
            fIn.close();
            return ret;
        } catch(Exception ex){ex.printStackTrace();};
        return Collections.emptyList();
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        File f = new File(fileName);
        try{
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f, true);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            for(AnimalWithMethods animal : animals)
                objOut.writeObject(animal);
            objOut.close();
            fOut.close();
        } catch(Exception ignored){};
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        try(FileInputStream fIn = new FileInputStream(fileName)){
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            List<AnimalWithMethods> ret = new ArrayList<>();
            try {
                Object obj = objIn.readObject();
                while (true) {
                    ret.add((AnimalWithMethods) obj);
                    obj = objIn.readObject();
                }
            } catch(EOFException eof){};

            objIn.close();
            fIn.close();
            return ret;
        } catch(Exception ignored){};
        return Collections.emptyList();
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        File f = new File(fileName);
        try{
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f, true);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            for(AnimalExternalizable animal : animals)
                objOut.writeObject(animal);
            objOut.close();
            fOut.close();
        } catch(Exception ignored){};
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        try(FileInputStream fIn = new FileInputStream(fileName)){
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            List<AnimalExternalizable> ret = new ArrayList<>();
            try {
                Object obj = objIn.readObject();
                while (true) {
                    ret.add((AnimalExternalizable) obj);
                    obj = objIn.readObject();
                }
            } catch(EOFException eof){};

            objIn.close();
            fIn.close();
            return ret;
        } catch(Exception ex){ex.printStackTrace();};
        return Collections.emptyList();
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        File f = new File(fileName);
        try{
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f, true);
            DataOutputStream dOut = new DataOutputStream(fOut);
            dOut.writeInt(animals.size());
            for(Animal a : animals){
                dOut.writeUTF(a.getName());
                dOut.writeUTF(a.getSpecies());
                dOut.writeLong(a.getAdmissionDate().toInstant().toEpochMilli());
                dOut.write(a.getGroup().ordinal());
                dOut.write(a.getAge());
                dOut.writeDouble(a.getHeight());
                dOut.writeDouble(a.getLength());

                dOut.writeUTF(a.getDisease().getDiseaseName());
                dOut.write(a.getDisease().getSeverity().ordinal());

                dOut.write(a.getFavouriteFood().size());
                for(String food : a.getFavouriteFood()){
                    dOut.writeUTF(food);
                }

            }

        } catch(Exception ignored){};
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        try(FileInputStream fIn = new FileInputStream(fileName)){
            DataInputStream dIn = new DataInputStream(fIn);
            int count = dIn.readInt();
            List<Animal> ret = new ArrayList<>(count);
            for(int i = 0; i < count; ++i){
                    Animal a = new Animal();
                    a.setName(dIn.readUTF());
                    a.setSpecies(dIn.readUTF());
                    a.setAdmissionDate(Date.from(Instant.ofEpochMilli(dIn.readLong())));
                    a.setGroup(Animal.AnimalGroup.values()[dIn.read()]);
                    a.setAge(dIn.read());
                    a.setHeight(dIn.readDouble());
                    a.setLength(dIn.readDouble());

                    Disease d = new Disease();
                    d.setDiseaseName(dIn.readUTF());
                    d.setSeverity(Disease.DiseaseSeverity.values()[dIn.read()]);
                    a.setDisease(d);

                    int foodCount = dIn.read();
                    List<String> favFood = new ArrayList<>(foodCount);
                    for(int j = 0; j < foodCount; ++j)
                        favFood.add(dIn.readUTF());
                    a.setFavouriteFood(favFood);
                    ret.add(a);
            }
            return ret;
        } catch(Exception ex){ex.printStackTrace();};
        return Collections.emptyList();
    }
}
