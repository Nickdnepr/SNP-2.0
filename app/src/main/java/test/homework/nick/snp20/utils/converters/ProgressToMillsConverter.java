package test.homework.nick.snp20.utils.converters;

/**
 * Created by Nick on 13.11.16.
 */
public class ProgressToMillsConverter {

    public static int progressToMills(int duration, int progress) {
        int percent = duration / 100;
        return percent * progress;
    }

    public static int millsToProgress(int duration, int mills) {
        int percent = duration / 100;


        return mills/percent;
    }
}
