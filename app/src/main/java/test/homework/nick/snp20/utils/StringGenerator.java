package test.homework.nick.snp20.utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Nick on 03.09.16.
 */
public class StringGenerator {

    public static String generateDurationString(int mills) {
        StringBuilder answer = new StringBuilder("");
        int seconds = mills / 1000;
        answer.append(seconds / 60);
        answer.append(":");
        if (seconds % 60 < 10) {
            answer.append("0");
            answer.append(seconds % 60);
        } else {
            answer.append(seconds % 60);
        }
//        Log.i("STRING_GENERATOR", "DURATION STRING IS" + answer.toString());
        return answer.toString();
    }

    //http://api.soundcloud.com/tracks.json?q=h&limit=100&client_id=8a81c591a1701b27d7e76e7b4e780050
    public static String generateRequestHttpString(String request) {
        StringBuilder answer = new StringBuilder();
        answer.append(Constants.SOUNDCLOUD_URL);
        answer.append("?q=");
        answer.append(request);
        answer.append("&limit=100");
        answer.append("&client_id=");
        answer.append(Constants.USER_ID);
        return answer.toString();
    }

    public static ArrayList<String> generateListOfPlaylists(String lists){
        String[] arr = lists.split("===");
        return new ArrayList<>(Arrays.asList(arr));
    }
}
