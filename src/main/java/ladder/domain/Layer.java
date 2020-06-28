package ladder.domain;

import ladder.domain.moveStrategy.RandomGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Layer {

    public static final String MINIMUN_POINTS_EXCEPTION = "포인트가 2개보다 적으면 게임이 성립되지 않습니다.";

    private static final int MINIMUN_POINTS = 2;
    private static final int ONE = 1;
    private static final int INDEX_ONE = 1;

    private List<Point> points;

    private Layer(List<Point> points) {
        validateLayer(points);
        this.points = points;
    }

    private void validateLayer(List<Point> points) {
        if (points.size() < MINIMUN_POINTS){
            throw new IllegalArgumentException(MINIMUN_POINTS_EXCEPTION);
        }
    }

    public static Layer of(int countOfPerson) {
        return createLine(countOfPerson);
    }

    private static Layer createLine(int countOfperson) {
        List<Point> points = new ArrayList<>();
        Point point = Point.first(new RandomGenerator());
        points.add(point);

        point = addMidPoints(countOfperson, point, points);

        Point lastPoint = point.last();
        points.add(lastPoint);

        return new Layer(points);
    }

    private static Point addMidPoints(int countOfperson, Point point, List<Point> points) {
        int lastPointIndex = countOfperson - ONE;
        for (int i = INDEX_ONE; i < lastPointIndex; i++){
            point = point.mid();
            points.add(point);
        }
        return point;
    }

    public static Layer of(List<Point> points) {
        return new Layer(points);
    }

    public int move(int position){
        return points.get(position).move();
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    @Override
    public String toString() {
        return points.stream()
                     .map(Point::toString)
                     .collect(Collectors.joining());
    }
}