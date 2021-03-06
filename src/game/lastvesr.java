import game.MovingGroup;
import game.Planet;
import game.Round;

import java.util.ArrayList;
import java.util.List;

import static game.InOutUtils.readStringsFromInputStream;
import static game.ProcessUtils.UTF_8;

public class Main {

    public static void main(String[] args) {

        List< String > input = readStringsFromInputStream(System.in, UTF_8);

        if (!input.isEmpty()) {

            Round round = new Round(input);

            printMovingGroups(makeMove(round));

        }

        System.exit(0);

    }

    private static List <MovingGroup> makeMove(Round round) {

        List < MovingGroup > movingGroups = new ArrayList< >();

        // Место для Вашего кода.

        try {

            List <Planet> myPlanets = round.getOwnPlanets();

            List < Planet > myPlanetstoDefense = new ArrayList();

            boolean flag;

            int ii = 0;

            Planet nearestPlanet = null;

            if (round.getCurrentStep() < 4) {

                if (!round.getNoMansPlanets().isEmpty()) {

                    Planet home_first = getHomePlanet(round);

                    List < Planet > noMNGP = getNoMnPlanets(round); //Возвращает планеты, которые ещё не заняты, и на них не летят наши корабли

                    if (!noMNGP.isEmpty()) {

                        noMNGP.sort((o1, o2) - > {

                        return round.getDistanceMap()[home_first.getId()][o1.getId()] - round.getDistanceMap()[home_first.getId()][o2.getId()];

                        });

                        for (int i = 0; i < noMNGP.size(); i++) {

                            if ( /*myPlanets.get(0).getPopulation()*/ home_first.getPopulation() > noMNGP.get(i).getPopulation() + 1) {

                                MovingGroup group = new MovingGroup();

                                group.setFrom(home_first.getId());

                                group.setTo(noMNGP.get(i).getId());

                                group.setCount(noMNGP.get(i).getPopulation() + 1);

                                movingGroups.add(group);

                            }

                        }

                    }

                }

            } else {

                //Оборона

                try {

                    List < MovingGroup > enemiesAttack = round.getAdversarysMovingGroups();

                    Planet planetISAttacked;

                    List < Planet > myPlanet2 = round.getOwnPlanets();

                    List < MovingGroup > myGroupstoDefense = round.getOwnMovingGroups();

                    boolean flag1 = true;

                    for (int j = 0; j < enemiesAttack.size(); j++) {

                        planetISAttacked = round.getPlanets().get(enemiesAttack.get(j).getTo());

                        if ((planetISAttacked.getOwnerTeam() == round.getTeamId()) &&

                                (planetISAttacked.getPopulation() + enemiesAttack.get(j).getStepsLeft() * planetISAttacked.getReproduction() <= enemiesAttack.get(j).getCount())) { //Нас атакуют!!!

                            /*flag = true;

                            ii = 0;*/

                            Planet planetToAttack1 = planetISAttacked;

                            for (MovingGroup element: myGroupstoDefense)

                                if (element.getTo() == planetISAttacked.getId())

                                    flag1 = false; //Мы уже отправили помощь

                            if (flag1) {

                                boolean flag2 = true;

                                myPlanet2.sort((o1, o2) - > {

                                return round.getDistanceMap()[o1.getId()][planetToAttack1.getId()] - round.getDistanceMap()[o2.getId()][planetToAttack1.getId()];

                                });

                                //Здесь мы отсортировали список, чтобы отправлять помощь с ближайшей планеты

                                for (Planet element: myPlanet2) {

                                    if (flag2) {

                                        if ((element.getPopulation() > enemiesAttack.get(j).getCount() /*element.getPopulation() > enemiesAttack.get(j).getCount() - planetToAttack.getPopulation() - enemiesAttack.get(j).getStepsLeft() * planetToAttack.getReproduction() + 1*/ )) {

                                            //if (home_first.getPopulation() > enemiesAttack.get(j).getCount() - planetToAttack.getPopulation() - en

                                            MovingGroup group = new MovingGroup();

                                            group.setFrom(element.getId());

                                            group.setTo(planetISAttacked.getId());

                                            group.setCount(enemiesAttack.get(j).getCount() /*enemiesAttack.get(j).getCount() - planetToAttack.getPopulation() - enemiesAttack.get(j).getStepsLeft() * planetToAttack.getReproduction() + 1*/ );

                                            movingGroups.add(group);

                                            flag2 = false;

                                            round.getPlanets().get(element.getId()).setPopulation(round.getPlanets().get(element.getId()).getPopulation() - enemiesAttack.get(j).getCount());

                                            myPlanetstoDefense.add(planetISAttacked);

                                        }

                                    }

                                }

                            }

                        }

                    }

                } catch (NullPointerException e) {

                }

                //Оборона

                //Атака

                List < Planet > otherPlanets = new ArrayList();

                List < Planet > sentToNoMan = new ArrayList < > ();

                boolean f = true;

                for (int i = 0; i < round.getPlanets().size(); i++) {

                    if (round.getPlanets().get(i).getOwnerTeam() != round.getTeamId()) { //Планета не наша

                        try {

                            List < MovingGroup > myGroups = round.getOwnMovingGroups();

                            f = true;

                            int k = 0;

                            Planet planetToAttack = round.getPlanets().get(i); //Эту планету мы собираемся атаковать

                            // ход 3, если планета не занята и мы ее не обороняем то высылаем помощь по ее захвату.
                            //
                            int j = 0, countSent = 1;

                            while ((j < myPlanets.size())) {

                                if (!myPlanetstoDefense.contains(myPlanets.get(j))) { //Если мы её не обороняем

                                    if ((round.getPlanets().get(i).getOwnerTeam() == -1) && (myPlanets.get(j).getPopulation() > round.getPlanets().get(i).getPopulation() + 1) && (!sentToNoMan.contains(round.getPlanets().get(i)))) { //если планета не занята

                                        MovingGroup group = new MovingGroup();

                                        group.setFrom(myPlanets.get(j).getId());

                                        group.setTo(round.getPlanets().get(i).getId());

                                        group.setCount(round.getPlanets().get(i).getPopulation() + 1);

                                        myPlanets.get(j).setPopulation(myPlanets.get(j).getPopulation() - round.getPlanets().get(i).getPopulation() - 1);

                                        sentToNoMan.add(round.getPlanets().get(i));

                                        movingGroups.add(group);

                                    } else if ((myPlanets.get(j).getPopulation() >

                                            (round.getPlanets().get(i).getPopulation() / countSent + round.getDistanceMap()[round.getPlanets().get(i).getId()][myPlanets.get(j).getId() / countSent]) + countSent)) {

                                        MovingGroup group = new MovingGroup();

                                        group.setFrom(myPlanets.get(j).getId());

                                        group.setTo(round.getPlanets().get(i).getId());

                                        group.setCount(round.getPlanets().get(i).getPopulation() / countSent + round.getDistanceMap()[round.getPlanets().get(i).getId()][myPlanets.get(j).getId() / countSent] + countSent);

                                        movingGroups.add(group);

                                        myPlanets.get(j).setPopulation(myPlanets.get(j).getPopulation() - (round.getPlanets().get(i).getPopulation() / countSent + /*round.getPlanets().get(i).getReproduction() **/ round.getDistanceMap()[round.getPlanets().get(i).getId()][myPlanets.get(j).getId() / countSent] + countSent));

                                        countSent++;

                                    }

                                }

                                j++;

                            }

                            // }

                        } catch (NullPointerException e) {

                            int j = 0, countSent = 1;

                            while ((f) && (j < myPlanets.size())) {

                                if (!myPlanetstoDefense.contains(myPlanets.get(j))) { //Если мы её не обороняем

                                    if ((round.getPlanets().get(i).getOwnerTeam() == -1) && (myPlanets.get(j).getPopulation() > round.getPlanets().get(i).getPopulation() +

                                            1) && (!sentToNoMan.contains(round.getPlanets().get(i)))) { //если планета не занята

                                        MovingGroup group = new MovingGroup();

                                        group.setFrom(myPlanets.get(j).getId());

                                        group.setTo(round.getPlanets().get(i).getId());

                                        group.setCount(round.getPlanets().get(i).getPopulation() + 1);

                                        sentToNoMan.add(round.getPlanets().get(i));

                                        movingGroups.add(group);

                                        myPlanets.get(j).setPopulation(myPlanets.get(j).getPopulation() - round.getPlanets().get(i).getPopulation() - 1);

                                    } else if ((myPlanets.get(j).getPopulation() >

                                            (round.getPlanets().get(i).getPopulation() / countSent + round.getDistanceMap()[round.getPlanets().get(i).getId()][myPlanets.get(j).getId() / countSent]) + countSent)) {

                                        MovingGroup group = new MovingGroup();

                                        group.setFrom(myPlanets.get(j).getId());

                                        group.setTo(round.getPlanets().get(i).getId());

                                        group.setCount(round.getPlanets().get(i).getPopulation() / countSent + round.getDistanceMap()[round.getPlanets().get(i).getId()][myPlanets.get(j).getId() / countSent] + countSent);

                                        movingGroups.add(group);

                                        myPlanets.get(j).setPopulation(myPlanets.get(j).getPopulation() - (round.getPlanets().get(i).getPopulation() / countSent + /*round.getPlanets().get(i).getReproduction() **/

                                                round.getDistanceMap()[round.getPlanets().get(i).getId()][myPlanets.get(j).getId() / countSent] + countSent));

                                        countSent++;

                                    }

                                }

                                j++;

                            }

                        }

                    }

                }

                //Атака

            }

        } catch (NullPointerException e) {}

        return movingGroups;

    }

    private static Planet getHomePlanet(Round round) {

        Planet home_first;

        if (round.getTeamId() == 0)

            home_first = round.getPlanets().get(0);

        else

            home_first = round.getPlanets().get(round.getPlanetCount() - 1);

        return home_first; //round.getOwnPlanets().get(0);

    }

    private static List < Planet > getNoMnPlanets(Round round) {

        List < Planet > noMans = round.getNoMansPlanets();

        List < Planet > res_planet = new ArrayList < > ();

        boolean f;

        try {

            List < MovingGroup > myGroups = round.getOwnMovingGroups();

            List < MovingGroup > enemyGroups = round.getAdversarysMovingGroups();

            for (int j = 0; j < noMans.size(); j++) {

                f = true;

                for (int i = 0; i < myGroups.size(); i++) {

                    if (myGroups.get(i).getTo() == noMans.get(j).getId())

                        f = false;

                }

                if (f)

                    res_planet.add(noMans.get(j));

            }

            return res_planet;

        } catch (NullPointerException e) {

            return noMans;

        }

    }

    private static void printMovingGroups(List < MovingGroup > moves) {

        System.out.println(moves.size());

        moves.forEach(move - > System.out.println(move.getFrom() + " " + move.getTo() + " " + move.getCount()));

    }

}