import java.util.Arrays;
import java.util.Scanner;

public class q1 {

    private static Scanner sc = new Scanner(System.in);
    public static int counter = 0;
    
    // Returns the number of digits in the maximum number of the array.
    public static int getLength(int arr[]) {
        counter++; // method call 
        int max = arr[0];
        counter += 2; // assignment and lookup
        int length = 0;
        counter ++; // assignment
        for (int i = 1; i < arr.length; i++) { 
            counter += 3; // assignment, comparison, addition
            if (arr[i] > max) {
                max = arr[i];
                counter += 3; // comparison, assignment and lookup
            }
        }
        while (max > 0) {
            max /= 10;
            length++;
            counter += 5; //comparison, 2 assignment, division and addition
        }
        
        counter++; //return
        return length;
    }

    // Reads user input to create and return an integer array.
    public static int[] getNumInput() {
        System.out.print("Enter the number of elements in the array: ");
        int n = sc.nextInt();
        int arr[] = new int[n];
        System.out.println("Enter the elements of the array: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println("Input array: " + Arrays.toString(arr));
        return arr;
    }

    // Distributes numbers into buckets according to their units (ones) digit.
    public static void unitDigitSort(int arr[], int arrayBuckets[][]) {
        counter ++; //method call
        for (int i = 0; i < arr.length; i++) {
            counter += 3; // assignment, comparison and addition
            int num = arr[i];
            counter += 2; // assignment and lookup
            int digit = (num / 1) % 10;
            counter += 3; // assignment, division, and modulus
            for (int j = 0; j < arrayBuckets[digit].length; j++) {
                counter += 3; // assignment ,comparison, addition
                if (arrayBuckets[digit][j] == -1) {
                    counter ++; //comparison
                    arrayBuckets[digit][j] = num;
                    counter += 2; // assignment and lookup
                    break;
                }
            }
        }
    }

    // Sorts the numbers according to subsequent digit positions using alternate buckets.
    public static void subsequentDigitSort(int[][] current, int[][] next, int exp) {
        counter ++; // method call
        for (int i = 0; i < current.length; i++) {
            counter += 3; //assignment, comparision and addition
            for (int j = 0; j < current[i].length; j++) {
                counter += 3; // assignment, comparison and addition
                int val = current[i][j];
                counter += 2; //assignment and lookup
                if (val != -1) {
                    counter++; //comparison
                    int digit = (val / exp) % 10;
                    counter += 3; // assignment, division and modulus
                    for (int k = 0; k < next[digit].length; k++) {
                        counter += 3; // assignment, comparison and addition
                        if (next[digit][k] == -1) {
                            counter++; //comparison
                            next[digit][k] = val;
                            counter += 2; // assignment and lookup
                            break;
                        }
                    }
                }
            }
        }
    }

    // Converts the 2D buckets into a flattened 1D sorted array.
    public static int[] flattenArrayBuckets(int[][] arrayBuckets, int size) {
        counter ++; // method call
        int[] result = new int[size];
        int index = 0;
        counter += 2; // 2 assignments
        for (int i = 0; i < arrayBuckets.length; i++) {
            counter += 3; // assignment, comparison and addition
            for (int j = 0; j < arrayBuckets[i].length; j++) {
                counter += 3; // assignment, comparison and addition
                if (arrayBuckets[i][j] != -1) {
                    counter++; //comparison
                    result[index++] = arrayBuckets[i][j];
                    counter += 3; // assignment, lookup and addition
                }
            }
        }
        counter++;
        return result;
    }

    // Prints the contents of each bucket for visualization.
    public static void printArrayBuckets(int[][] arrayBuckets) {
        for (int i = 0; i < arrayBuckets.length; i++) {
            System.out.printf("arrayBuckets %d: [", i);
            boolean first = true;
            for (int num : arrayBuckets[i]) {
                if (num != -1) {
                    if (!first) System.out.print(", ");
                    System.out.print(num);
                    first = false;
                }
            }
            System.out.println("]");
        }
    }

    // Prints the 1D array of numbers.
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    // Main method that initializes input, performs sorting, and displays the sorted numbers.
    public static void main(String[] args) {
        System.out.println("Welcome to the Numbers Sorting Program!");
        int[] nums = getNumInput();
        int passes = getLength(nums);

        int[][] arrayBuckets1 = new int[10][nums.length];
        int[][] arrayBuckets2 = new int[10][nums.length];
        for (int i = 0; i < 10; i++) {
            Arrays.fill(arrayBuckets1[i], -1);
            Arrays.fill(arrayBuckets2[i], -1);
        }

        unitDigitSort(nums, arrayBuckets1);
        System.out.println("\nAfter pass 1 (digit place = 1):");
        printArrayBuckets(arrayBuckets1);
        System.out.print("Flattened: ");
        printArray(flattenArrayBuckets(arrayBuckets1, nums.length));

        int exp = 10;
        boolean isCurrentArrayBuckets1 = true;

        for (int pass = 2; pass <= passes; pass++) {
            System.out.printf("%nAfter pass %d (digit place = %d):%n", pass, exp);
            if (isCurrentArrayBuckets1) {
                for (int i = 0; i < 10; i++) Arrays.fill(arrayBuckets2[i], -1);
                subsequentDigitSort(arrayBuckets1, arrayBuckets2, exp);
                printArrayBuckets(arrayBuckets2);
                System.out.print("Flattened: ");
                printArray(flattenArrayBuckets(arrayBuckets2, nums.length));
            } else {
                for (int i = 0; i < 10; i++) Arrays.fill(arrayBuckets1[i], -1);
                subsequentDigitSort(arrayBuckets2, arrayBuckets1, exp);
                printArrayBuckets(arrayBuckets1);
                System.out.print("Flattened: ");
                printArray(flattenArrayBuckets(arrayBuckets1, nums.length));
            }
            exp *= 10;
            isCurrentArrayBuckets1 = !isCurrentArrayBuckets1;
        }

        int[] sortedNums = isCurrentArrayBuckets1 ?
                flattenArrayBuckets(arrayBuckets1, nums.length) :
                flattenArrayBuckets(arrayBuckets2, nums.length);
        System.out.println("\nFinal Sorted Numbers:");
        printArray(sortedNums);
        System.out.println("Total primitive operations: " + counter);
    }
}
