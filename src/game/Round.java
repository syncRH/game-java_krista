package game;

import java.util.*;
import java.util.stream.Collectors;
import static game.InOutUtils.*;

/**
 * Шаг игры (бот).
 */
public class Round {

    private final List<List<Integer>> world;
    private Integer teamId;
    private Integer maxSteps;
    private Integer currentStep;
    private Integer planetCount;
    private List<Planet> planets;
    private Integer distanceMapSizeIndex;
    private List<List<Integer>> distanceMap;
    private Integer movingGroupCountIndex;
    private Integer movingGroupCount;
    private List<MovingGroup> movingGroups;

    private void setTeamId(){
        if(this.teamId == null){
            if(world.get(FIRST).size() == 1) {
                this.teamId = world.get(FIRST).get(FIRST);
            } else {
                throw new IllegalStateException("Некорректный номер команды");
            }
        }
    }

    private void setMaxSteps(){
        if(this.maxSteps == null){
            if(world.get(MAX_STEPS_INDEX).size() == 1) {
                this.maxSteps = world.get(MAX_STEPS_INDEX).get(FIRST);
            } else {
                throw new IllegalStateException("Некорректное максимальное количество шагов");
            }
        }
    }

    private void setCurrentStep(){
        if(this.currentStep == null){
            if(world.get(CURRENT_STEP_INDEX).size() == 1) {
                this.currentStep = world.get(CURRENT_STEP_INDEX).get(FIRST);
            } else {
                throw new IllegalStateException("Некорректный номер шага");
            }
        }
    }

    private void setPlanetCount(){
        if(this.planetCount == null){
            if(world.get(PLANET_COUNT_INDEX).size() == 1) {
                this.planetCount = world.get(PLANET_COUNT_INDEX).get(FIRST);
            } else {
                throw new IllegalStateException("Некорректное количество планет");
            }
        }
    }

    private void setPlanets(){
        if(this.planets == null && this.planetCount > 0){
            planets = new ArrayList<>();
            List<Integer> temp;
            Planet planet;
            int firstPlanetIndex = PLANET_COUNT_INDEX + 1;
            distanceMapSizeIndex = firstPlanetIndex + planetCount;
            for(int i = PLANET_COUNT_INDEX + 1; i < distanceMapSizeIndex; i++){
                temp = world.get(i);
                planet = new Planet()
                        .setId(temp.get(PLANET_ID_INDEX))
                        .setOwnerTeam(temp.get(PLANET_OWNER_INDEX))
                        .setPopulation(temp.get(PLANET_POPULATION_INDEX))
                        .setReproduction(temp.get(PLANET_REPRODUCTION_INDEX));
                this.planets.add(planet);
            }
        }
    }

    private void setDistanceMap(){
        if(this.distanceMap == null){
            distanceMap = new ArrayList<>();
            movingGroupCountIndex = distanceMapSizeIndex + planetCount + 1;
            for(int i = distanceMapSizeIndex + 1; i < movingGroupCountIndex; i++){
                distanceMap.add(world.get(i));
            }
        }
    }

    private void setMovingGroups(){
        if(this.movingGroupCount == null){
            if(this.world.get(movingGroupCountIndex).size() == 1){
                movingGroupCount = world.get(movingGroupCountIndex).get(FIRST);
                if(movingGroupCount > 0){
                    movingGroups = new ArrayList<>();
                    MovingGroup movingGroup;
                    List<Integer> temp;
                    for(int i = movingGroupCountIndex + 1; i <= movingGroupCountIndex + movingGroupCount; i++){
                        temp = world.get(i);
                        movingGroup = new MovingGroup()
                                .setOwnerTeam(temp.get(TEAM_ID_INDEX))
                                .setFrom(temp.get(FROM_INDEX))
                                .setTo(temp.get(TO_INDEX))
                                .setCount(temp.get(COUNT_INDEX))
                                .setStepsLeft(temp.get(STEPS_LEFT_INDEX));
                        movingGroups.add(movingGroup);
                    }
                }
            } else {
                throw new IllegalStateException("Некорректное количество перемещающихся групп");
            }
        }
    }

    private void buildWorld(){
        setTeamId();
        setMaxSteps();
        setCurrentStep();
        setPlanetCount();
        setPlanets();
        setDistanceMap();
        setMovingGroups();
    }

    /**
     * Конструктор по списку строк.
     * @param input список строк
     */
    public Round(List<String> input) {
        this.world = parseInput(input);
        buildWorld();
    }

    /**
     * Получить идентификатор игрока.
     * @return
     */
    public Integer getTeamId(){
        return this.teamId;
    }

    /**
     * Получить максимальное количество ходов.
     * @return максимальное количество ходов
     */
    public Integer getMaxSteps() {
        return maxSteps;
    }

    /**
     * Получить текущий шаг.
     * @return текущий шаг
     */
    public Integer getCurrentStep() {
        return currentStep;
    }

    /**
     * Получить количество планет.
     * @return количество планет
     */
    public Integer getPlanetCount() {
        return planetCount;
    }

    /**
     * Получить список планет.
     * @return список планет
     */
    public List<Planet> getPlanets() {
        return planets;
    }

    /**
     * Получить таблицу расстояний (список списков).
     * @return таблица расстояний
     */
    public List<List<Integer>> getDistMap() {
        return distanceMap;
    }

    /**
     * Получить таблицу расстояний (матрица).
     * @return таблица расстояний
     */
    public int[][] getDistanceMap(){
        int[][] result = new int[planetCount][planetCount];
        if(this.distanceMap != null) {
            for (int i = 0; i < planetCount; i++) {
                for (int j = 0; j < planetCount; j++) {
                    result[i][j] = distanceMap.get(i).get(j);
                }
            }
        }
        return result;
    }

    /**
     * Получить перемещающиеся группы.
     */
    public List<MovingGroup> getMovingGroups(){
        return this.movingGroups;
    }

    /**
     * Получить список перемещающихся групп игрока.
     * @return список перемещающихся групп
     */
    public List<MovingGroup> getOwnMovingGroups(){
        return movingGroups.stream().filter(e -> e.getOwnerTeam() == teamId).collect(Collectors.toList());
    }

    /**
     * Получить список перемещающихся групп другого игрока.
     * @return список планет
     */
    public List<MovingGroup> getAdversarysMovingGroups(){
        int adversaryId = teamId == TEAM_0_ID ? TEAM_1_ID : TEAM_0_ID;
        return movingGroups.stream().filter(e -> e.getOwnerTeam() == adversaryId).collect(Collectors.toList());
    }

    /**
     * Получить список планет игрока.
     * @return список планет
     */
    public List<Planet> getOwnPlanets(){
        return planets.stream().filter(e -> e.getOwnerTeam() == teamId).collect(Collectors.toList());
    }

    /**
     * Получить список незанятых планет.
     * @return список планет
     */
    public List<Planet> getNoMansPlanets(){
        return planets.stream().filter(e -> e.getOwnerTeam() == NO_MANS_TEAM_ID).collect(Collectors.toList());
    }

    /**
     * Получить список планет другого игрока.
     * @return список планет
     */
    public List<Planet> getAdversarysPlanets(){
        int adversaryId = teamId == TEAM_0_ID ? TEAM_1_ID : TEAM_0_ID;
        return planets.stream().filter(e -> e.getOwnerTeam() == adversaryId).collect(Collectors.toList());
    }
}
