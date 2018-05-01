package Consolee;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Map<Integer, Command> options;

    public Menu() {
        this.options = new HashMap<>();
    }

    /**
     * Receives a Command and adds it to the Map options
     *
     * @param cmd
     */
    public void addCmd(Command cmd) {
        this.options.put(cmd.getKey(), cmd);
    }

    /**
     * Receives a String and returns the value from the Map for that String(which is the key)
     *
     * @param k
     * @return
     */
    public Command getCmd(String k) {

        if (this.options.containsKey(k))
            return this.options.get(k);
        return null;
    }

    /**
     * Prints all the values from the Map options
     */
    public void printMenu() {
        options.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).forEach(System.out::println);
        //   for(Command cmd: this.options.values())
        //       System.out.println(cmd.toString());
    }

    /**
     * Calls the printMenu method. Waits for a user input
     * and calls the exec method with the received param
     */
    public void showMenu() throws IOException {
        Scanner s = new Scanner(System.in);
        boolean go=true;
        while (go) {
            //System.in.read();
            printMenu();
            System.out.println("\nOption: ");
            Integer k = Integer.parseInt(s.nextLine());
            if(k==0)
                go=false;
            else {
                Command cmd = this.options.get(k);
                if (cmd == null)
                    System.out.println("Invalid Option!");
                else cmd.exec();
            }

        }
    }
}
