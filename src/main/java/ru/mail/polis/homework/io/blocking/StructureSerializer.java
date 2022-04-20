package ru.mail.polis.homework.io.blocking;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и написать сравнительное тестирование 2-ух способов записи и чтения.
 * Для тестирования надо создать список из 10 разных структур (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Список и объекты внутри лучше копировать, чтобы у вас не было ссылок на одни и те же объекты.
 *
 * Далее этот список надо прочитать из файла.
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 2 теста,
 * за правильную генерацию данных 1 тугрик, 1 тугрик за каждый тест и 1 тугрик за правильное объяснение результатов)
 * Для тестов создайте классы в соответствующем пакете в папке тестов. Используйте другие тесты как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class StructureSerializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param structures Список структур для сериализации
     * @param fileName файл в который "пишем" структуры
     */
    public void defaultSerialize(List<Structure> structures, String fileName) {
            if(!Files.exists(Paths.get(fileName))){
                return;
            }
            try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))){
                for(Structure structure : structures){
                    objectOutputStream.writeObject(structure);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список структур
     */
    public List<Structure> defaultDeserialize(String fileName) {
        if(!Files.exists(Paths.get(fileName))){
            return Collections.emptyList();
        }
        List<Structure> structures = new ArrayList<>();
        try(FileInputStream fileInputStream = new FileInputStream(fileName); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            while(fileInputStream.available() > 0) {
                structures.add((Structure) objectInputStream.readObject());
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return structures;
    }



    /**
     * 1 тугрик
     * Реализовать сериализацию, с помощью StructureOutputStream
     * @param structures Список структур для сериализации
     * @param fileName файл в который "пишем" структуры
     */
    public void serialize(List<Structure> structures, String fileName) {
        if(!Files.exists(Paths.get(fileName))){
            return;
        }
        try(StructureOutputStream structureOutputStream = new StructureOutputStream(new File(fileName))){
            structureOutputStream.write(structures.toArray(new Structure[0]));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать дисериализацию, с помощью StructureInputStream
     *
     * @param fileName файл из которого "читаем" животных
     * @return список структур
     */
    public List<Structure> deserialize(String fileName) {
        if(!Files.exists(Paths.get(fileName))){
            return Collections.emptyList();
        }
        List<Structure> structures = new ArrayList<>();
        try(StructureInputStream structureInputStream = new StructureInputStream(new File(fileName))){
            structures = Arrays.asList(structureInputStream.readStructures());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return structures;
    }


}
