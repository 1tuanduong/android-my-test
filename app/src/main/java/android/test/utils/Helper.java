package android.test.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Helper {

    public static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static ArrayList<String> spliteString (String content){

        String nullStr = " ";
        ArrayList<String> list = new ArrayList<>();

        String[] element = content.split(" ");

        for (int i = 0; i < element.length; i++) {

            StringBuffer buffer = new StringBuffer();

            if(i == 0 ){

                buffer.append(element[0]);
                list.add(buffer.toString());

            }
            else {

                buffer.append(element[i]);
                list.add(buffer.toString());
            }
        }

        return list;
    }
    public static String editString(ArrayList<String> list){
        int midNumber = 0;

        StringBuffer fistStr = new StringBuffer();
        StringBuffer lastStr = new StringBuffer();
        StringBuffer resultStr = new StringBuffer();
        if (list.size() != 0 && list != null){
            midNumber = list.size()/ 2;
            Log.d("checkLine72",midNumber +"");
            if (list.size() > 2){
                //Xet chan le

                if(list.size() % 2 == 1){ //le

                    for (int i = 0; i < list.size(); i++){
                        if (i < midNumber){
                            fistStr.append(list.get(i));
                            fistStr.append(" ");
                        }
                        if (i > midNumber) {
                            lastStr.append(list.get(i));
                            lastStr.append(" ");
                        }
                    }
                    if (fistStr.toString().length() <= lastStr.toString().length()){
                        resultStr = fistStr.append(list.get(midNumber)).append("\n").append(lastStr.toString());
                    }
                    else{
                        resultStr = fistStr.append("\n").append(list.get(midNumber)).append(" ").append(lastStr.toString());
                    }
                }
                else{ // chan
                    resultStr = fistStr.append(list.get(midNumber)).append("\n").append(lastStr.toString());
                }


            }
            else{
                if (list.size() == 1){
                    resultStr.append(list.get(0));
                }
                if (list.size() == 2){
                    fistStr.append(list.get(0));
                    lastStr.append(list.get(1));
                    resultStr = fistStr.append(" ").append("\n").append(lastStr.toString());
                }
            }

        }
    return resultStr.toString();

    }

}
