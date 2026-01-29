package sait.mms.manager;

import java.io.*; //We import all input/output utility per https://www.geeksforgeeks.org/java/java-io-input-output-in-java-with-examples/
import java.util.*; //We import all general utility
import sait.mms.problemdomain.Movie; //We import the problemdomain Movie package

public class MovieManager {

    private ArrayList<Movie> movies; //Movie is the variable type that we want to add in the ArrayList that we imported from problemdomain
    private Scanner userInput;

    private static final String FILE_PATH = "res/movies.txt"; //This is the path to the text file itself

    public MovieManager() {
        this.movies = new ArrayList<>();
        this.userInput = new Scanner(System.in);
    }

    //method to load movies from text file

    public void loadMovieList() {
        File file = new File(FILE_PATH);

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(","); //Splits the syntax in the text file
                if (parts.length == 3) {
                    int duration = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    int year = Integer.parseInt(parts[2]);

                    movies.add(new Movie(duration, title, year));
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_PATH);
        }
    }

    public void displayMenu() { //display menu per assignment requirements
        int option = 0;
        while (option != 4) {
            System.out.println("Movie Management System");
            System.out.println("1     Add New movie and Save");
            System.out.println("2     Generate List of Movies Released in a Year");
            System.out.println("3     Generate list of Random Movies");
            System.out.println("4     Exit");

            if (userInput.hasNextInt()) {
                option = userInput.nextInt();
                userInput.nextLine();

                switch(option) {
                    case 1:
                        addMovie();
                        break;
                    case 2: 
                        generateMovieListInYear();
                        break;
                    case 3: 
                        generateRandomMovieList();
                        break;
                    case 4: 
                        System.out.println("Exiting...");
                        saveMovieListToFile();
                        break;
                    default:
                        System.out.println("Invalid Option!");
                }
            } else {
                System.out.println("Invalid Input. Try again!");
                userInput.nextLine();
            }
            System.out.println();
        }
    }

    public void addMovie(){
        int duration = -1;
        while (duration <= 0){
            System.out.print("Enter duration: ");
            if (userInput.hasNextInt()){
                duration = userInput.nextInt();
                if (duration <= 0){
                    System.out.println("Must be a valid duration!");
                }
            } else {
                System.out.println("Invalid value. Please enter a positive time value!");
                userInput.next();
            }
        }
        userInput.nextLine();
        String title = "";
        while (title.isEmpty()){    //isblank could probably work here also
            System.out.print("Enter movie title: ");
            title = userInput.nextLine();
            if (title.isEmpty()){
                System.out.println("Title cannot be empty!");
            }
        } 
        int year = -1;
        while (year <= 0){
            System.out.print("Enter year: ");
            if (userInput.hasNextInt()){
                year = userInput.nextInt();
                if (year <= 0){
                    System.out.println("Must be a valid year!");
                }
            } else {
                System.out.println("Invalid value. Please enter a positive time value!");
                userInput.next();
            }
        } 
        movies.add(new Movie(duration, title, year));
        System.out.println("Saving movies...");
        saveMovieListToFile();
        System.out.println("Added movie to the data file.\n");
    }
    
    
    
    /*public void addMovie() {          Has no verification checks
        System.out.print("Enter duration: ");
        int duration = userInput.nextInt();
        userInput.nextLine();
        System.out.print("Enter movie title: ");
        String title = userInput.nextLine();
        System.out.print("Enter year: ");
        int year = userInput.nextInt();
        userInput.nextLine();

        movies.add(new Movie(duration, title, year));
        System.out.println("Saving movies...");
        saveMovieListToFile();
        System.out.println("Added movie to the data file.\n");
    }*/

    public void generateMovieListInYear(){
        System.out.print("Enter in year: ");
        int year = userInput.nextInt();
        System.out.println("\nMovie List:");
        System.out.printf("%-10s %-8s %s%n", "Duration","Year","Title"); //per https://www.w3schools.com/java/ref_string_format.asp 

        int totalDuration = 0;
        for (Movie m : movies) {
            if (m.getYear() == year) {
                System.out.printf("%-10d %-8d %s%n", m.getDuration(), m.getYear(), m.getTitle());
                totalDuration += m.getDuration();
            }
        }
        System.out.println("Total duration: " + totalDuration + " minutes\n");
    }

    public void generateRandomMovieList() {
        System.out.print("Enter number of movies: ");
        int count = userInput.nextInt();
        ArrayList<Movie> tempList =  new ArrayList<>(movies);
        Collections.shuffle(tempList); //per https://www.geeksforgeeks.org/java/collections-shuffle-method-in-java-with-examples/
        System.out.println("\nMovie List:");
        System.out.printf("%-10s %-8s %s%n", "Duration","Year","Title");

        int totalDuration = 0;
        for (Movie m : tempList.subList(0, count)){
            System.out.printf("%-10d %-8d %s%n", m.getDuration(), m.getYear(), m.getTitle());
            totalDuration += m.getDuration();
        }
        System.out.println("Total duration: " + totalDuration + " minutes\n");
    }

    public void saveMovieListToFile(){
        try (PrintWriter pw = new PrintWriter(new File(FILE_PATH))){
            for (Movie m : movies){
                pw.println(m.toString());
            }
        } catch (FileNotFoundException e){
            System.out.println("Error saving movies.");
        }
    }
}