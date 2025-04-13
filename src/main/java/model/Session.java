package model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import model.categories.Animal;
import model.categories.Category;
import model.categories.Food;
import model.categories.Hobby;
import model.categories.Science;
import model.categories.Sports;

// Älä tee Sessionista final (Ei voi mockata testeissä)
public class Session {
    private static final  List<Animal> animals = List.copyOf(EnumSet.allOf(Animal.class));
    private static final  List<Food> foods = List.copyOf(EnumSet.allOf(Food.class));
    private static final  List<Hobby> hobbies = List.copyOf(EnumSet.allOf(Hobby.class));
    private static final  List<Science> sciences = List.copyOf(EnumSet.allOf(Science.class));
    private static final  List<Sports> sports = List.copyOf(EnumSet.allOf(Sports.class));

    private final Participant participant;
    private final List<Category> participantInterests;

    public Session(Participant participant) {
        if (participant == null) {
            System.err.println("ERROR: Participant is null in Session constructor");
            throw new IllegalArgumentException("Participant cannot be null");
        }
        this.participant = participant;
        participantInterests = new ArrayList<>();
    }

    public static List<Animal> getAnimals() {
        return animals;
    }

    public static List<Food> getFoods() {
        return foods;
    }

    public static List<Hobby> getHobbies() {
        return hobbies;
    }

    public static List<Science> getSciences() {
        return sciences;
    }

    public static List<Sports> getSports() {
        return sports;
    }

    public void addParticipantInterest(Category interest) {
        participantInterests.add(interest);
    }

    public void removeParticipantInterest(Category interest) {
        participantInterests.remove(interest);
    }

    public List<Category> getParticipantInterests() {
        return participantInterests;
    }

    public Participant getParticipant() {
        return participant;
    }
}
