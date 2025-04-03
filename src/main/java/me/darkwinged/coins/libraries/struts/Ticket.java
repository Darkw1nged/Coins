package me.darkwinged.coins.libraries.struts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ticket {

    private final Account owner;
    private final List<Integer> numbers;

    public Ticket(Account account) {
        this.owner = account;
        this.numbers = new ArrayList<>();

        Random random = new Random();
        int generatedNumbers=0;
        while (generatedNumbers < 6) {
            int number = random.nextInt(59);
            if (numbers.contains(number)) continue;
            this.numbers.add(number);
            generatedNumbers++;
        }
    }

    // ---- [ Getters ] ----
    public Account getOwner() {
        return this.owner;
    }

    public List<Integer> getNumbers() {
        return this.numbers;
    }

}
