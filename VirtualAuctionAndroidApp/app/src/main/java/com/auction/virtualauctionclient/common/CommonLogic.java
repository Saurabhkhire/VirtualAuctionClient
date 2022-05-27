package com.auction.virtualauctionclient.common;


import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.ImageView;

import com.auction.virtualauctionclient.model.RoomInfo;

import java.io.IOException;
import java.io.InputStream;


public class CommonLogic {

    public static boolean editTextCheck(EditText editText, String inputName, String type, int minLimit, int maxLimit) {

        boolean rightInput = true;
        String inputText = editText.getText().toString();

        if (inputText.equals("")) {

            editText.setError(inputName + Constants.BLANK_ENTRY_MESSAGE);
            rightInput = false;

        } else if (inputText.length()<minLimit) {

            editText.setError(inputName + Constants.MIN_LIMIT_NOT_MATCH_MESSAGE.replaceAll("\\{Limit\\}", String.valueOf(minLimit)));
            rightInput = false;

        } else if (inputText.length()>maxLimit) {

            editText.setError(inputName + Constants.MAX_LIMIT_NOT_MATCH_MESSAGE.replaceAll("\\{Limit\\}", String.valueOf(maxLimit)));
            rightInput = false;

        } else {

            if (type.equals(Constants.NUMERIC_ENTRY)) {

                if (!inputText.matches("[0-9]+")) {
                    editText.setError(inputName + Constants.NON_NUMERIC_ENTRY_MESSAGE);
                    rightInput = false;
                }
            }

        }

        if(rightInput) {
            editText.setError(null);
        }

        return rightInput;
    }

    public static int colorForAuctionScreenBackround(String team) {
         int colorValue = 0;

         if(team.equals(Constants.TEAM_CSK)) {
             colorValue = Color.parseColor("#FFFF00");
         } else if(team.equals(Constants.TEAM_RCB)) {
             colorValue = Color.parseColor("#FF0000");
         } else if(team.equals(Constants.TEAM_SRH)) {
             colorValue = Color.parseColor("#FFA500");
         } else if(team.equals(Constants.TEAM_DC)) {
             colorValue = Color.parseColor("#2561AE");
         } else if(team.equals(Constants.TEAM_MI)) {
             colorValue = Color.parseColor("#005FA2");
         } else if(team.equals(Constants.TEAM_RR)) {
             colorValue = Color.parseColor("#FFC0CB");
         } else if(team.equals(Constants.TEAM_KKR)) {
             colorValue = Color.parseColor("#3A225D");
         } else if(team.equals(Constants.TEAM_PBKS)) {
             colorValue = Color.parseColor("#ED1B24");
         } else if(team.equals(Constants.TEAM_LSG)) {
             colorValue = Color.parseColor("#ADD8E6");
         } else if(team.equals(Constants.TEAM_GT)) {
             colorValue = Color.parseColor("#1B03A3");
         }

         return colorValue;
    }

    public static void setTeamImage(String team, ImageView imageView, AssetManager assetManager) {
        try {

            InputStream ipStream = assetManager.open("logos/" + team + "_logo.png");
            Drawable draw = Drawable.createFromStream(ipStream, null);
            // set image to ImageView
            imageView.setImageDrawable(draw);
            ipStream.close();
            // use the input stream as you want
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerImage(String player, ImageView imageView1, ImageView imageView2, AssetManager assetManager) {
        try {

            player = player.replaceAll("\\s+","_");
            InputStream ipStream1 = assetManager.open("players1/" + player + ".png");
            Drawable draw1 = Drawable.createFromStream(ipStream1, null);
            // set image to ImageView
            imageView1.setImageDrawable(draw1);
            ipStream1.close();

            InputStream ipStream2 = assetManager.open("players2/" + player + ".png");
            Drawable draw2 = Drawable.createFromStream(ipStream2, null);
            // set image to ImageView
            imageView2.setImageDrawable(draw2);
            ipStream2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
