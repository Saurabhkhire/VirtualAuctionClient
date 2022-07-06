package com.auction.virtualauctionclient.auctionscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.auction.virtualauctionclient.R;

import java.util.ArrayList;

public class TeamPlayersListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mPlayerNamesList;
    private ArrayList<String> mPlayerCountryList;
    private ArrayList<String> mPlayerRoleList;
    private ArrayList<String> mBattingStyleList;
    private ArrayList<String> mBowlingStyleList;
    private ArrayList<String> mBattingPositionList;
    private ArrayList<String> mTotalPriceInLakhsList;
    private ArrayList<String> mTotalPriceInCroresList;
    private ListView mListView;

    public TeamPlayersListAdapter(Context context, ArrayList playerNamesList, ArrayList playerCountryList, ArrayList playerRoleList, ArrayList battingStyleList, ArrayList bowlingStyleList, ArrayList battingPositionList, ArrayList totalPriceInLakhsList, ArrayList totalPriceInCroresList, ListView listView) {
        super();
        mContext = context;
        mPlayerNamesList = playerNamesList;
        mPlayerCountryList = playerCountryList;
        mPlayerRoleList = playerRoleList;
        mBattingStyleList = battingStyleList;
        mBowlingStyleList = bowlingStyleList;
        mBattingPositionList = battingPositionList;
        mTotalPriceInLakhsList = totalPriceInLakhsList;
        mTotalPriceInCroresList = totalPriceInCroresList;
        mListView = listView;
    }

    public int getCount() {
        // return the number of records
        return mPlayerNamesList.size();
    }


    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.team_players_listview, parent, true);
        //ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200, 100);
        view.setLayoutParams(params);


        // get the reference of textView and button
        TextView PlayerNameListEdit = (TextView) view.findViewById(R.id.PlayerNameEdit);
        TextView PlayerCountryListEdit = (TextView) view.findViewById(R.id.PlayerCountryEdit);
        TextView PlayerRoleListEdit = (TextView) view.findViewById(R.id.PlayerRoleEdit);
        TextView BattingStyleListEdit = (TextView) view.findViewById(R.id.BatingStyleEdit);
        TextView BowlingStyleListEdit = (TextView) view.findViewById(R.id.BowlingStyleEdit);
        TextView BatingPositionListEdit = (TextView) view.findViewById(R.id.BattingPositionEdit);
        TextView TotalPriceInLakhsListEdit = (TextView) view.findViewById(R.id.PriceinLakhsEdit);
        TextView TotalPriceInCroresEdit = (TextView) view.findViewById(R.id.PriceinCroresEdit);

        // Set the title and button name
        PlayerNameListEdit.setText(mPlayerNamesList.get(position));
        PlayerCountryListEdit.setText(mPlayerCountryList.get(position));
        PlayerRoleListEdit.setText(mPlayerRoleList.get(position));
        BattingStyleListEdit.setText(mBattingStyleList.get(position));
        BowlingStyleListEdit.setText(mBowlingStyleList.get(position));
        BatingPositionListEdit.setText(mBattingPositionList.get(position));
        TotalPriceInLakhsListEdit.setText(mTotalPriceInLakhsList.get(position));
        TotalPriceInCroresEdit.setText(mTotalPriceInCroresList.get(position));

        return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }}


