package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static class Movie {
        int id, year, duration, dirID;
        String title, genre;
        float rating;
        List<Integer> actorIDs;

        Movie(int id, String title, int year, String genre, float rating, int duration, int dirID, List<Integer> actorIDs) {
            this.id = id;
            this.title = title;
            this.year = year;
            this.genre = genre;
            this.rating = rating;
            this.duration = duration;
            this.dirID = dirID;
            this.actorIDs = actorIDs;
        }

        @Override
        public String toString() {
            return "Movie{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", year=" + year +
                    ", genre='" + genre + '\'' +
                    ", rating=" + rating +
                    ", duration=" + duration +
                    ", dirID=" + dirID +
                    ", actorIDs=" + actorIDs +
                    '}';
        }
    }

    static class Actor {
        int id;
        String name;
        String birthYear; // Changed from int to String

        Actor(int id, String name, String birthYear) {
            this.id = id;
            this.name = name;
            this.birthYear = birthYear;
        }
    }

    static class Director {
        int id;
        String name;

        Director(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static Map<Integer, Movie> movies = new HashMap<>();
    static Map<Integer, Actor> actors = new HashMap<>();
    static Map<Integer, Director> directors = new HashMap<>();

    public static void main(String[] args) {
        loadMovies("C:\\Users\\dell\\IdeaProjects\\myMoviesProject\\src\\main\\java\\org\\example\\data\\movies_large.csv");
        loadActors("C:\\Users\\dell\\IdeaProjects\\myMoviesProject\\src\\main\\java\\org\\example\\data\\actors_large.csv");
        loadDirectors("C:\\Users\\dell\\IdeaProjects\\myMoviesProject\\src\\main\\java\\org\\example\\data\\directors_large.csv");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMovie Data Management System");
            System.out.println("1. Get Movie Information");
            System.out.println("2. Get Top 10 Rated Movies");
            System.out.println("3. Get Movies by Genre");
            System.out.println("4. Get Movies by Director");
            System.out.println("5. Get Movies by Release Year");
            System.out.println("6. Get Movies by Year Range");
            System.out.println("7. Add a New Movie");
            System.out.println("8. Update Movie Rating");
            System.out.println("9. Delete a Movie");
            System.out.println("10. Get Top 15 Movies Sorted by Release Year");
            System.out.println("11. Get Directors with Most Movies");
            System.out.println("12. Get Actors Who Worked in Most Movies");
            System.out.println("13. Get Movies of Youngest Actor (as of 10-02-2025)");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    getMovieInfo(scanner);
                    break;
                case 2:
                    getTopRatedMovies();
                    break;
                case 3:
                    getMoviesByGenre(scanner);
                    break;
                case 4:
                    getMoviesByDirector(scanner);
                    break;
                case 5:
                    getMoviesByReleaseYear(scanner);
                    break;
                case 6:
                    getMoviesByYearRange(scanner);
                    break;
                case 7:
                    addMovie(scanner);
                    break;
                case 8:
                    updateMovieRating(scanner);
                    break;
                case 9:
                    deleteMovie(scanner);
                    break;
                case 10:
                    getTopMoviesByYear();
                    break;
                case 11:
                    getTopDirectors();
                    break;
                case 12:
                    getActorsWithMostMovies();
                    break;
                case 13:
                    getYoungestActorMovies();
                    break;
                case 14:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static void loadMovies(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 8) continue;

                int id = Integer.parseInt(data[0]);
                String title = data[1];
                int year = Integer.parseInt(data[2]);
                String genre = data[3];
                float rating = Float.parseFloat(data[4]);
                int duration = Integer.parseInt(data[5]);
                int dirID = Integer.parseInt(data[6]);

                List<Integer> actorIDs = new ArrayList<>();
                if (!data[7].isEmpty()) {
                    for (String actorId : data[7].replaceAll("[\"\\[\\]]", "").split(",")) {
                        actorIDs.add(Integer.parseInt(actorId.trim()));
                    }
                }

                movies.put(id, new Movie(id, title, year, genre, rating, duration, dirID, actorIDs));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void loadActors(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 3) continue;

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String birthYear = data[2]; // Treat birthYear as a String

                actors.put(id, new Actor(id, name, birthYear));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void loadDirectors(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length < 2) continue;

                int id = Integer.parseInt(data[0]);
                String name = data[1];

                directors.put(id, new Director(id, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void getMovieInfo(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Movie movie = movies.get(id);
        if (movie != null) {
            System.out.println(movie);
        } else {
            System.out.println("Movie not found!");
        }
    }

    static void getTopRatedMovies() {
        movies.values().stream()
                .sorted((m1, m2) -> Float.compare(m2.rating, m1.rating))
                .limit(10)
                .forEach(System.out::println);
    }

    static void getMoviesByGenre(Scanner scanner) {
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.genre.equalsIgnoreCase(genre))
                .forEach(System.out::println);
    }

    static void getMoviesByDirector(Scanner scanner) {
        System.out.print("Enter director ID: ");
        int dirID = scanner.nextInt();
        scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.dirID == dirID)
                .forEach(System.out::println);
    }

    static void getMoviesByReleaseYear(Scanner scanner) {
        System.out.print("Enter release year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.year == year)
                .forEach(System.out::println);
    }

    static void getMoviesByYearRange(Scanner scanner) {
        System.out.print("Enter start year: ");
        int startYear = scanner.nextInt();
        System.out.print("Enter end year: ");
        int endYear = scanner.nextInt();
        scanner.nextLine();

        movies.values().stream()
                .filter(m -> m.year >= startYear && m.year <= endYear)
                .forEach(System.out::println);
    }

    static void addMovie(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter rating: ");
        float rating = scanner.nextFloat();
        System.out.print("Enter duration: ");
        int duration = scanner.nextInt();
        System.out.print("Enter director ID: ");
        int dirID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter actor IDs (comma-separated): ");
        List<Integer> actorIDs = new ArrayList<>();
        String[] actorIdsStr = scanner.nextLine().split(",");
        for (String actorId : actorIdsStr) {
            actorIDs.add(Integer.parseInt(actorId.trim()));
        }

        movies.put(id, new Movie(id, title, year, genre, rating, duration, dirID, actorIDs));
        System.out.println("Movie added successfully!");
    }

    static void updateMovieRating(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new rating: ");
        float rating = scanner.nextFloat();
        scanner.nextLine();

        Movie movie = movies.get(id);
        if (movie != null) {
            movie.rating = rating;
            System.out.println("Rating updated successfully!");
        } else {
            System.out.println("Movie not found!");
        }
    }

    static void deleteMovie(Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (movies.remove(id) != null) {
            System.out.println("Movie deleted successfully!");
        } else {
            System.out.println("Movie not found!");
        }
    }

    static void getTopMoviesByYear() {
        movies.values().stream()
                .sorted(Comparator.comparingInt(m -> -m.year))
                .limit(15)
                .forEach(System.out::println);
    }

    static void getTopDirectors() {
        Map<Integer, Long> directorCount = movies.values().stream()
                .collect(Collectors.groupingBy(m -> m.dirID, Collectors.counting()));

        directorCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.println("Director ID: " + e.getKey() + " | Movies: " + e.getValue()));
    }

    static void getActorsWithMostMovies() {
        Map<Integer, Long> actorCount = movies.values().stream()
                .flatMap(m -> m.actorIDs.stream())
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));

        actorCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> System.out.println("Actor ID: " + e.getKey() + " | Movies: " + e.getValue()));
    }

    static void getYoungestActorMovies() {
        Optional<Actor> youngestActor = actors.values().stream()
                .min((a1, a2) -> {
                    // Parse birthYear strings to integers for comparison
                    int year1 = Integer.parseInt(a1.birthYear);
                    int year2 = Integer.parseInt(a2.birthYear);
                    return Integer.compare(year1, year2);
                });

        youngestActor.ifPresent(actor -> {
            System.out.println("Youngest Actor: " + actor.name + " (Born: " + actor.birthYear + ")");
            movies.values().stream()
                    .filter(m -> m.actorIDs.contains(actor.id))
                    .forEach(System.out::println);
        });
    }
}