import java.util.Arrays;
import java.util.Scanner;

public class q2 {

    private static Scanner sc = new Scanner(System.in);

    // Returns the maximum length among all words,
    // which determines the number of passes needed.
    public static int getLength(String arr[]) {
        int max = arr[0].length();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].length() > max) {
                max = arr[i].length();
            }
        }
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
    // Uses bucket index 0 for words that have no character at that position.
    public static void charPositionSort(String[] words, String[][] arrayBuckets, int pos) {
        for (String word : words) {
            // Default bucket index 0 for words lacking a character at this position.
            int bucketIndex = 0;
            if (pos < word.length()) {
                // Calculate index based on the character's offset from 'a'. The +1 shift reserves bucket 0.
                bucketIndex = word.charAt(pos) - 'a' + 1;
            }
            // Traverse the selected bucket to locate an empty slot.
            for (int j = 0; j < arrayBuckets[bucketIndex].length; j++) {
                if (arrayBuckets[bucketIndex][j] == null) {
                    arrayBuckets[bucketIndex][j] = word;
                    // After placing the word, break to avoid duplicate entries.
                    break;
                }
            }
        }
    }

    // Processes subsequent passes by distributing words into new buckets,
    // based on the character at the current position.
    public static void subsequentCharSort(String[][] current, String[][] next, int pos) {
        for (int i = 0; i < current.length; i++) {
            // Process each bucket from the current bucket array.
            for (int j = 0; j < current[i].length; j++) {
                String word = current[i][j];
                if (word != null) {
                    // Default bucket for words that do not have a character at pos.
                    int bucketIndex = 0;
                    if (pos < word.length()) {
                        // Calculate bucket index from the character at position pos.
                        bucketIndex = word.charAt(pos) - 'a' + 1;
                    }
                    // Find an empty slot in the target bucket within the next array.
                    for (int k = 0; k < next[bucketIndex].length; k++) {
                        if (next[bucketIndex][k] == null) {
                            next[bucketIndex][k] = word;
                            // Word placed in the bucket; exit the loop.
                            break;
                        }
                    }
                }
            }
        }
    }

    // Flattens the 2D array buckets into a single 1D array of sorted words.
    public static String[] flattenWordBuckets(String[][] arrayBuckets, int size) {
        String[] result = new String[size];
        int index = 0;

        for (int i = 0; i < arrayBuckets.length; i++) {
            for (int j = 0; j < arrayBuckets[i].length; j++) {
                if (arrayBuckets[i][j] != null) {
                    result[index++] = arrayBuckets[i][j];
                }
            }
        }
        return Arrays.copyOf(result, index);
    }

    // Prints each word bucket with a corresponding letter label.
    public static void printWordBuckets(String[][] arrayBuckets) {
        for (int i = 1; i < arrayBuckets.length; i++) {
            char label = (char) ('a' + i - 1);
            System.out.printf("Bucket %c: [", label);
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

        for (int pos = maxLength - 1; pos >= 0; pos--) {
            System.out.printf("%nSorting position %d:%n", pos + 1);

            if (useArrayStrBuckets1) {
                for (int i = 0; i < 27; i++) Arrays.fill(arrayStrBuckets2[i], null);

                if (pos == maxLength - 1) {
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
    }
}
