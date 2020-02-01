    //Gabriel Lam
//Assignment 1
//31/1/2019
//this lap has no bugs works perfect, allow user to create a search word game, and shows answer and print game.
// for extra credit : 1 there is a hint section showing how many rows columns and obliques.
//                    2 the map will change size based on the numbers of inputs.
//                    3 there is a random reverse function and will be called randomly.
// improvement : will work on overlaps which there is same letter.
//               data should be private will be move to private.

package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
    private static String[][] list;
    private static int mapSize;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        String option = " ";

        while (!option.equals("Q")) {
            intro();
            option = console.next();
            if (option.equals("g")) {
                getWords();
            } else if (option.equals("p")) {
                printGame(list, mapSize);
            } else if (option.equals("s")) {
                printSolution(list, mapSize);
            } else System.out.println("please select enter a valid  option.");
        }
    }

    public static void intro() {
        System.out.println("Welcome to my word search generator!");
        System.out.println("This Programs will allow you to generate your own word search puzzle");
        System.out.println("Please select and option:");
        System.out.println("Print our your word search(p)");
        System.out.println("Generate a new word search(g)");
        System.out.println("Show the solution to your word search (s)");
        System.out.println("Quit the program (q)");
    }

    // Get user input words
    public static void getWords() {
        Scanner console = new Scanner(System.in);
        String[] words;
        int numberOfWords;
        int wordLength;
        int theLongestLength = 0;
        String currentWord;

        System.out.println("how many words you would like you have ?");
        numberOfWords = console.nextInt();
        words = new String[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            System.out.println("Enter your words");
            words[i] = console.next();
            currentWord = words[i].toUpperCase();
            wordLength = currentWord.length();
            if (wordLength > theLongestLength) {
                theLongestLength = currentWord.length();
            }
        }

        ArrayList<String> wordList = new ArrayList<String>();
        for (String x : words) {
            wordList.add(x);
        }
        sizeMap(numberOfWords, theLongestLength, wordList);
    }

    //print map and size setting
    public static void sizeMap(int N1, int N2, ArrayList<String> list) {
        ArrayList<String> wordList = list;
        int theLongestLength = N2;
        int mapSize = 8;
        if(theLongestLength >8)
            mapSize = theLongestLength;
        else if(N1 >15){
            mapSize = N1;
        }
        int rowCount = 0;
        int Columns = 0;
        int oblique = 0;

        Random generator = new Random();
        String[][] thingy = new String[mapSize][mapSize];
        //count rows or columns
        for (int i = 0; i < wordList.size(); i++) {
            String wordSet = wordList.get(i);
            int randomX = generator.nextInt(mapSize);
            int randomY = generator.nextInt(mapSize);
            int rowsOrColumns = generator.nextInt(3);
            if (rowsOrColumns == 0) {
                rowCount++;
            }
            if (rowsOrColumns == 1) {
                Columns++;
            }
            if (rowsOrColumns == 2) {
                oblique++;
            }
            //check letters placement ( if its open for placement  so it wouldn't overlap)
            while (!check(wordSet, randomX, randomY, rowsOrColumns, thingy,mapSize)) {
                randomX = generator.nextInt(mapSize);
                randomY = generator.nextInt(mapSize);
                rowsOrColumns = generator.nextInt(3);
                check(wordSet, randomX, randomY, rowsOrColumns, thingy,mapSize);
            }


            // confirm letters placement
            for (int k = 0; k < wordSet.length(); k++) {
                char word = wordSet.charAt(k);
                int reverse = generator.nextInt(1);
                    if (reverse == 1){
                        String what = setreverse(wordSet);
                        word = what.charAt(i);

                    }
                word = Character.toUpperCase(word);
                char valueOfChar = (word);
                String charToString = String.valueOf(valueOfChar);
                while (mapSize < randomX + wordSet.length()) {
                    randomX -= 1;

                    //System.out.println("randomX: " + randomX +"mapsize"+mapSize);
                }
                while (mapSize < randomY + wordSet.length()) {
                        randomY -= 1;
                    //System.out.println("randomY: " + randomY+"mapsize"+mapSize);
                }
                thingy[xLocation(randomX, k, rowsOrColumns)][yLocation(randomY, k, rowsOrColumns)] = charToString;
                //ystem.out.println("rowsOrColumns" + rowsOrColumns);
            }
        }

        // print game
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {

                if (thingy[x][y] != null) {
                    System.out.print(thingy[x][y] + "   ");
                } else
                    System.out.print((char) (65 + generator.nextInt(25)) + "   ");
            }
            System.out.println();
            System.out.println();
            Main.list = thingy;
            Main.mapSize = mapSize;

        }
        // note that the columns and rows means each other
        System.out.println("There are " + rowCount + " columns");
        System.out.println("There are " + Columns + " rows");
        System.out.println("There are " + oblique+ " oblique");
        System.out.println();
        System.out.println();
    }
//check if the location is open or null.
    public static boolean check(String wordSet, int randomX, int randomY, int rowsOrColumns, String[][] thingy, int mapSize) {

        for (int k = 0; k < wordSet.length(); k++) {
            char word = wordSet.charAt(k);
            word = Character.toUpperCase(word);
            char valueOfChar = (word);
            String charToString = String.valueOf(valueOfChar);
            while (mapSize < randomX + wordSet.length()) {

                randomX -= 1;
                //System.out.println("randomX: " + randomX);
            }
            while (mapSize < randomY + wordSet.length()) {

                randomY -= 1;
                //System.out.println("randomY: " + randomY);
            }
            if (thingy[xLocation(randomX, k, rowsOrColumns)][yLocation(randomY, k, rowsOrColumns)] != null)
                return false;

        }return true;
    }
    public static String setreverse( String word ){

        String reversedWord ="";
        for ( int i = word.length(); i < 0 ; i --   ){
            reversedWord = reversedWord + word.charAt(i);
        }
        System.out.println("the setreverse is working :" +reversedWord);
            return reversedWord;
    }
    // print solution
    public static void printSolution(String[][] list, int mapSize) {
        String[][] thingy = list;
        System.out.println("YOUR SOLUTION IS RIGHT HERE !");
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {

                if (thingy[x][y] != null) {
                    System.out.print(thingy[x][y] + "   ");
                } else
                    System.out.print(" " + "   ");
            }
            System.out.println();
            System.out.println();
        }
    }

    // for x location
    public static int xLocation(int x, int k, int rowsOrColumns) {
        if (rowsOrColumns == 0) {
            int location = x + k;
            return location;
        }
        if (rowsOrColumns == 1) {
            int location = x;
            return location;
        }
        if (rowsOrColumns == 2) {
            int location = x + k;
            return location;
        }
        return x;
    }

    // for Y location
    public static int yLocation(int y, int k, int rowsOrColumns) {
        if (rowsOrColumns == 1) {
            int location = y + k;
            return location;
        }
        if (rowsOrColumns == 2) {
            int location = y + k;
            return location;
        }
        if (rowsOrColumns == 3) {
            int location = y;
            return location;
        }
        return y;
    }

    public static void printGame(String[][] list, int mapSize) throws FileNotFoundException {
        String[][] thingy = list;
        Random generator = new Random();
        Scanner console = new Scanner(System.in);
        System.out.println("Name of the out.print file:");
        String name = console.nextLine();
        PrintStream out = new PrintStream(new File(name));

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {

                if (thingy[x][y] != null) {
                    out.print(thingy[x][y] + "   ");
                } else
                    out.print((char) (65 + generator.nextInt(25)) + "   ");
            }
            out.println();
            out.println();
        }
        System.out.println("your file is printed");
    }
}




