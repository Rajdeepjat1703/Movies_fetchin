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
}