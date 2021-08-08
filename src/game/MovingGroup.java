package game;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Перемещающаяся группа.
 */
public class MovingGroup {
    private int from;
    private int to;
    private int count;
    private int stepsLeft;
    private int ownerTeam;

    public MovingGroup() {

    }

    public int getFrom() {
        return from;
    }

    public MovingGroup setFrom(int from) {
        this.from = from;
        return this;
    }

    public MovingGroup(int from, int to, int count){
        this.from = from;
        this.to = to;
        this.count = count;
    }

    public int getTo() {
        return to;
    }

    public MovingGroup setTo(int to) {
        this.to = to;
        return this;
    }

    public int getCount() {
        return count;
    }

    public MovingGroup setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStepsLeft() {
        return stepsLeft;
    }

    public MovingGroup setStepsLeft(int stepsLeft) {
        this.stepsLeft = stepsLeft;
        return this;
    }

    public int getOwnerTeam() {
        return ownerTeam;
    }

    public MovingGroup setOwnerTeam(int ownerTeam) {
        this.ownerTeam = ownerTeam;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovingGroup that = (MovingGroup) o;
        return from == that.from &&
                to == that.to &&
                count == that.count &&
                stepsLeft == that.stepsLeft &&
                ownerTeam == that.ownerTeam;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, count, stepsLeft, ownerTeam);
    }

    @Override
    public String toString() {
        return "MovingGroup{" +
                ", from=" + from +
                ", to=" + to +
                ", count=" + count +
                ", stepsLeft=" + stepsLeft +
                ", ownerTeam=" + ownerTeam +
                '}';
    }
}
