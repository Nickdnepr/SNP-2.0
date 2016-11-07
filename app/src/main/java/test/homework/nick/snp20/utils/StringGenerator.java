package test.homework.nick.snp20.utils;

/**
 * Created by Nick on 03.09.16.
 */
public class StringGenerator {

    public static String generateDurationString(int mills) {
        StringBuilder answer = new StringBuilder("");
        int seconds = mills/1000;
        answer.append(seconds/60);
        answer.append(":");
        if (seconds%60<10){
            answer.append("0");
            answer.append(seconds%60);
        }else {
            answer.append(seconds%60);
        }
//        Log.i("STRING_GENERATOR", "DURATION STRING IS" + answer.toString());
        return answer.toString();
    }
}
