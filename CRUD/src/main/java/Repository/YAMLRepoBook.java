package Repository;

import Domain.Book;

import Validator.Validator;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class YAMLRepoBook extends InMemoRepo<Integer, Book> {
    private List<Book> books;
    private String filename;

    public YAMLRepoBook() {
    }

    public YAMLRepoBook(Validator<Book> validator, String fl) {
        super(validator);
        books = new ArrayList<>();
        filename = fl;
        readFromYaml();
    }


    public void readFromYaml()

    {
        Yaml yaml = new Yaml();

        try {

            InputStream ios = new FileInputStream(new File(filename));
            // Parse the YAML file and return the output as a series of Maps and Lists
            Map<String, Book> result = yaml.load(ios);

            for (Book b : result.values()) {
                entities.put(b.getIdEntity(), b);
            }
            System.out.println(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeToYaml() throws IOException {

        Map<String, Book> map = createMap();
        FileWriter writer = new FileWriter(filename);
        Yaml yaml = new Yaml();
        yaml.dump(map, writer);


    }

    private Map<String, Book> createMap() {
        Map<String, Book> map = new HashMap<>();
        for (Integer i : entities.keySet()) {

            map.put("book" + i, entities.get(i));
        }
        return map;
    }

    @Override
    public Optional<Book> save(Book entity) throws Exception {
        super.save(entity);
        writeToYaml();
        return null;
    }

    @Override
    public Optional<Book> delete(Integer id) throws Exception {
        super.delete(id);
        writeToYaml();
        return null;
    }

    @Override
    public Optional<Book> update(Book entity) throws Exception {

        super.update(entity);
        writeToYaml();
        return null;
    }

}
