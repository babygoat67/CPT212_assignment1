import java.util.Arrays;
import java.util.Scanner;

public class q1 {

    private static Scanner sc = new Scanner(System.in);
    public static int counter = 0;  // counter for primitive operations
    
    // Returns the maximum number of digits in the array.
    public static int getLength(int arr[]) {
        counter++; // method call 
        int max = arr[0];
        counter += 2; // assignment and lookup
        int length = 0;
        counter ++; // assignment
        for (int i = 1; i < arr.length; i++) {  // loop through the array to find the maximum number
            counter += 3; // assignment, comparison, addition
            if (arr[i] > max) {
                max = arr[i];
                counter += 3; // comparison, assignment and lookup
            }
        }
        while (max > 0) {   // compute the number of digits in that maximum number
            max /= 10;
            length++;
            counter += 5; //comparison, 2 assignment, division and addition
        }
        
        counter++; //return
        return length;
    }

    // Reads user input to create and return a integer array
    public static int[] getNumInput() {
        System.out.print("Enter the number of elements in the array: ");
        int n = sc.nextInt();
        sc.nextLine(); 
        int arr[] = new int[n];
        System.out.println("Enter the elements of the array (separated by space or comma): ");
        String input = sc.nextLine();
        String[] tokens = input.replace(",", " ").split("\\s+");    // split by space or comma for easier parsing
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }
        System.out.println("Input array: " + Arrays.toString(arr));
        return arr;
    }

    // First pass: distributes numbers into buckets (0-9) according to their units digit.
    public static void unitDigitSort(int arr[], int arrayBuckets[][]) {
        counter ++; //method call
        for (int i = 0; i < arr.length; i++) {  // loop through the numbers to be sorted
            counter += 3; // assignment, comparison and addition
            int num = arr[i];
            counter += 2; // assignment and lookup
            int digit = (num / 1) % 10;
            counter += 3; // assignment, division, and modulus
            for (int j = 0; j < arrayBuckets[digit].length; j++) {  // place the number into the first available slot in the bucket
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

    // Subsequent passes: sorts the numbers according to subsequent digit positions (tens, hundresds, and so on) using alternate buckets.
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
                    int digit = (val / exp) % 10;   // get the digit at the current place value
                    counter += 3; // assignment, division and modulus
                    for (int k = 0; k < next[digit].length; k++) {
                        counter += 3; // assignment, comparison and addition
                        if (next[digit][k] == -1) {
                            counter++; //comparison
                            next[digit][k] = val;   // place the number in the appropriate bucket
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

    // Prints the contents of each bucket for visualization in each pass.
    public static void printArrayBuckets(int[][] arrayBuckets) {
        for (int i = 0; i < arrayBuckets.length; i++) {
            System.out.printf("arrayBuckets %d: [", i);
            boolean first = true;
            for (int num : arrayBuckets[i]) {
                if (num != -1) {    // filter out empty (-1) values which will not be output
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
        int[] nums = getNumInput(); // get input array from user
        int passes = getLength(nums);   // get the maximum digit length of the numbers

        // Initialize the buckets for sorting
        int[][] arrayBuckets1 = new int[10][nums.length];
        int[][] arrayBuckets2 = new int[10][nums.length];

        for (int i = 0; i < 10; i++) {  // fill the buckets with -1 to indicate empty slots
            Arrays.fill(arrayBuckets1[i], -1);
            Arrays.fill(arrayBuckets2[i], -1);
        }

        // First pass, sort the numbers based on their unit digit
        unitDigitSort(nums, arrayBuckets1);
        System.out.println("\nAfter pass 1 (digit place = 1):");
        printArrayBuckets(arrayBuckets1);
        System.out.print("Flattened: ");
        printArray(flattenArrayBuckets(arrayBuckets1, nums.length));

        int exp = 10;   // multiplier for subsequent digit places
        boolean isCurrentArrayBuckets1 = true;

        // Remaining subsequent passes, sort the numbers based on their tens, hundreds and so on
        for (int pass = 2; pass <= passes; pass++) {
            System.out.printf("%nAfter pass %d (digit place = %d):%n", pass, exp);
            if (isCurrentArrayBuckets1) {
                for (int i = 0; i < 10; i++) Arrays.fill(arrayBuckets2[i], -1); // clear the bucket with -1 before used
                subsequentDigitSort(arrayBuckets1, arrayBuckets2, exp);
                printArrayBuckets(arrayBuckets2);
                System.out.print("Flattened: ");
                printArray(flattenArrayBuckets(arrayBuckets2, nums.length));
            } else {
                for (int i = 0; i < 10; i++) Arrays.fill(arrayBuckets1[i], -1); // clear the bucket with -1 before used
                subsequentDigitSort(arrayBuckets2, arrayBuckets1, exp);
                printArrayBuckets(arrayBuckets1);
                System.out.print("Flattened: ");
                printArray(flattenArrayBuckets(arrayBuckets1, nums.length));
            }
            exp *= 10;  // Move to next digit place
            isCurrentArrayBuckets1 = !isCurrentArrayBuckets1;   // toggle the active buckets for the next pass
        }

        // final sorted result
        int[] sortedNums = isCurrentArrayBuckets1 ?
                flattenArrayBuckets(arrayBuckets1, nums.length) :
                flattenArrayBuckets(arrayBuckets2, nums.length);
        System.out.println("\nFinal Sorted Numbers:");
        printArray(sortedNums);

        // Print the total number of primitive operations
        System.out.println("Total primitive operations: " + counter);   
    }
}
