package game;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Планета.
 */
public class Planet {
    private int id;
    private int population;
    private int ownerTeam;
    private int reproduction;

    public int getId() {
        return id;
    }

    public Planet setId(int id) {
        this.id = id;
        return this;
    }

    public int getPopulation() {
        return population;
    }

    public Planet setPopulation(int population) {
        this.population = population;
        return this;
    }

    public int getOwnerTeam() {
        return ownerTeam;
    }

    public Planet setOwnerTeam(int ownerTeam) {
        this.ownerTeam = ownerTeam;
        return this;
    }

    public int getReproduction() {
        return reproduction;
    }

    public Planet setReproduction(int reproduction) {
        this.reproduction = reproduction;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet that = (Planet) o;
        return population == that.population &&
                ownerTeam == that.ownerTeam &&
                reproduction == that.reproduction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(population, ownerTeam, reproduction);
    }

    @Override
    public String toString() {
        return "Planet{" +
                "population=" + population +
                ", ownerTeam=" + ownerTeam +
                ", reproduction=" + reproduction +
                '}';
    }

    public static Planet buildPlanetState(Planet previous) {
        return new Planet()
                .setId(previous.getId())
                .setPopulation(previous.getPopulation())
                .setOwnerTeam(previous.getOwnerTeam())
                .setReproduction(previous.getReproduction());
    }

    public String asInput(){
        StringJoiner joiner = new StringJoiner(" ", "", "");
        joiner.add(String.valueOf(id));
        joiner.add(String.valueOf(ownerTeam));
        joiner.add(String.valueOf(population));
        joiner.add(String.valueOf(reproduction));
        return joiner.toString();
    }
}