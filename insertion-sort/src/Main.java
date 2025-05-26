import java.util.Arrays;

public class Main {
    public static void main(String[] args) {


        int temp;
        int[] array = {2,6,7,22,76,11};
        for (int i = 1; i < array.length; i++) { // due to nature of insertion sort, we start at idx 1, ~~
            // we'll end up comparing i-1 in the subloop where main swap logic is
            // increment via i++ slow, but insert sort via compare via subloop for lesser val up to index of i
            for (int j = i;j > 0; j--){ // we sort decrementing in reverse
                if (array[j] < array[j - 1]){ // start of logic, if curr index j less than prev number, we swap
                    temp = array[j]; // this is the temp var from curr index of j
                    array[j] = array[j-1]; // swap greater number into curr index of j
                    array[j - 1] = temp; // then swap j into j-1 because it's less
                }
            }
        }
        String strArr = Arrays.toString(array);
        System.out.println(strArr);
    }
}