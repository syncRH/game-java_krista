package game;

import com.sun.media.jfxmedia.events.PlayerEvent;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.omg.CORBA.FREE_MEM;

import java.util.*;
import static game.InOutUtils.readStringsFromInputStream;
import static game.ProcessUtils.UTF_8;

/**
 * Main x class.
 */
public class Main {
    gh repo clone syncRH/game-java_krista

    public static void main(String[] args) {
        List<String> input = readStringsFromInputStream(System.in, UTF_8);
        if(!input.isEmpty()){
            Round round = new Round(input);
            printMovingGroups(makeMove(round));
        }
        System.exit(0);
    }
/*
                Первые шаги.
                Изначально, мы, захватываем все ближайшие планеты, расстояние до которых не более 12 dist. и население планеты с которой
                мы хотим отправить корабль не менее населения той, которую мы хотим захватить, так же, планета не должна быть нашей.
                Оборона.
                После того как мы сделали первые шаги, а именно, попытались захватить все свободные планеты, пытаемся защитить те планеты, которые имеем.
                Мы отправляем корабли от тех планет, которые ближе по расстоянию, а если на эту планету уже летит помощь, то высылаем ее на другую.
                Атака.
                Если планета не наша и мы ее не защищаем, то высылаем ракеты захвата на эту планету, с свободный планеты(которая не защищает другие планеты),
                так же, идет поиск планеты которая имеет может захватить разом вражескую планету..

 */

    private static List<MovingGroup> makeMove(Round round) {
        List<MovingGroup> movingGroups = new ArrayList<>();
        List<Planet> planets = round.getPlanets();
        List<Planet> myPlanets = round.getOwnPlanets();
        ArrayList<Integer> freeplanet = new ArrayList<Integer>();
        boolean alreadyHelp, findingHelpPlanet;
        int myplanet = round.getTeamId();
        try {
            if (round.getCurrentStep() < 10) {
                for (int i = 0; i < round.getPlanetCount(); i++) {
                    Planet one = planets.get(i);
                    for (int j = 0; j < round.getPlanetCount(); j++) {
                        Planet two = planets.get(j);
                        if (one.getOwnerTeam() == myplanet && two.getOwnerTeam() != myplanet && round.getDistanceMap()[i][j] < 12 && one.getPopulation() - 1 > two.getPopulation() + 1)
                            freeplanet.add(j);
                    }
                    for (int jk = 0; jk < freeplanet.size(); jk++)
                        movingGroups.add(new MovingGroup(i, freeplanet.get(jk), (one.getPopulation() - 1) / freeplanet.size()));
                    freeplanet.clear();
                }
            } else {
                List<MovingGroup> warningplanet = round.getAdversarysMovingGroups();
                List<MovingGroup> groupHelp = round.getOwnMovingGroups();
                alreadyHelp = false;
                int tempcount = 9999, tempplanet = 0;
                for (int ik = 0; ik < warningplanet.size(); ik++) {
                    Planet damagePlanet = planets.get(warningplanet.get(ik).getTo());
                    if ((warningplanet.get(ik).getStepsLeft() * damagePlanet.getReproduction() + damagePlanet.getPopulation() <= warningplanet.get(ik).getCount()) && damagePlanet.getOwnerTeam() == myplanet) {
                        for (int ikj = 0; ikj < groupHelp.size(); ikj++)
                            if (groupHelp.get(ikj).getTo() == damagePlanet.getId()) alreadyHelp = true;
                        if (!alreadyHelp) {
                            findingHelpPlanet = false;
                            for (int jk = 0; jk < myPlanets.size(); jk++) {
                                if (((round.getDistanceMap()[ik][jk]) < tempcount) && (myPlanets.get(jk).getPopulation() > warningplanet.get(ik).getCount())) {
                                    tempcount = round.getDistanceMap()[ik][jk];
                                    tempplanet = myPlanets.get(jk).getId();
                                    findingHelpPlanet = true;
                                }
                            }
                            if (findingHelpPlanet) {
                                movingGroups.add(new MovingGroup(tempplanet, damagePlanet.getId(), warningplanet.get(ik).getCount()));
                                groupHelp.add(new MovingGroup(0, damagePlanet.getId(), 0));
                            }

                        }
                    } else {
                        for (int i = 0; i < round.getPlanetCount(); i++) {
                            Planet one = planets.get(i);
                            for (int j = 0; j < round.getPlanetCount(); j++) {
                                Planet two = planets.get(j);
                                if (one.getOwnerTeam() == myplanet && two.getOwnerTeam() != myplanet && round.getDistanceMap()[i][j] < 9 && one.getPopulation() - 50 > two.getPopulation() + 1)
                                    movingGroups.add(new MovingGroup(i, j, (one.getPopulation() - 50)));
                            }
                        }
                    }
                    tempcount = 9999;
                    tempplanet = 0;
                }
            }
        }
        catch (NullPointerException ex) {
           for (int i = 0; i < round.getPlanetCount(); i++) {
                Planet one = planets.get(i);
                for (int j = 0; j < round.getPlanetCount(); j++) {
                    Planet two = planets.get(j);
                    if (one.getOwnerTeam() == myplanet && two.getOwnerTeam() != myplanet && round.getDistanceMap()[i][j] < 12 && one.getPopulation() - 1 > two.getPopulation() + 1)
                        freeplanet.add(j);
                }
                for (int jk = 0; jk < freeplanet.size(); jk++)
                    movingGroups.add(new MovingGroup(i, freeplanet.get(jk), (one.getPopulation() - 1) / freeplanet.size()));
                freeplanet.clear();
            }
        }
        return movingGroups;
    }
    private static void printMovingGroups(List<MovingGroup> moves) {
        System.out.println(moves.size());
        moves.forEach(move -> System.out.println(move.getFrom() + " " + move.getTo() + " " + move.getCount()));
    }

}
