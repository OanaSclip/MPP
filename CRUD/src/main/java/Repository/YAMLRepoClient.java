package Repository;

import Domain.Book;
import Domain.Client;
import Validator.Validator;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class YAMLRepoClient extends InMemoRepo<Integer, Client> {
    private String filename;

    public YAMLRepoClient() {
    }

    public YAMLRepoClient(Validator<Client> validator, String fl) {
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
            Map<String, Client> result = yaml.load(ios);

            for (Client b : result.values()) {
                entities.put(b.getIdEntity(), b);
            }
            System.out.println(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void writeToYaml() throws IOException {

        Map<String, Client> map = createMap();
        FileWriter writer = new FileWriter(filename);
        Yaml yaml = new Yaml();
        yaml.dump(map, writer);


    }

    private Map<String, Client> createMap() {
        Map<String, Client> map = new HashMap<>();
        for (Integer i : entities.keySet()) {

            map.put("client" + i, entities.get(i));
        }
        return map;
    }

    @Override
    public Optional<Client> save(Client entity) throws Exception {
        super.save(entity);
        writeToYaml();
        return null;
    }

    @Override
    public Optional<Client> delete(Integer id) throws Exception {
        super.delete(id);
        writeToYaml();
        return null;
    }

    @Override
    public Optional<Client> update(Client entity) throws Exception {

        super.update(entity);
        writeToYaml();
        return null;
    }

}
