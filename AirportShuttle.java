import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AirportShuttle {

    public static void main(String[] args) {
        List<List<Integer>> stops = new ArrayList<>();
        ArrayList<Integer> row0 = new ArrayList<>();
        row0.add(1);
        row0.add(1);
        row0.add(0);
        row0.add(0);
        stops.add(row0);
        ArrayList<Integer> row1 = new ArrayList<>();
        row1.add(1);
        row1.add(1);
        row1.add(0);
        row1.add(-1);
        stops.add(row1);
        ArrayList<Integer> row2 = new ArrayList<>();
        row2.add(0);
        row2.add(0);
        row2.add(1);
        row2.add(0);
        stops.add(row2);
        ArrayList<Integer> row3 = new ArrayList<>();
        row3.add(0);
        row3.add(0);
        row3.add(1);
        row3.add(1);
        stops.add(row3);

        System.out.println(findMaxPassengers(stops));
    }

    private static int findMaxPassengers(List<List<Integer>> stops) {

        List<List<Stop>> results = new ArrayList<>();
        List<Stop> path = new ArrayList<>();

        makeTrips(stops, new Stop(stops.get(0).get(0), 0, 0), path, results);

        System.out.println("RESULTS");
        return results.stream()
                .peek(System.out::println)
                .map(list -> list.parallelStream()
                        .peek(System.out::println)
                        .map(Stop::getValue)
                        .reduce(Integer::sum)
                        .orElse(0))
                .reduce(Integer::max)
                .orElse(0);

    }

    private static void makeTrips(List<List<Integer>> stops, Stop stop, List<Stop> path, List<List<Stop>> results) {

        int finalPosition = stops.size() - 1;
        if (stop.getY() == finalPosition && stop.getX() == finalPosition) {
            ArrayList<Stop> currentPath = new ArrayList<>(path);
            currentPath.add(stop);
            results.add(currentPath);
            return;
        }

        stop.add(getChildren(stops, stop.getY(), stop.getX()));
        path.add(stop);

        stop.getChildren()
                .forEach(child -> makeTrips(stops, child, path, results));

        path.remove(stop);

    }

    private static List<Stop> getChildren(List<List<Integer>> stops, int positionY, int positionX) {
        List<Stop> children = new ArrayList<>();

        if(positionY + 1 < stops.size() && stops.get(positionY + 1).get(positionX) != -1)  {
            children.add(new Stop(stops.get(positionY + 1).get(positionX), positionY + 1, positionX));
        }
        if(positionX + 1 < stops.size() && stops.get(positionY).get(positionX + 1) != -1)  {
            children.add(new Stop(stops.get(positionY).get(positionX + 1), positionY, positionX + 1));
        }

        return children;

    }


    private static class Stop {

        private int value;
        private int y;
        private int x;
        private final List<Stop> children = new ArrayList<>();

        public Stop(int value, int y, int x) {
            this.value = value;
            this.y = y;
            this.x = x;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public List<Stop> getChildren() {
            return children;
        }

        public void add(List<Stop> children) {
            this.children.addAll(children);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Stop stop = (Stop) o;
            return value == stop.value &&
                    y == stop.y &&
                    x == stop.x &&
                    Objects.equals(children, stop.children);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, y, x, children);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "value=" + value +
                    ", y=" + y +
                    ", x=" + x +
                    '}';
        }
    }
}
