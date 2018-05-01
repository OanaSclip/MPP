package Repository;

import Domain.Book;
import Domain.Inventory;
import Validator.Validator;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class YAMLRepoInventory extends InMemoRepo<Integer, Inventory> {
    private String filename;
    public YAMLRepoInventory() {
    }

    public YAMLRepoInventory(Validator<Inventory> validator, String fl) {
        super(validator);
        filename = fl;
        try {
            writeToYaml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFromYaml();
    }


    public void readFromYaml()

    {
        Yaml yaml = new Yaml();

        try {

            InputStream ios = new FileInputStream(new File(filename));
            // Parse the YAML file and return the output as a series of Maps and Lists
            Map<String, Inventory> result = yaml.load(ios);

            for (Inventory b : result.values()) {
                entities.put(b.getIdEntity(), b);
            }
            System.out.println(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeToYaml() throws IOException {

        Map<String, Inventory> map = createMap();
        FileWriter writer = new FileWriter(filename);
        Yaml yaml = new Yaml();
        yaml.dump(map, writer);


    }

    private Map<String, Inventory> createMap() {
        Map<String, Inventory> map = new HashMap<>();
        for (Integer i : entities.keySet()) {

            map.put("product" + i, entities.get(i));
        }
        return map;
    }

    @Override
    public Optional<Inventory> save(Inventory entity) throws Exception {
        super.save(entity);
        writeToYaml();
        return null;
    }

    @Override
    public Optional<Inventory> delete(Integer id) throws Exception {
        super.delete(id);
        writeToYaml();
        return null;
    }

    @Override
    public Optional<Inventory> update(Inventory entity) throws Exception {

        super.update(entity);
        writeToYaml();
        return null;
    }

}
