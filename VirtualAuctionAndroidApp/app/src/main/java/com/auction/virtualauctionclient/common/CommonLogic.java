package com.auction.virtualauctionclient.common;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.core.graphics.ColorUtils;

import com.auction.virtualauctionclient.R;
import com.auction.virtualauctionclient.api.Client;
import com.auction.virtualauctionclient.auctionscreen.AuctionScreen;
import com.auction.virtualauctionclient.auctionscreen.TeamPlayersListScreen;
import com.auction.virtualauctionclient.model.PlayerName;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommonLogic {

    private static ArrayList<String> modelArrayList;
    public static boolean editTextCheck(EditText editText, String inputName, String nullType, String type, int minLimit, int maxLimit) {

        boolean rightInput = true;
        String inputText = editText.getText().toString();

        if (inputText.equals("") && nullType.equals(Constants.NOT_NULL)) {

            editText.setError(Constants.BLANK_ENTRY_MESSAGE);
            rightInput = false;

        } else if (inputText.length()<minLimit) {

            editText.setError(Constants.MIN_LIMIT_NOT_MATCH_MESSAGE.replaceAll("\\{Limit\\}", String.valueOf(minLimit)));
            rightInput = false;

        } else if (inputText.length()>maxLimit) {

            editText.setError(Constants.MAX_LIMIT_NOT_MATCH_MESSAGE.replaceAll("\\{Limit\\}", String.valueOf(maxLimit)));
            rightInput = false;

        } else {

            if (type.equals(Constants.NUMERIC_ENTRY)) {

                if (!inputText.matches("[0-9]+")) {
                    editText.setError(Constants.NON_NUMERIC_ENTRY_MESSAGE);
                    rightInput = false;
                }
            } else if (type.equals(Constants.ALPHA_NUMERIC_ENTRY)){
                if (!inputText.matches("[a-zA-Z0-9]+")) {
                    editText.setError(Constants.NON_ALPHA_NUMERIC_ENTRY_MESSAGE);
                    rightInput = false;
                }


            } else if (type.equals(Constants.EMAIL_ENTRY)){

//                //boolean v = inputText.indexOf("@", inputText.indexOf("@") + 1) > -1;
//                if (!inputText.contains("@")) {
//                    editText.setError(inputName + Constants.HAVING_AT_THE_RATE_IN_EMAIL_ENTRY_MESSAGE);
//                    rightInput = false;
//                } else {
//                    String[] splitMail = inputText.split("@");
//                    if (!splitMail[0].matches("[a-zA-Z0-9]+")) {
//                        editText.setError(inputName + Constants.NON_ALPHA_NUMERIC_ENTRY_MESSAGE + " before @");
//                        rightInput = false;
//                    } else {
//                        String[] mailData = {"gmail.com"};
//                        List<String> list = Arrays.asList(mailData);
//                        String mailEnd = "";
//                        try {
//                            mailEnd = splitMail[1];
//                        } catch (Exception ex) {
//
//                        }
//
//                        if (!list.contains(mailEnd)) {
//                            editText.setError(inputName + Constants.INVALID_MAIL_ENDING_ENTRY_MESSAGE);
//                            rightInput = false;
//                        }
//                    }
//
//
//
//                }

            }

        }

        if(rightInput) {
            editText.setError(null);
        }

        return rightInput;
    }

    public static void colorForAuctionScreenBackround(String team, ContentFrameLayout contentFrameLayout) {
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

        contentFrameLayout.setBackgroundColor(colorValue);
    }

    public static void setTeamImage(String team, ImageView imageView, Context context) {
        try {

//            InputStream ipStream = assetManager.open("logos/" + team + "_logo.png");
//            Drawable draw = Drawable.createFromStream(ipStream, null);
//            // set image to ImageView
//            imageView.setImageDrawable(draw);
//            ipStream.close();
            // use the input stream as you want

            String teamImageUri = "";

            if(team.equals(Constants.TEAM_CSK)) {
                teamImageUri = "https://i.imgur.com/LnX3Pxq.png";
            } else if(team.equals(Constants.TEAM_RCB)) {
                teamImageUri = "https://i.imgur.com/egPKbls.png";
            } else if(team.equals(Constants.TEAM_SRH)) {
                teamImageUri = "https://i.imgur.com/4UgTUmd.png";
            } else if(team.equals(Constants.TEAM_DC)) {
                teamImageUri = "https://i.imgur.com/znVYcD0.jpg";
            } else if(team.equals(Constants.TEAM_MI)) {
                teamImageUri = "https://i.imgur.com/7YJUhPt.png";
            } else if(team.equals(Constants.TEAM_RR)) {
                teamImageUri = "https://i.imgur.com/12nkXiz.png";
            } else if(team.equals(Constants.TEAM_KKR)) {
                teamImageUri = "https://i.imgur.com/SE421NZ.png";
            } else if(team.equals(Constants.TEAM_PBKS)) {
                teamImageUri = "https://i.imgur.com/gZ1mXhz.png";
            } else if(team.equals(Constants.TEAM_LSG)) {
                teamImageUri = "https://i.imgur.com/5x9lj8y.png";
            } else if(team.equals(Constants.TEAM_GT)) {
                teamImageUri = "https://i.imgur.com/gZh62A6.png";
            }

            Picasso.with(context).load(teamImageUri).into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerImage(String playerImageUri, ImageView imageView, Context context) {
        try {

//            player = player.replaceAll("\\s+","_");
//            InputStream ipStream1 = assetManager.open("players1/" + player + ".png");
//            Drawable draw1 = Drawable.createFromStream(ipStream1, null);
//            // set image to ImageView
//            imageView1.setImageDrawable(draw1);
//            ipStream1.close();
//
//            InputStream ipStream2 = assetManager.open("players2/" + player + ".png");
//            Drawable draw2 = Drawable.createFromStream(ipStream2, null);
//            // set image to ImageView
//            imageView2.setImageDrawable(draw2);
//            ipStream2.close();

            Picasso.with(context).load(playerImageUri).into(imageView);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setOtherImage(String other, ImageView imageView, Context context) {

            try {

                Picasso.with(context).load("https://i.imgur.com/A8OQYWr.jpg").into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static void setBackgroundForButtons(String name, Button button) {

        try {

            String colorList = colorForLists(name, "button");
            String[] colorListSplit = colorList.split(",");

            if(name.equals(Constants.BUTTON_BACKGROUND2)) {

            GradientDrawable gd = new GradientDrawable(
                    //Drawable d = Drawable.createFromStream(assetManager.open("background/" + name), null);
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{Color.parseColor(colorListSplit[0]),Color.parseColor(colorListSplit[1])});
            gd.setCornerRadius(10f);
            button.setBackground(gd);

        } else if (name.equals(Constants.BUTTON_BACKGROUND1)) {

                GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[] {Color.parseColor(colorListSplit[0]),Color.parseColor(colorListSplit[1])});
                gd.setCornerRadius(10f);
                button.setBackground(gd);

            } else {

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[] {Color.parseColor(colorListSplit[0]),Color.parseColor(colorListSplit[1])});
                gd.setCornerRadius(10f);
                button.setBackground(gd);
            }

            //button.setBackgroundTintList(ColorStateList.valueOf(resultColor));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Bitmap bitmap = BitmapFactory.decodeFile("//assets/background/pink.png");
        //BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        //this.findViewById(android.R.id.content).setBackground(bitmapDrawable);
    }

    public static void setBackgroundImage(String name, ContentFrameLayout contentFrameLayout, AssetManager assetManager) {


        try {
            Drawable d = Drawable.createFromStream(assetManager.open("background/" + name), null);
            contentFrameLayout.setBackgroundDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Bitmap bitmap = BitmapFactory.decodeFile("//assets/background/pink.png");
        //BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        //this.findViewById(android.R.id.content).setBackground(bitmapDrawable);
    }

    public static String colorForLists(String team, String resource) {

        String colorList = "";  // main, table, border

        if(team.equals(Constants.TEAM_CSK)) {
            colorList = "#FFFF00,#000000";
        } else if(team.equals(Constants.TEAM_RCB)) {
            if(resource.equals("list")) {
                colorList = "#FF0000,#F2F2F2";
            } else {
                colorList = "#FFCCCB,#F2F2F2";
            }
        } else if(team.equals(Constants.TEAM_SRH)) {
            if(resource.equals("list")) {
            colorList = "#FFA500,#F2F2F2";
            } else {
                colorList = "#FFCCCB,#F2F2F2";
            }
        } else if(team.equals(Constants.TEAM_DC)) {
                if(resource.equals("list")) {
            colorList = "#2561AE,#F2F2F2";
                } else {
                    colorList = "#FFCCCB,#F2F2F2";
                }
        } else if(team.equals(Constants.TEAM_MI)) {
                    if(resource.equals("list")) {
            colorList = "#005FA2,#F2F2F2";
                    } else {
                        colorList = "#FFCCCB,#F2F2F2";
                    }
        } else if(team.equals(Constants.TEAM_RR)) {
                        if(resource.equals("list")) {
            colorList = "#E75480,#F2F2F2";
                        } else {
                            colorList = "#FFCCCB,#F2F2F2";
                        }
        } else if(team.equals(Constants.TEAM_KKR)) {
                        if(resource.equals("list")) {
            colorList = "#3A225D,#F2F2F2";
                        } else {
                            colorList = "#FFCCCB,#F2F2F2";
                        }
        } else if(team.equals(Constants.TEAM_PBKS)) {
                        if(resource.equals("list")) {
            colorList = "#ED1B24,#F2F2F2";
                        } else {
                            colorList = "#FFCCCB,#F2F2F2";
                        }
        } else if(team.equals(Constants.TEAM_LSG)) {
                        if(resource.equals("list")) {
            colorList = "#ADD8E6,#000000";
                        } else {
                            colorList = "#FFCCCB,#F2F2F2";
                        }
        } else if(team.equals(Constants.TEAM_GT)) {
                        if(resource.equals("list")) {
            colorList = "#1B03A3,#F2F2F2";
                        } else {
                            colorList = "#FFCCCB,#F2F2F2";
                        }
        } else if (team.equals("room")) {
            colorList = "#000000,#F2F2F2";
        } else if (team.equals("normal")) {
            colorList = "#000000,#F2F2F2";
        } else if (team.equals("gamebutton")) {
            colorList = "#8B0000,#00008B";
        } else if (team.equals("accountbutton")) {
            colorList = "#F2F2F2,#FFFF00";
        }

        return colorList;
    }

    public static void setTable(ContentFrameLayout contentFrameLayout, View topline, TextView PlayerNameTxt,TextView PlayerCountryTxt,TextView PlayerRoleTxt,TextView BattingStyleTxt,TextView BowlingStyleTxt,TextView BattingPositionTxt,TextView PriceInLakhsTxt,TextView PriceInCroresTxt, ArrayList<String> playerNamesList, ArrayList<String> playerCountryList, ArrayList<String> playerRoleList, ArrayList<String> battingStyleList, ArrayList<String> bowlingStyleList, ArrayList<String> battingPositionList, ArrayList<String> totalPriceInLakhsList, ArrayList<String> totalPriceInCroresList, TableLayout prices, Context context, String team) {

        String colorList = colorForLists(team,"list");
        String[] colorListSplit = colorList.split(",");

        contentFrameLayout.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        topline.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        PlayerNameTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        PlayerNameTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        PlayerCountryTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        PlayerCountryTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        PlayerRoleTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        PlayerRoleTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        BattingStyleTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        BattingStyleTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        BowlingStyleTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        BowlingStyleTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        BattingPositionTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        BattingPositionTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        PriceInLakhsTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        PriceInLakhsTxt.setTextColor(Color.parseColor(colorListSplit[1]));
        PriceInCroresTxt.setBackgroundColor(Color.parseColor(colorListSplit[0]));
        PriceInCroresTxt.setTextColor(Color.parseColor(colorListSplit[1]));

        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.parseColor(colorListSplit[0])); //white background
        border.setStroke(1, Color.parseColor(colorListSplit[0])); //black border with full opacity
        prices.setBackground(border);
        for(int i = 0; i < playerNamesList.size(); i++) {
            TableRow tr =  new TableRow(context);
            //tr.setBackgroundColor(Color.BLACK);
            TextView c1 = new TextView(context);
            c1.setText(playerNamesList.get(i));
            c1.setGravity(Gravity.CENTER);
            c1.setBackground(border);
            c1.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c2 = new TextView(context);
            c2.setText(playerCountryList.get(i));
            c2.setBackground(border);
            c2.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c3 = new TextView(context);
            c3.setText(playerRoleList.get(i));
            c3.setBackground(border);
            c3.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c4 = new TextView(context);
            c4.setText(battingStyleList.get(i));
            c4.setBackground(border);
            c4.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c5 = new TextView(context);
            c5.setText(bowlingStyleList.get(i));
            c5.setBackground(border);
            c5.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c6 = new TextView(context);
            c6.setText(battingPositionList.get(i));
            c6.setBackground(border);
            c6.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c7 = new TextView(context);
            c7.setText(totalPriceInLakhsList.get(i));
            c7.setBackground(border);
            c7.setTextColor(Color.parseColor(colorListSplit[1]));
            TextView c8 = new TextView(context);
            c8.setText(totalPriceInCroresList.get(i));
            c8.setBackground(border);
            c8.setTextColor(Color.parseColor(colorListSplit[1]));
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tr.addView(c4);
            tr.addView(c5);
            tr.addView(c6);
            tr.addView(c7);
            tr.addView(c8);

            prices.addView(tr);
        }
    }

    public static void addPlayersTable(ArrayList<String> playerNamesList, ArrayList<String> priceList, TableLayout prices, Context context, String username, String roomId, String teamName) {

        modelArrayList = new ArrayList<>();
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(1, 0xFF000000); //black border with full opacity
        prices.setBackground(border);
        for(int i = 0; i < playerNamesList.size(); i++){
            TableRow tr =  new TableRow(context);
            //tr.setMinimumHeight(30);
            //tr.setBackgroundColor(Color.BLACK);
            TextView c1 = new TextView(context);
            c1.setText(playerNamesList.get(i));
            c1.setGravity(Gravity.CENTER);
            c1.setBackground(border);
            c1.setHeight(50);
            TextView c2 = new TextView(context);
            c2.setText(priceList.get(i));
            c2.setGravity(Gravity.CENTER);
            c2.setBackground(border);
            c2.setHeight(50);

                CheckBox c3 = new CheckBox(context);
                c3.setChecked(false);
                c3.setGravity(Gravity.CENTER);
                c3.setBackground(border);
                c3.setHeight(50);
                tr.addView(c1);
                tr.addView(c2);
                tr.addView(c3);
                prices.addView(tr);

                c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            modelArrayList.add(c1.getText().toString());
                        } else {
                            modelArrayList.remove(c1.getText().toString());
                        }
                    }
                });




        }
    }

    public static ArrayList<String> list() {
         return modelArrayList;
    }

}


/*         Picasso.with(this).load("http://imageUrl").into(new Target() {
@Override
public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mYourLayout.setBackground(new BitmapDrawable(bitmap));
        }

@Override
public void onBitmapFailed(Drawable errorDrawable) {

        }

@Override
public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
        });*/
