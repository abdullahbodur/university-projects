package cs.hacettepe.bbm102;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    public static HashMap<Integer, Person> readPersons(String text) {

        String[] lines = Helper.lineSplit(text);

        HashMap<Integer, Person> persons = new HashMap<>();

        for (String line : lines) {

            String[] arguments = line.split(":");

            arguments[1] = arguments[1].trim();

            Person newPerson = (Person) Controller.generateObject(arguments[0], arguments[1]);


            // caught personId
            int id = Integer.parseInt(arguments[1].split("\\s+")[0]);


            persons.put(id, newPerson);

        }

        return persons;
    }

    public static HashMap<Integer, Film> readFilms(String text) {

        String[] lines = Helper.lineSplit(text);

        HashMap<Integer, Film> films = new HashMap<>();

        for (String line : lines) {

            String[] arguments = line.split(":");

            arguments[1] = arguments[1].trim();

            Film newFilm = (Film) Controller.generateObject(arguments[0], arguments[1]);

            if (newFilm.getClass().getSimpleName().equals("ShortFilm") && newFilm.length <= 40)
                System.out.println("A short film cannot be longer than 40 minutes!");
            else {

                int id = Integer.parseInt(arguments[1].split("\\s+")[0]);

                films.put(id, newFilm);
            }


        }

        return films;
    }

    public static HashMap<Integer, Film> rateFilm(List<String> commandNodes, HashMap<Integer, Film> films, HashMap<Integer, Person> persons) {

        int userId = Integer.parseInt(commandNodes.get(1));
        int filmId = Integer.parseInt(commandNodes.get(2));
        int point = Integer.parseInt(commandNodes.get(3));

        Film film = films.get(filmId);
        Person person = persons.get(userId);

        String outputText = "";

        if (film == null || person == null) {

            outputText += "Command Failed\nUser ID: " + userId + "\nFilm ID: " + filmId + "\n\n";

        } else if (!person.getClass().getSimpleName().equals("User")) {

            outputText += "Command Failed\nUser ID: " + userId + "\nFilm ID: " + filmId + "\n\n";


        } else if (film.score.get(userId) != null) {
            outputText += "This film was earlier rated\n\n";

        } else {

            Score score = new Score(userId, filmId, point);

            film.score.put(userId, score);

            film = getAveragePoint(film);

            films.put(filmId, film);

            String FILM_TYPE = film.getClass().getSimpleName();

            outputText += "Film rated successfully\nFilm type: " + FILM_TYPE + "\nFilm title: " + film.title + "\n\n";
        }


        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);

        return films;


    }

    public static HashMap<Integer, Film> addFilm(List<String> commandNodes, HashMap<Integer, Film> films, HashMap<Integer, Person> persons) {
        String filmType = commandNodes.get(1);

        List<String> nodes = commandNodes.subList(2, commandNodes.size());

        String outputText = "";

        String propertiesNode = String.join("\t", nodes);

        Film newFilm = (Film) generateObject(filmType, propertiesNode);


        boolean writer_verified = true;

        switch (filmType) {

            case "TVSERIES":

                List<Integer> writers = ((TvSeries) newFilm).writers;
                writer_verified = controlUsers(writers, persons, "WRITER");

                break;
            case "FEATUREFILM":
                writers = ((FeatureFilm) newFilm).writers;
                writer_verified = controlUsers(writers, persons, "WRITER");

                break;
            case "SHORTFILM":
                writers = ((ShortFilm) newFilm).writers;
                writer_verified = controlUsers(writers, persons, "WRITER");
                break;

        }


        boolean director_verified = controlUsers(newFilm.directors, persons, "DIRECTOR");

        boolean stars_verified = controlUsers(newFilm.performers, persons, "STUNTPERFORMER", "CHILDACTOR", "ACTOR");

        if (films.get(newFilm.Id) != null || !director_verified || !writer_verified || !stars_verified) {

            outputText += "Command Failed\nFilm ID: " + newFilm.Id + "\nFilm title: " + newFilm.title + "\n\n";
        } else {

            films.put(newFilm.Id, newFilm);
            outputText += "FeatureFilm added successfully\nFilm ID: " + newFilm.Id + "\nFilm title: " + newFilm.title + "\n\n";

        }

        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);

        return films;
    }

    public static void viewFilm(List<String> commandNodes, HashMap<Integer, Film> films, HashMap<Integer, Person> persons) {

        int filmId = Integer.parseInt(commandNodes.get(1));

        String outputText = "";

        Film film = films.get(filmId);


        if (film == null) {

            outputText += "Command Failed\nFilm ID: " + filmId + "\n\n";
        } else {

            String FILM_TYPE = film.getClass().getSimpleName();


            int size = film.score.size();


            String votes = size >= 1 ? film.totalScore + "/10 from " + size + " users" : "Awaiting for votes";

            switch (FILM_TYPE) {

                case "Documentary":

                    Documentary dFilm = ((Documentary) film);

                    String directors = collectUsers(dFilm.directors, persons);

                    String stars = collectUsers(dFilm.performers, persons);

                    String releaseYear = dFilm.releaseDate.split("\\.")[2];

                    outputText += String.format("%1$s (%2$s)\nDirectors: %3$s\nStars: %4$s\n%5$s\n\n",
                            dFilm.title, releaseYear, directors, stars, votes);

                    break;

                case "TvSeries":


                    TvSeries tFilm = ((TvSeries) film);

                    String writers = collectUsers(tFilm.writers, persons);
                    directors = collectUsers(tFilm.directors, persons);
                    stars = collectUsers(tFilm.performers, persons);
                    String genres = String.join(" ", tFilm.genres);
                    String startYear = tFilm.startDate.split("\\.")[2];
                    String endYear = tFilm.endDate.split("\\.")[2];

                    outputText += String.format("%1$s (%2$s-%3$s)\n%4$d seasons, %5$d episodes\n%6$s\nWriters: %7$s\nDirectors: %8$s\nStars: %9$s\n%10$s\n\n",
                            tFilm.title, startYear, endYear, tFilm.numberOfSeason, tFilm.numberOfEpisode, genres, writers, directors, stars, votes);

                    break;


                case "FeatureFilm":
                    FeatureFilm fFilm = ((FeatureFilm) film);

                    writers = collectUsers(fFilm.writers, persons);

                    directors = collectUsers(fFilm.directors, persons);

                    stars = collectUsers(fFilm.performers, persons);

                    genres = String.join(" ", fFilm.genres);

                    releaseYear = fFilm.releaseDate.split("\\.")[2];

                    outputText += String.format("%1$s (%2$s)\n%3$s\nWriters: %4$s\nDirectors: %5$s\nStars: %6$s\n%7$s\n\n",
                            fFilm.title, releaseYear, genres, writers, directors, stars, votes);

                    break;

                case "ShortFilm":
                    ShortFilm sFilm = ((ShortFilm) film);

                    writers = collectUsers(sFilm.writers, persons);

                    directors = collectUsers(sFilm.directors, persons);

                    stars = collectUsers(sFilm.performers, persons);

                    genres = String.join(" ", sFilm.genres);

                    releaseYear = sFilm.releaseDate.split("\\.")[2];

                    outputText += String.format("%1$s (%2$s)\n%3$s\nWriters: %4$s\nDirectors: %5$s\nStars: %6$s\n%7$s\n\n",
                            sFilm.title, releaseYear, genres, writers, directors, stars, votes);
                default:

                    break;
            }

        }


        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);


    }

    public static void listUser(List<String> commandNodes, HashMap<Integer, Film> films, HashMap<Integer, Person> persons) {


        int userId = Integer.parseInt(commandNodes.get(2));

        StringBuilder outputText = new StringBuilder();

        if (persons.get(userId) == null) {
            outputText.append("Command Failed\n").append("User ID: +").append(userId).append("\n\n");
        } else {

            boolean noRate = true;

            for (Film film : films.values()) {

                List<Integer> ids = film.score.values().stream().map(Score::getUserId).collect(Collectors.toList());

                if (ids.contains(userId)) {

                    noRate = false;
                    String title = film.title;
                    int score = film.score.get(userId).getPoint();

                    outputText.append(title).append(": ").append(score).append("\n\n");

                }

            }

            if (noRate) {

                outputText.append("There is not any ratings so far\n\n");
            }

        }


        outputText.append("-----------------------------------------------------------------------------------------------------\n");


        Helper.writeFile("output2.txt", outputText.toString());
    }

    public static HashMap<Integer, Film> editRate(List<String> commandNodes, HashMap<Integer, Film> films, HashMap<Integer, Person> persons) {


        int userId = Integer.parseInt(commandNodes.get(2));
        int filmId = Integer.parseInt(commandNodes.get(3));
        int newPoint = Integer.parseInt(commandNodes.get(4));


        Film film = films.get(filmId);
        Person user = persons.get(userId);

        String outputText = "";

        if (user == null || film == null) {

            outputText += "Command Failed\nUser ID: " + userId + "\nFilm ID: " + filmId + "\n\n";

        } else if (!user.getClass().getSimpleName().equals("User") || film.score.get(userId) == null) {

            outputText += "Command Failed\nUser ID: " + userId + "\nFilm ID: " + filmId + "\n\n";

        } else {

            Score score = film.score.get(userId);

            score.setPoint(newPoint);

            film.score.put(userId, score);

            film = getAveragePoint(film);

            films.put(filmId, film);

            outputText += "New ratings done successfully\nFilm title: " + film.title + "\nYour rating: " + newPoint + "\n\n";

        }


        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);


        return films;


    }

    public static HashMap<Integer, Film> removeRate(List<String> commandNodes, HashMap<Integer, Film> films, HashMap<Integer, Person> persons) {

        int userId = Integer.parseInt(commandNodes.get(2));
        int filmId = Integer.parseInt(commandNodes.get(3));


        Person person = persons.get(userId);
        Film film = films.get(filmId);


        String outputText = "";

        if (person == null || film == null) {

            outputText += "Command Failed\nUser ID: " + userId + "\nFilm ID: " + filmId + "\n\n";

        } else if (film.score.get(userId) == null || !person.getClass().getSimpleName().equals("User")) {

            outputText += "Command Failed\nUser ID: " + userId + "\nFilm ID: " + filmId + "\n\n";

        } else {

            film.score.remove(userId);

            films.put(filmId, film);

            outputText += "Your film rating was removed successfully\nFilm title: " + film.title + "\n\n";

        }


        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);

        return films;
    }

    public static void listFilmSeries(HashMap<Integer, Film> films) {

        StringBuilder outputText = new StringBuilder();

        boolean notFound = true;

        for (Film film : films.values()) {

            if (film.getClass().getSimpleName().equals("TvSeries")) {

                notFound = false;

                TvSeries tv_s = ((TvSeries) film);

                String startYear = tv_s.startDate.split("\\.")[2];
                String endYear = tv_s.endDate.split("\\.")[2];

                outputText.append(String.format("%1$s (%2$s-%3$s)\n%4$d seasons and %5$d episodes\n\n",
                        tv_s.title, startYear, endYear, tv_s.numberOfSeason, tv_s.numberOfEpisode));

            }
        }

        if (notFound) {
            outputText.append("No result\n\n");
        }

        outputText.append("-----------------------------------------------------------------------------------------------------\n");


        Helper.writeFile("output2.txt", outputText.toString());
    }

    public static void listFilmsByCountry(List<String> commandNodes, HashMap<Integer, Film> films) {

        String country = commandNodes.get(4);


        String outputText = films.values().stream().filter(film -> film.country.equals(country)).
                map(f -> String.format("Film title: %1$s\n%2$d min\nLanguage: %3$s\n\n", f.title, f.length, f.language)).
                reduce("", String::concat);


        if (outputText.isEmpty()) {

            outputText += "No result\n\n";

        }


        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);


    }

    public static void listFeatureFilms(List<String> commandNodes, HashMap<Integer, Film> films) {

        int year = Integer.parseInt(commandNodes.get(3));


        List<FeatureFilm> featureFilms = films.values().stream().
                filter(film -> film.getClass().getSimpleName().equals("FeatureFilm")).
                map(film -> ((FeatureFilm) film)).
                collect(Collectors.toList());


        StringBuilder outputText = new StringBuilder();

        boolean notFound = true;

        for (FeatureFilm featureFilm : featureFilms) {

            int releaseYear = Integer.parseInt(featureFilm.releaseDate.split("\\.")[2]);


            switch (commandNodes.get(2)) {

                case "BEFORE":

                    if (releaseYear < year) {

                        notFound = false;

                        outputText.append(String.format("Film title: %1$s (%2$s)\n%3$d min\nLanguage: %4$s\n\n",
                                featureFilm.title, releaseYear, featureFilm.length, featureFilm.language));

                    }
                    break;

                case "AFTER":

                    if (releaseYear > year) {

                        notFound = false;

                        outputText.append(String.format("%1$s (%2$s)\n%3$d min\nLanguage: %4$s\n\n",
                                featureFilm.title, releaseYear, featureFilm.length, featureFilm.language));
                    }

                    break;

            }

        }

        if (notFound) {

            outputText.append("No result\n\n");
        }

        outputText.append("-----------------------------------------------------------------------------------------------------\n");

        Helper.writeFile("output2.txt", outputText.toString());


    }

    public static void listFilmByRate(HashMap<Integer, Film> films) {

        StringBuilder outputText = new StringBuilder();

        String featureFilms = films.values().stream().
                filter(film -> film.getClass().getSimpleName().equals("FeatureFilm")).
                map(film -> ((FeatureFilm) film)).
                sorted(Comparator.comparingDouble(o -> ((FeatureFilm) o).totalScore).reversed()).
                map(f -> String.format("%1$s (%2$s) Ratings: %3$s/10 from %4$d users\n",
                        f.title, getYear(f.releaseDate), Helper.pointToComma(f.totalScore), f.score.values().size())).
                reduce("", String::concat);


        String shortFilms = films.values().stream().
                filter(film -> film.getClass().getSimpleName().equals("ShortFilm")).
                map(film -> ((ShortFilm) film)).
                sorted(Comparator.comparingDouble(o -> ((ShortFilm) o).totalScore).reversed()).
                map(f -> String.format("%1$s (%2$s) Ratings: %3$s/10 from %4$d users\n",
                        f.title, f, f.totalScore, f.score.values().size())).
                reduce("", String::concat);


        String documentaries = films.values().stream().
                filter(film -> film.getClass().getSimpleName().equals("Documentary")).
                map(film -> ((Documentary) film)).
                sorted(Comparator.comparingDouble(o -> ((Documentary) o).totalScore).reversed()).
                map(f -> String.format("%1$s (%2$s) Ratings: %3$s/10 from %4$d users\n",
                        f.title, getYear(f.releaseDate), f.totalScore, f.score.values().size())).
                reduce("", String::concat);


        String tvSeries = films.values().stream().
                filter(film -> film.getClass().getSimpleName().equals("TvSeries")).
                map(film -> ((TvSeries) film)).
                sorted(Comparator.comparingDouble(o -> o.totalScore)).
                map(f -> String.format("%1$s (%2$s-%3$s) Ratings: %4$s/10 from %5$d users\n",
                        f.title, getYear(f.startDate), getYear(f.endDate), f.totalScore, f.score.values().size())).
                reduce("", String::concat);


        outputText.append("FeatureFilm:\n");

        if (featureFilms.isEmpty()) {

            outputText.append("No result\n");

        } else {

            outputText.append(featureFilms);
        }


        outputText.append("\nShortFilm:\n");


        if (shortFilms.isEmpty()) {

            outputText.append("No result\n");

        } else {

            outputText.append(shortFilms);
        }


        outputText.append("\nDocumentary:\n");


        if (documentaries.isEmpty()) {

            outputText.append("No result\n");

        } else {

            outputText.append(documentaries);
        }


        outputText.append("\nTVSeries:\n");

        if (tvSeries.isEmpty()) {

            outputText.append("No result\n");

        } else {

            outputText.append(tvSeries);
        }


        outputText.append("\n-----------------------------------------------------------------------------------------------------\n");

        Helper.writeFile("output2.txt", outputText.toString());


    }

    public static void listArtistFromCountry(List<String> commandNodes, HashMap<Integer, Person> persons) {
        String country = commandNodes.get(3);

        String outputText = "";

        String directorsText = persons.values().stream().
                filter(p -> p.country.equals(country) && p.getClass().getSimpleName().equals("Director")).
                map(p -> (Director) p).
                map(p -> String.format("%1$s %2$s %3$s\n", p.name, p.surname, p.agent)).
                reduce("", String::concat);


        String writersText = persons.values().stream().
                filter(p -> p.country.equals(country) && p.getClass().getSimpleName().equals("Writer")).
                map(p -> (Writer) p).
                map(p -> String.format("%1$s %2$s %3$s\n", p.name, p.surname, p.writingType)).
                reduce("", String::concat);

        String actorsText = persons.values().stream().
                filter(p -> p.country.equals(country) && p.getClass().getSimpleName().equals("Actor")).
                map(p -> (Actor) p).
                map(p -> String.format("%1$s %2$s %3$s cm\n", p.name, p.surname, p.getHeight())).
                reduce("", String::concat);

        String childActorsText = persons.values().stream().
                filter(p -> p.country.equals(country) && p.getClass().getSimpleName().equals("ChildActor")).
                map(p -> (ChildActor) p).
                map(p -> String.format("%1$s %2$s %3$d\n", p.name, p.surname, p.getAge())).
                reduce("", String::concat);

        String stuntPerformersText = persons.values().stream().
                filter(p -> p.country.equals(country) && p.getClass().getSimpleName().equals("StuntPerformer")).
                map(p -> (StuntPerformer) p).
                map(p -> String.format("%1$s %2$s %3$s cm\n", p.name, p.surname, p.getHeight())).
                reduce("", String::concat);



        outputText += "Directors:\n" + (directorsText.isEmpty() ? "No result\n" : directorsText);

        outputText += "\nWriters:\n" + (writersText.isEmpty() ? "No result\n" : writersText);

        outputText += "\nActors:\n" + (actorsText.isEmpty() ? "No result\n" : actorsText);

        outputText += "\nChildActors:\n" + (childActorsText.isEmpty() ? "No result\n" : childActorsText);

        outputText += "\nStuntPerformers:\n" + (stuntPerformersText.isEmpty() ? "No result\n" : stuntPerformersText);


        outputText += "-----------------------------------------------------------------------------------------------------\n";

        Helper.writeFile("output2.txt", outputText);


    }

    private static Film getAveragePoint(Film film) {

        film.totalScore = film.score.values().stream().map(Score::getPoint).mapToInt(Integer::intValue).average().orElse(-1);

        return film;

    }

    private static Object generateObject(String objectType, String propertiesNode) {

        Object generatedObject = null;
        String[] properties = propertiesNode.split("\\s+");

        switch (objectType.toUpperCase()) {

            case "USER":
                generatedObject = new User(properties);
                break;
            case "WRITER":
                generatedObject = new Writer(properties);
                break;
            case "DIRECTOR":
                generatedObject = new Director(properties);
                break;
            case "ACTOR":
                generatedObject = new Actor(properties);
                break;
            case "CHILDACTOR":
                generatedObject = new ChildActor(properties);
                break;
            case "STUNTPERFORMER":
                generatedObject = new StuntPerformer(properties);
                break;

            // films
            case "DOCUMENTARY":
                generatedObject = new Documentary(properties);
                break;
            case "TVSERIES":
                generatedObject = new TvSeries(properties);
                break;
            case "FEATUREFILM":
                generatedObject = new FeatureFilm(properties);
                break;
            case "SHORTFILM":
                generatedObject = new ShortFilm(properties);
                break;
            default:
                break;
        }


        return generatedObject;

    }

    private static String collectUsers(List<Integer> ids, HashMap<Integer, Person> persons) {

        List<String> users = new ArrayList<>();
        for (Integer id : ids) {
            Person p = persons.get(id);
            users.add(p.name + " " + p.surname);
        }

        return String.join(", ", users);
    }

    private static boolean controlUsers(List<Integer> ids, HashMap<Integer, Person> persons, String... userType) {

        List<String> userT = Arrays.asList(userType);

        for (int id : ids) {

            Person p = persons.get(id);

            if (p == null) return false;


            String CLASS_NAME = p.getClass().getSimpleName().toUpperCase();

            if (!userT.contains(CLASS_NAME)) return false;
        }

        return true;
    }


    private static String getYear(String date) {
        return date.split("\\.")[2];
    }

}
