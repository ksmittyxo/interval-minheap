import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class Interval {
    private LocalDateTime start;
    private LocalDateTime end;
    public static Interval genericInterval  = new Interval(
            LocalDateTime.of(0, Month.JANUARY, 1, 0, 0),
            LocalDateTime.of(0, Month.JANUARY, 1, 0, 0));

    public LocalDateTime getStart() {
        return start;
    }
    public LocalDateTime getEnd() {
        return end;
    }
    public Duration getDifference() {
        return Duration.between(start, end);
    }
    public Interval(LocalDateTime startIn, LocalDateTime endIn) {
        start = startIn;
        end = endIn;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start= " + start.getMonth() + " " + start.getDayOfMonth() + " " + start.getHour() + ":" + start.getHour() + start.getMinute() +
                ", end=" + end.getMonth() + " " + end.getDayOfMonth() + " " + end.getHour() + ":" + end.getHour() + end.getMinute() +
                '}';
    }

    public boolean isEqualTo(Interval interval) {
        return start.getYear() == interval.getStart().getYear() &&
                start.getMonth().equals(interval.getStart().getMonth()) &&
                start.getDayOfMonth() == interval.getStart().getDayOfMonth() &&
                start.getHour() == interval.getStart().getHour() &&
                start.getMinute() == interval.getStart().getMinute() &&
                end.getYear() == interval.getEnd().getYear() &&
                end.getMonth().equals(interval.getEnd().getMonth()) &&
                end.getDayOfMonth() == interval.getEnd().getDayOfMonth() &&
                end.getHour() == interval.getEnd().getHour() &&
                end.getMinute() == interval.getEnd().getMinute();
    }


}