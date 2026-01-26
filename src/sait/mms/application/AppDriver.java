package sait.mms.application;

import sait.mms.manager.*;
public class AppDriver {
    public static void main(String[] args) {
        MovieManager manager = new MovieManager();
        manager.loadMovieList();
        manager.displayMenu();
    }
}
