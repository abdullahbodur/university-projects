import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // write your code here


        String fixturesPath = args[0];

        String fixturesText = Helper.readFile(fixturesPath);


        String[] fixturesLines = fixturesText.split("\n");

        HashMap<String, Sports> handballClubs = new HashMap<>();
        HashMap<String, Sports> IceHockeyClubs = new HashMap<>();

        for (String line : fixturesLines) {

            String[] properties = line.split("\t");

            String team1 = properties[1];
            String team2 = properties[2];

            List<Integer> scores = Arrays.stream(properties[3].split(":")).map(Integer::parseInt).collect(Collectors.toList());

            int p1 = scores.get(0);
            int p2 = scores.get(1);

            if (properties[0].equals("I")) {

                Sports iceHockey1, iceHockey2;

                if (IceHockeyClubs.get(team1) == null) {

                    iceHockey1 = new IceHockey(team1);

                } else {
                    iceHockey1 = IceHockeyClubs.get(team1);
                }

                if (IceHockeyClubs.get(team2) == null) {

                    iceHockey2 = new IceHockey(team2);

                } else {
                    iceHockey2 = IceHockeyClubs.get(team2);
                }


                iceHockey1.playedMatches += 1;
                iceHockey2.playedMatches += 1;

                iceHockey1.totalScores += p1;
                iceHockey1.totalAgainstScores += p2;
                iceHockey2.totalScores += p2;
                iceHockey2.totalAgainstScores += p1;


                if (p1 > p2) {

                    iceHockey1.wonMatches += 1;

                    iceHockey2.lossMatches += 1;

                    iceHockey1.totalPoints += 3;

                } else if (p2 > p1) {
                    iceHockey2.wonMatches += 1;

                    iceHockey1.lossMatches += 1;

                    iceHockey2.totalPoints += 3;


                } else {

                    iceHockey1.evenScoreOrTie += 1;

                    iceHockey2.evenScoreOrTie += 1;

                    iceHockey1.totalPoints += 1;
                    iceHockey2.totalPoints += 1;


                }

                IceHockeyClubs.put(iceHockey1.clubName, iceHockey1);
                IceHockeyClubs.put(iceHockey2.clubName, iceHockey2);


            } else {


                Sports handBall1, handBall2;

                if (handballClubs.get(team1) == null) {

                    handBall1 = new HandBall(team1);

                } else {
                    handBall1 = handballClubs.get(team1);
                }

                if (handballClubs.get(team2) == null) {

                    handBall2 = new HandBall(team2);

                } else {
                    handBall2 = handballClubs.get(team2);
                }


                handBall1.playedMatches += 1;
                handBall2.playedMatches += 1;
                handBall1.totalScores += p1;
                handBall1.totalAgainstScores += p2;
                handBall2.totalScores += p2;
                handBall2.totalAgainstScores += p1;

                if (p1 > p2) {

                    handBall1.wonMatches += 1;
                    handBall2.lossMatches += 1;

                    handBall1.totalPoints += 2;

                } else if (p2 > p1) {
                    handBall2.wonMatches += 1;
                    handBall1.lossMatches += 1;

                    handBall2.totalPoints += 2;


                } else {

                    handBall1.evenScoreOrTie += 1;
                    handBall2.evenScoreOrTie += 1;

                    handBall1.totalPoints += 1;
                    handBall2.totalPoints += 1;

                }

                handballClubs.put(handBall1.clubName, handBall1);
                handballClubs.put(handBall2.clubName, handBall2);


            }


        }


        List<Sports> handBalls = new ArrayList<>(handballClubs.values());
        List<Sports> iceHockeys = new ArrayList<>(IceHockeyClubs.values());

        handBalls = Helper.putProperOrder(handBalls);
        iceHockeys = Helper.putProperOrder(iceHockeys);


        StringBuilder handballTxt = new StringBuilder();

        for (int i = 0; i < handBalls.size(); i++) {


            HandBall handBall = (HandBall) handBalls.get(i);

            String scoreTable = handBall.totalScores + ":" + handBall.totalAgainstScores;
            int totalPoints = 2 * handBall.wonMatches + handBall.evenScoreOrTie;
            int playedMatches = handBall.wonMatches + handBall.lossMatches + handBall.evenScoreOrTie;

            handballTxt.append(String.format("%1$d.\t%2$s\t%3$d\t%4$d\t%5$d\t%6$d\t%7$s\t%8$d",
                    (i + 1), handBall.clubName,playedMatches, handBall.wonMatches, handBall.evenScoreOrTie, handBall.lossMatches, scoreTable, totalPoints));


            if (i != handballClubs.size() - 1 ) handballTxt.append("\n");


        }

        StringBuilder iceHockeyTxt = new StringBuilder();

        for (int i = 0; i < iceHockeys.size(); i++) {


            IceHockey iceHockey = (IceHockey) iceHockeys.get(i);

            String scoreTable = iceHockey.totalScores + ":" + iceHockey.totalAgainstScores;
            int totalPoints = 3 * iceHockey.wonMatches + iceHockey.evenScoreOrTie;
            int playedMatches = iceHockey.wonMatches + iceHockey.lossMatches + iceHockey.evenScoreOrTie;

            iceHockeyTxt.append(String.format("%1$d.\t%2$s\t%3$d\t%4$d\t%5$d\t%6$d\t%7$s\t%8$d",
                    (i + 1), iceHockey.clubName, playedMatches,iceHockey.wonMatches, iceHockey.evenScoreOrTie, iceHockey.lossMatches, scoreTable, totalPoints));



            if (i != iceHockeys.size() - 1 ) iceHockeyTxt.append("\n");

        }


        Helper.writeFile("icehockey.txt", iceHockeyTxt.toString());
        Helper.writeFile("handball.txt", handballTxt.toString());


    }


}
