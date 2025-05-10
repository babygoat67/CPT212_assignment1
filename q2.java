import java.util.Arrays;
import java.util.Scanner;

public class q2 {

    private static Scanner sc = new Scanner(System.in);
    public static int counter = 0;

    // Returns the maximum length among all words
    public static int getLength(String arr[]) {
        counter++; // method call
        int max = arr[0].length();
        counter += 3; // assignment, lookup and function call
        for (int i = 1; i < arr.length; i++) {
            counter += 3;// assignment, comparison and addition
            if (arr[i].length() > max) {
                counter += 3; // lookup, function call and comparison
                max = arr[i].length();
                counter += 3; // assignment, lookup and function call
            }
        }
        counter++; // return
        return max;
    }

    // Reads user input to create and return a String array,
    // converting every word to lowercase.
    public static String[] getStringInput() {
        System.out.print("Enter the number of elements in the array: ");
        int n = sc.nextInt();
        String arr[] = new String[n];
        System.out.println("Enter the elements of the array: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.next().toLowerCase(); // Convert input to lowercase
        }
        System.out.println("Input array: " + Arrays.toString(arr));
        return arr;
    }

    // Places words into buckets based on the character at the specified position.
    // Sorting starts from the last character of each word.
    public static void charPositionSort(String[] words, String[][] arrayBuckets, int pos) {
        counter++; // method call

        for (int i = 0; i < words.length; i++) {
            counter += 3; // assignment, comparison and addition
            String word = words[i];
            counter += 2; // assignment and lookup
            int bucketIndex = 0;
            counter++; // assignment
            if (word.length() - 1 - pos >= 0) { // Get character from the end
                counter += 4; // function call, 2 subtraction and comparison
                bucketIndex = word.charAt(word.length() - 1 - pos) - 'a' + 1;
                counter += 7; // assignment, 2 function call, 3 subtraction, addition
            }
            for (int j = 0; j < arrayBuckets[bucketIndex].length; j++) {
                counter += 3; // assignment, comparison and addition
                if (arrayBuckets[bucketIndex][j] == null) {
                    counter++; // comparison
                    arrayBuckets[bucketIndex][j] = word;
                    counter++; // assignment
                    break;
                }
            }
        }
    }

    // Processes subsequent passes by distributing words into new buckets,
    // based on the character at the current position from the end.
    public static void subsequentCharSort(String[][] current, String[][] next, int pos) {
        counter++; // method call
        for (int i = 0; i < current.length; i++) {
            counter += 3; // assignment, comparison and addition
            for (int j = 0; j < current[i].length; j++) {
                counter += 3; // assignment, comparison and addition
                String word = current[i][j];
                counter += 2; // assignment and lookup
                if (word != null) {
                    counter++; // comparison
                    int bucketIndex = 0;
                    counter++; // assignment
                    if (word.length() - 1 - pos >= 0) { // Get character from the end
                        counter += 4; // function call, 2 subtraction and comparison
                        bucketIndex = word.charAt(word.length() - 1 - pos) - 'a' + 1;
                        counter += 7; // assignment, 2 function call, 3 subtraction, addition
                    }
                    for (int k = 0; k < next[bucketIndex].length; k++) {
                        counter += 3; // assignment, comparison and addition
                        if (next[bucketIndex][k] == null) {
                            counter++; // comparison
                            next[bucketIndex][k] = word;
                            counter++; // assignment
                            break;
                        }
                    }
                }
            }
        }
    }

    // Flattens the 2D array buckets into a single 1D array of sorted words.
    public static String[] flattenWordBuckets(String[][] arrayBuckets, int size) {
        counter++; // method call
        String[] result = new String[size];
        int index = 0;
        counter += 2; // 2 assignments

        for (int i = 0; i < arrayBuckets.length; i++) {
            counter += 3; // assignment, comparison and addition
            for (int j = 0; j < arrayBuckets[i].length; j++) {
                counter += 3; // assignment, comparison and addition
                if (arrayBuckets[i][j] != null) {
                    counter++; // comparison
                    result[index++] = arrayBuckets[i][j];
                    counter += 3; // assignment, lookup and addition
                }
            }
        }
        counter += 2; // return, function call
        return Arrays.copyOf(result, index);
    }

    // Prints each word bucket with a corresponding letter label.
    public static void printWordBuckets(String[][] arrayBuckets) {
        for (int i = 0; i < arrayBuckets.length; i++) {
            String label = (i == 0) ? "Bucket _" : "Bucket " + (char)('a' + i - 1);
            System.out.printf("%s: [", label);
            boolean first = true;
            for (String word : arrayBuckets[i]) {
                if (word != null) {
                    if (!first) System.out.print(", ");
                    System.out.print(word);
                    first = false;
                }
            }
            System.out.println("]");
        }
    }

    // Prints the flattened, sorted array of words.
    public static void printWords(String[] words) {
        System.out.println(Arrays.toString(words));
    }

    // Main method that initializes input, sorts the words, and displays the results.
    public static void main(String[] args) {
        System.out.println("Welcome to the Words Sorting Program!");
        String[] words = getStringInput();
        int maxLength = getLength(words);

        String[][] arrayStrBuckets1 = new String[27][words.length];
        String[][] arrayStrBuckets2 = new String[27][words.length];
        boolean useArrayStrBuckets1 = true;

        // Process from last character (pos = 0) to the first character
        for (int pos = 0; pos < maxLength; pos++) {
            System.out.printf("%nAfter pass %d:%n", pos + 1);

            if (useArrayStrBuckets1) {
                for (int i = 0; i < 27; i++) Arrays.fill(arrayStrBuckets2[i], null);

                if (pos == 0) {
                    charPositionSort(words, arrayStrBuckets2, pos);
                } else {
                    subsequentCharSort(arrayStrBuckets1, arrayStrBuckets2, pos);
                }
                printWordBuckets(arrayStrBuckets2);
                System.out.print("Flattened: ");
                printWords(flattenWordBuckets(arrayStrBuckets2, words.length));
            } else {
                for (int i = 0; i < 27; i++) Arrays.fill(arrayStrBuckets1[i], null);
                subsequentCharSort(arrayStrBuckets2, arrayStrBuckets1, pos);
                printWordBuckets(arrayStrBuckets1);
                System.out.print("Flattened: ");
                printWords(flattenWordBuckets(arrayStrBuckets1, words.length));
            }

            useArrayStrBuckets1 = !useArrayStrBuckets1;
        }

        String[] sortedWords = useArrayStrBuckets1 ?
                flattenWordBuckets(arrayStrBuckets1, words.length) :
                flattenWordBuckets(arrayStrBuckets2, words.length);

        System.out.println("\nFinal Sorted Words:");
        printWords(sortedWords);
        System.out.println("Total primitive operations: " + counter);
    }
}
