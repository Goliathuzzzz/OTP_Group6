package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import model.categories.Animal;
import model.categories.Category;
import model.categories.Food;
import model.categories.Hobby;
import model.categories.Science;
import model.categories.Sports;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "participants")
public abstract class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(nullable = false)
    protected String phoneNumber;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date joinDate;
    @ElementCollection
    protected List<Animal> animalInterests;
    @ElementCollection
    protected List<Food> foodInterests;
    @ElementCollection
    protected List<Hobby> hobbyInterests;
    @ElementCollection
    protected List<Science> scienceInterests;
    @ElementCollection
    protected List<Sports> sportsInterests;
    @OneToMany(mappedBy = "participant1", cascade = CascadeType.ALL)
    private Set<Match> matchesAsFirst;
    @OneToMany(mappedBy = "participant2", cascade = CascadeType.ALL)
    //ensure if a participant is deleted, all their matches are also deleted
    private Set<Match> matchesAsSecond;

    public Participant() {
    }

    public Participant(String phoneNumber, Date joinDate) {
        this.phoneNumber = phoneNumber;
        foodInterests = new ArrayList<>();
        hobbyInterests = new ArrayList<>();
        scienceInterests = new ArrayList<>();
        sportsInterests = new ArrayList<>();
        animalInterests = new ArrayList<>();
        this.joinDate = joinDate;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName(String language) {
        if (this instanceof User) {
            Optional<LocalizedUser> localizedUser = ((User) this).getLocalization(language);
            if (localizedUser.isPresent()) {
                return localizedUser.get().getUserName();
            }
            return ((User) this).getAnyUserName();
        } else {
            return "vieras" + id;
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Set<Match> getMatchesAsFirst() {
        return matchesAsFirst;
    }

    public void setMatchesAsFirst(Set<Match> matchesAsFirst) {
        this.matchesAsFirst = matchesAsFirst;
    }

    public Set<Match> getMatchesAsSecond() {
        return matchesAsSecond;
    }

    public void setMatchesAsSecond(Set<Match> matchesAsSecond) {
        this.matchesAsSecond = matchesAsSecond;
    }

    public List<Animal> getAnimalInterests() {
        return animalInterests;
    }

    public List<Food> getFoodInterests() {
        return foodInterests;
    }

    public List<Hobby> getHobbiesInterests() {
        return hobbyInterests;
    }

    public List<Science> getScienceInterests() {
        return scienceInterests;
    }

    public List<Sports> getSportsInterests() {
        return sportsInterests;
    }

    public void addAnimalInterest(Animal animal) {
        animalInterests.add(animal);
    }

    public void addFoodInterest(Food food) {
        foodInterests.add(food);
    }

    public void addScienceInterest(Science science) {
        scienceInterests.add(science);
    }

    public void addHobbiesInterest(Hobby hobby) {
        hobbyInterests.add(hobby);
    }

    public void addSportsInterest(Sports sport) {
        sportsInterests.add(sport);
    }

    public List<Category> getInterests() {
        List<Category> interests = new ArrayList<>();
        interests.addAll(animalInterests);
        interests.addAll(foodInterests);
        interests.addAll(hobbyInterests);
        interests.addAll(scienceInterests);
        interests.addAll(sportsInterests);
        return interests;
    }

    public void clearInterests() {
        animalInterests.clear();
        foodInterests.clear();
        hobbyInterests.clear();
        scienceInterests.clear();
        sportsInterests.clear();
    }
}
