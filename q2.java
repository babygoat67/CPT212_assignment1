import java.util.Arrays;
import java.util.Scanner;

public class q2 {

    private static Scanner sc = new Scanner(System.in);
    public static int counter = 0; // Counter for primitive operations

    // Returns the maximum length among all words.
    public static int getLength(String arr[]) {
        counter++; // method call
        int max = arr[0].length();
        counter += 3; // assignment, lookup and function call
        for (int i = 1; i < arr.length; i++) {
            counter += 3; // assignment, comparison and addition
            if (arr[i].length() > max) {
                counter += 3; // lookup, function call and comparison
                max = arr[i].length();
                counter += 3; // assignment, lookup and function call
            }
        }
        counter++; // return
        return max;
    }

    // Reads user input to create and return a String array
    public static String[] getStringInput() {
        System.out.print("Enter the number of elements in the array: ");
        int n = sc.nextInt();
        String arr[] = new String[n];
        System.out.println("Enter the elements of the array: ");
        for (int i = 0; i < n; i++) {
            // Existing conversion to lowercase and removal of commas
            arr[i] = sc.next().replace(",", "").toLowerCase(); 
        }
        System.out.println("Input array: " + Arrays.toString(arr));
        return arr;
    }

    // Places words into buckets based on the character at the specified position.
    // Bucket index 0 is used for words that do not have a character at that position.
    public static void charPositionSort(String[] words, String[][] arrayBuckets, int pos) {
        counter++; // method call
        for (int i = 0; i < words.length; i++) {
            counter += 3; // assignment, comparison and addition
            String word = words[i]; 
            counter += 2; // assignment and lookup
            int bucketIndex = 0;
            counter++ ; // assignment
            // If the word has a character at position 'pos', determine its bucket.
            if (pos < word.length()) {
                counter += 2; // comparison and function call
                bucketIndex = word.charAt(pos) - 'a' + 1;
                counter += 4; // assignment, function call, subtraction and addition
            }
            // Place the word into the first available slot in the correct bucket.
            for (int j = 0; j < arrayBuckets[bucketIndex].length; j++) {
                counter += 3; // assignment, comparison and addition
                if (arrayBuckets[bucketIndex][j] == null) {
                    counter++; // comparison
                    arrayBuckets[bucketIndex][j] = word;
                    counter++ ; // assignment
                    break;
                }
            }
        }
    }

    // Processes subsequent passes by distributing words from the current bucket array
    // to the next bucket array based on the character at the current position.
    // This method is used after the initial pass.
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
                    // Distribute word to the next bucket structure based on the character.
                    if (pos < word.length()) {
                        counter += 2; // comparison and function call
                        bucketIndex = word.charAt(pos) - 'a' + 1;
                        counter += 4; // assignment, function call, subtraction and addition
                    }
                    // Place word in the first available slot in the selected bucket.
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
        return Arrays.copyOf(result, index);
    }

    // Prints each word bucket with a corresponding letter label.
    public static void printWordBuckets(String[][] arrayBuckets) {
    for (int i = 0; i < arrayBuckets.length; i++) {
        String label;
        if (i == 0) {
            label = "_";
        } else {
            label = String.valueOf((char)('a' + i - 1));
        }

        System.out.printf("Bucket %s: [", label);

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

    // Main method that initializes input, performs sorting, and displays the results.
    public static void main(String[] args) {
        System.out.println("Welcome to the Words Sorting Program!");
        String[] words = getStringInput();
        int maxLength = getLength(words);

        // Initialize two sets of buckets for alternating sorting passes.
        String[][] arrayStrBuckets1 = new String[27][words.length];
        String[][] arrayStrBuckets2 = new String[27][words.length];
        boolean useArrayStrBuckets1 = true;

        // Perform the sorting passes from the most significant position to the first.
        for (int pos = maxLength - 1; pos >= 0; pos--) {
            System.out.printf("%nSorting position %d:%n", pos + 1);

            if (useArrayStrBuckets1) {
                // Clear the secondary bucket array before use.
                for (int i = 0; i < 27; i++) Arrays.fill(arrayStrBuckets2[i], null);

                if (pos == maxLength - 1) {
                    // Initial sort - distribute words based on current character.
                    charPositionSort(words, arrayStrBuckets2, pos);
                } else {
                    // Subsequent passes - re-distribute words from previous buckets.
                    subsequentCharSort(arrayStrBuckets1, arrayStrBuckets2, pos);
                }
                printWordBuckets(arrayStrBuckets2);
                System.out.print("Flattened: ");
                printWords(flattenWordBuckets(arrayStrBuckets2, words.length));
            } else {
                // Alternate bucket usage for subsequent passes.
                for (int i = 0; i < 27; i++) Arrays.fill(arrayStrBuckets1[i], null);
                subsequentCharSort(arrayStrBuckets2, arrayStrBuckets1, pos);
                printWordBuckets(arrayStrBuckets1);
                System.out.print("Flattened: ");
                printWords(flattenWordBuckets(arrayStrBuckets1, words.length));
            }

            // Toggle the active buckets for the next pass.
            useArrayStrBuckets1 = !useArrayStrBuckets1;
        }

        // Final flattening of the sorted buckets to produce the sorted array.
        String[] sortedWords = useArrayStrBuckets1 ?
                flattenWordBuckets(arrayStrBuckets1, words.length) :
                flattenWordBuckets(arrayStrBuckets2, words.length);

        System.out.println("\nFinal Sorted Words:");
        printWords(sortedWords);
        System.out.println("Total number of operations: " + counter);
    }
}
