package task8;

import java.util.*;

public class AnimalShelter {

    List<Dog> dogs = new ArrayList<>();
    List<Animal> otherAnimals = new ArrayList<>();

    public void addAnimals(List<? extends Dog> newDogs) {
        dogs.addAll(newDogs);
    }

    public void addOtherAnimal(Animal a) {
        otherAnimals.add(a);
    }

    public void printAnimalSounds() {

        for (Dog d : dogs) {
            d.makeSound();
        }

        for (Animal a : otherAnimals) {
            a.makeSound();
        }
    }
}
