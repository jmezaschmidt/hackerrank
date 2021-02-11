import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chocolates {

    public static void main(String[] args) {
        System.out.println(calculateChocolatesCombinations(7));
    }

    private static int calculateChocolatesCombinations(int numberOfChocolates) {

        int result = 1;
        int groupsFactor = 3;

        while (numberOfChocolates >= groupsFactor) {
            if (numberOfChocolates == groupsFactor) {
                result++;
            } else {
                int groups = groupsFactor / 3;
                int singles = (numberOfChocolates - groupsFactor);
                result += permutationsWithoutDuplicates(createBox(groups, singles));
            }

            groupsFactor += 3;
        }

        return result;
    }

    private static List<String> createBox(int groups, int singles) {
        Stream<String> groupsList = IntStream.range(0, groups)
                .mapToObj(value -> String.valueOf(3));

        Stream<String> singlesList = IntStream.range(0, singles)
                .mapToObj(value -> String.valueOf(1));

        return Stream.concat(groupsList, singlesList).collect(Collectors.toList());
    }

    private static long permutationsWithoutDuplicates(List<String> array) {

        List<List<String>> result = new ArrayList<>();
        generate(array.size(), array, result);

        return result.parallelStream()
                .map(stringList -> String.join("", stringList))
                .distinct()
                .peek(System.out::println)
                .count();
    }

    //Heap's Algorithm
    private static void generate(int n,
            List<String> array,
            List<List<String>> result) {

        if (n == 1) {
            result.add(new ArrayList<>(array));
            return;
        }

        generate(n - 1, array, result);

        for (int i = 0; i < n - 1; i++) {
            if (n % 2 == 0) {
                swap(array, i, n - 1);
            } else {
                swap(array, 0, n - 1);
            }

            generate(n - 1, array, result);
        }
    }

    private static void swap(List<String> array, int positionA, int positionB) {
        String temp = array.get(positionA);
        String toSwap = array.get(positionB);
        array.set(positionA, toSwap);
        array.set(positionB, temp);
    }
}
