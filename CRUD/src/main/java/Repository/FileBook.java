package Repository;


import Domain.Book;
import Exceptions.ValidatorException;
import Validator.Validator;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileBook extends InMemoRepo<Integer, Book> {

    private String filename;

    /**
     * Constructor
     *
     * @param validator BookValidator
     * @param fl        String
     * @throws Exception base exception
     */
    public FileBook(Validator<Book> validator, String fl) throws Exception {
        super(validator);
        filename = fl;
        readFile();


    }

    /**
     * Reads from a file
     *
     * @throws Exception base exception
     */
    private void readFile() throws Exception {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String[] split = st.split(",");
            Book b = new Book(Integer.parseInt(split[0]), split[1], split[2], Integer.parseInt(split[3]), split[4]);
            super.entities.put(b.getIdEntity(), b);
        }
        br.close();
    }

    /**
     * writes to a file
     *
     * @throws Exception base exception
     */
    private void writeFile() throws Exception {
        File file = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Integer i : super.entities.keySet()) {
            Book b = super.entities.get(i);
            writer.write(b.toStr());
        }
        writer.close();
    }

    /**
     * finds a book
     *
     * @param id int
     * @return whether otr not the book was found
     */
    @Override
    public Optional<Book> findOne(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return super.entities.entrySet().stream().filter(a -> a.getKey() == id).map(Map.Entry::getValue).findFirst();
        //return entities.entrySet().stream().filter(a -> a.getKey().equals(id)).map(Map.Entry::getValue).findFirst();

    }

    /**
     * iterates through the entities
     *
     * @return an iterator
     */
    @Override
    public Iterable<Book> findAll() {
        return super.entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    /**
     * whether or not the book was found
     *
     * @param entity Book
     * @return whether or not the book was found
     * @throws ValidatorException Custom -exception
     * @throws IOException        io exception
     * @throws Exception          base exception
     */
    @Override
    public Optional<Book> save(Book entity) throws ValidatorException, IOException, Exception {
        super.save(entity);
        writeFile();
        return Optional.ofNullable(super.entities.putIfAbsent(entity.getIdEntity(), entity));
    }


    /**
     * @param id int
     * @return whether or not the book was deleted
     * @throws ValidatorException Custom -exception
     * @throws IOException        io exception
     * @throws Exception          base exception
     */
    @Override
    public Optional<Book> delete(Integer id) throws ValidatorException, IOException, Exception {
        super.delete(id);
        writeFile();
        return Optional.ofNullable(super.entities.remove(id));
    }

    /**
     * @param entity Book
     * @return whether or not the book was deleted
     * @throws ValidatorException Custom -exception
     * @throws IOException        io exception
     * @throws Exception          base exception
     */
    @Override
    public Optional<Book> update(Book entity) throws ValidatorException, IOException, Exception {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(super.entities.computeIfPresent(entity.getIdEntity(), (k, v) -> entity));
    }

}
