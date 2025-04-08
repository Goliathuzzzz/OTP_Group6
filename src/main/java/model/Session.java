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

// Tarvitseeko edes laittaa tietokantaan?
// On nyt se tapahtuma jossa valitaan kiinnostuksenkohteet näytöltä
public final class Session {
    // Jotta kategoriat saadaan viewhun
    private final static List<Animal> animals = List.copyOf(EnumSet.allOf(Animal.class));
    private final static List<Food> foods = List.copyOf(EnumSet.allOf(Food.class));
    private final static List<Hobby> hobbies = List.copyOf(EnumSet.allOf(Hobby.class));
    private final static List<Science> sciences = List.copyOf(EnumSet.allOf(Science.class));
    private final static List<Sports> sports = List.copyOf(EnumSet.allOf(Sports.class));

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

    // Nämä valintabokseja varten
    public void addParticipantInterest(Category interest) {
        participantInterests.add(interest);
    }

    public void removeParticipantInterest(Category interest) {
        participantInterests.remove(interest);
    }

    // Matcheria varten
    public List<Category> getParticipantInterests() {
        return participantInterests;
    }

    public Participant getParticipant() {
        return participant;
    }
}
