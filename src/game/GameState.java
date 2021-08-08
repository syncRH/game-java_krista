package game;

import java.util.*;

/**
 * Состояние игры на начало хода.
 */
public class GameState {
    private int step;
    private int maxSteps;
    private Long timeoutMs;
    private List<Planet> planets = new ArrayList<>();
    private int[][] distMap = new int[][] {};
    private List<MovingGroup> movingGroups = new ArrayList<>();

    private List<String> input = new ArrayList<>();
    private List<String> input0 = new ArrayList<>();
    private List<String> input1 = new ArrayList<>();
    private List<String> output0 = new ArrayList<>();
    private List<String> output1 = new ArrayList<>();
    private List<String> exception0 = new ArrayList<>();
    private List<String> exception1 = new ArrayList<>();
    private Long executionTime0;
    private Long executionTime1;

    public GameState() {
        //Default
    }

    public GameState(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public int getStep() {
        return step;
    }

    public GameState setStep(int step) {
        this.step = step;
        return this;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public GameState setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
        return this;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public GameState setPlanets(List<Planet> planets) {
        this.planets = planets;
        return this;
    }

    public int[][] getDistMap() {
        return distMap;
    }

    public GameState setDistMap(int[][] distMap) {
        this.distMap = distMap;
        return this;
    }

    public List<MovingGroup> getMovingGroups() {
        return movingGroups;
    }

    public GameState setMovingGroups(List<MovingGroup> movingGroups) {
        this.movingGroups = movingGroups;
        return this;
    }


    public synchronized List<String> getInput() {
        return input;
    }

    public GameState setInput(List<String> input) {
        this.input = input;
        return this;
    }

    public List<String> getInput0() {
        return input0;
    }

    public GameState setInput0(List<String> input0) {
        this.input0 = input0;
        return this;
    }

    public List<String> getInput1() {
        return input1;
    }

    public GameState setInput1(List<String> input1) {
        this.input1 = input1;
        return this;
    }

    public List<String> getOutput0() {
        return output0;
    }

    public GameState setOutput0(List<String> output0) {
        this.output0 = output0;
        return this;
    }

    public List<String> getOutput1() {
        return output1;
    }

    public GameState setOutput1(List<String> output1) {
        this.output1 = output1;
        return this;
    }

    public List<String> getException0() {
        return exception0;
    }

    public void setException0(List<String> exception0) {
        this.exception0 = exception0;
    }

    public List<String> getException1() {
        return exception1;
    }

    public void setException1(List<String> exception1) {
        this.exception1 = exception1;
    }

    public Long getTimeoutMs() {
        return timeoutMs;
    }

    public GameState setTimeoutMs(Long timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    public Long getExecutionTime0() {
        return executionTime0;
    }

    public GameState setExecutionTime0(Long executionTime0) {
        this.executionTime0 = executionTime0;
        return this;
    }

    public Long getExecutionTime1() {
        return executionTime1;
    }

    public GameState setExecutionTime1(Long executionTime1) {
        this.executionTime1 = executionTime1;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return step == gameState.step &&
                maxSteps == gameState.maxSteps &&
                Objects.equals(timeoutMs, gameState.timeoutMs) &&
                Objects.equals(planets, gameState.planets) &&
                Arrays.deepEquals(distMap, gameState.distMap) &&
                Objects.equals(movingGroups, gameState.movingGroups) &&
                Objects.equals(input, gameState.input) &&
                Objects.equals(input0, gameState.input0) &&
                Objects.equals(input1, gameState.input1) &&
                Objects.equals(output0, gameState.output0) &&
                Objects.equals(output1, gameState.output1);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(step, maxSteps, timeoutMs, planets, movingGroups, input, input0, input1, output0, output1);
        result = 31 * result + Arrays.deepHashCode(distMap);
        return result;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "step=" + step +
                ", maxSteps=" + maxSteps +
                ", planets=" + planets +
                ", distMap=" + distMap +
                ", movingGroups=" + movingGroups +
                ", input=" + input +
                ", output0=" + output0 +
                ", output1=" + output1 +
                '}';
    }
}
