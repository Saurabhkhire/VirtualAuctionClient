package com.auction.virtualauctionclient.api;

import com.auction.virtualauctionclient.model.AuctionParams;
import com.auction.virtualauctionclient.model.Bid;
import com.auction.virtualauctionclient.model.NamesList;
import com.auction.virtualauctionclient.model.Login;
import com.auction.virtualauctionclient.model.PlayerInfoList;
import com.auction.virtualauctionclient.model.PlayerName;
import com.auction.virtualauctionclient.model.PlayerStatus;
import com.auction.virtualauctionclient.model.Register;
import com.auction.virtualauctionclient.model.ResponseMessage;
import com.auction.virtualauctionclient.model.RoomInfo;
import com.auction.virtualauctionclient.model.RoomStatus;
import com.auction.virtualauctionclient.model.RoomStatusResponse;
import com.auction.virtualauctionclient.model.SkipInfo;
import com.auction.virtualauctionclient.model.Team;
import com.auction.virtualauctionclient.model.Username;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {


    @POST("/login/")
        // API's endpoints
    Call<ResponseMessage> login(@Header("Content-Type") String content, @Body Login login);

    @POST("/register/")
        // API's endpoints
    Call<ResponseMessage> register(@Header("Content-Type") String content, @Body Register register);

    @POST("/createroom/")
        // API's endpoints
    Call<RoomInfo> createRoom(@Header("Content-Type") String content, @Body Username username);

    @POST("/joinroom/")
        // API's endpoints
    Call<RoomStatus> joinRoom(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/leaveroom/")
        // API's endpoints
    Call<ResponseMessage> leaveRoom(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/quitroom/")
        // API's endpoints
    Call<ResponseMessage> quitRoom(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/roomstatus/")
        // API's endpoints
    Call<RoomStatusResponse> roomStatus(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/updateauctionparams/")
        // API's endpoints
    Call<ResponseMessage> updateAuctionParams(@Header("Content-Type") String content, @Body AuctionParams auctionParams);

    @POST("/startauction/")
        // API's endpoints
    Call<ResponseMessage> startAuction(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/skipsets/")
        // API's endpoints
    Call<ResponseMessage> skipSets(@Header("Content-Type") String content, @Body SkipInfo skipInfo);

    @POST("/pauseauction/")
        // API's endpoints
    Call<ResponseMessage> pauseAuction(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/playauction/")
        // API's endpoints
    Call<ResponseMessage> playAuction(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

   // @FormUrlEncoded // annotation used in POST type requests
    @POST("/playerstatus/")
        // API's endpoints
    Call<PlayerStatus> info(@Header("Content-Type") String content, @Body Team team);

    @POST("/bid/")
        // API's endpoints
    Call<ResponseMessage> bid(@Header("Content-Type") String content, @Body Bid price);

    @POST("/getusernames/")
        // API's endpoints
    Call<NamesList> getUsernames(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/makehost/")
        // API's endpoints
    Call<ResponseMessage> makeHost(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/getunsoldplayerslist/")
        // API's endpoints
    Call<NamesList> getUnsoldPlayers(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/addunsoldplayers/")
        // API's endpoints
    Call<ResponseMessage> addUnsoldPlayers(@Header("Content-Type") String content, @Body NamesList namesList);

    @POST("/auctionbreakstatus/")
        // API's endpoints
    Call<RoomStatus> auctionBreakStatus(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/getcurrentsetplayerslist/")
        // API's endpoints
    Call<NamesList> getCurrentSetPlayersList(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/gettotalunsoldplayerslist/")
        // API's endpoints
    Call<PlayerInfoList> getTotalUnsoldPlayersList(@Header("Content-Type") String content, @Body RoomInfo roomInfo);

    @POST("/getteamplayerslist/")
        // API's endpoints
    Call<PlayerInfoList> getTeamPlayersList(@Header("Content-Type") String content, @Body Team team);

    @POST("/addplayertoteamafterauction")
        // API's endpoints
    Call<ResponseMessage> addPlayerToTeamAfterAuction(@Header("Content-Type") String content, @Body PlayerName playerName);
}
