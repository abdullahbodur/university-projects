package cs.hacettepe.bbm102;

import java.util.HashMap;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        String filmText = Helper.readFile("films.txt");

        String peopleText = Helper.readFile("people.txt");

        String commandText = Helper.readFile("commands.txt");

        String[] commands = Helper.lineSplit(commandText);

        HashMap<Integer,Film> films = Controller.readFilms(filmText);

        HashMap<Integer,Person> persons = Controller.readPersons(peopleText);

        for (String command:commands) {
            Helper.writeFile("output2.txt",command+"\n\n");

            List<String> commandNodes = Helper.splitFromWhiteSpace(command);

            switch (commandNodes.get(0)){
                case "RATE":
                    films = Controller.rateFilm(commandNodes,films,persons);
                    break;

                case "ADD":
                    films = Controller.addFilm(commandNodes,films,persons);
                    break;
                case "VIEWFILM":
                    Controller.viewFilm(commandNodes,films,persons);
                    break;

                case "LIST":

                    switch (commandNodes.get(1)){

                        case "USER":
                            Controller.listUser(commandNodes,films,persons);
                            break;
                        case "FILM":
                            Controller.listFilmSeries(films);
                            break;
                        case "FILMS":
                            switch (commandNodes.get(3)){
                                case "COUNTRY":
                                    Controller.listFilmsByCountry(commandNodes,films);
                                    break;
                                case "RATE":
                                    Controller.listFilmByRate(films);
                                    break;
                            }
                            break;

                        case "FEATUREFILMS":
                            Controller.listFeatureFilms(commandNodes,films);
                            break;

                        case "ARTISTS":
                            Controller.listArtistFromCountry(commandNodes,persons);
                            break;

                    }

                    break;

                case "EDIT":
                    films = Controller.editRate(commandNodes,films,persons);
                    break;

                case "REMOVE":
                    films = Controller.removeRate(commandNodes,films,persons);
                default:
                    break;
            }

        }

    }
}
