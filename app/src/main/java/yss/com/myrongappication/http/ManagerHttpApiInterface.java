package yss.com.myrongappication.http;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import yss.com.myrongappication.friendcircle.friendcircledemo.bean.CircleBean;
import yss.com.myrongappication.resp.FriendsList;
import yss.com.myrongappication.resp.LoginInfo;
import yss.com.myrongappication.resp.UserInfoVo;


/**
 * 用于的访问接口
 * Created by hli社区有我管理man on 2015/10/5.
 */
public interface ManagerHttpApiInterface {
    //登录
    @FormUrlEncoded
    @POST("/mybatiseproject/user/login")
    Call<LoginInfo> login(@Field("userName") String userName, @Field("password") String password);

    //获取好友列表
    @GET("/mybatiseproject/user/friends")
    Call<List<FriendsList>> friendsList(@Query("userId") String userId);

    //注册
    @FormUrlEncoded
    @POST("/mybatiseproject/user/regist")
    Call<Map> register(@Field("userName") String userName, @Field("phone") String phone, @Field("password") String password);

    //获取好友列表
    @GET("/mybatiseproject/user/applyFriends")
    Call<List<FriendsList>> applyFriendsList(@Query("userId") String userId);

    //添加好友
    @POST("/mybatiseproject/user/addFriend")
    Call<Map> addFriend(@Query("userId") String userId, @Query("friendId") String friendId);

    //同意好友申请
    @PUT("/mybatiseproject/user/agreeApply")
    Call<Map> agreeFriendsApply(@Query("userId") String userId, @Query("friendId") String friendId);

    //根据条件查找用户
    @GET("/mybatiseproject/user/searchFriends")
    Call<List<UserInfoVo>> searchFriends(@Query("userId") String userId, @Query("searchKey") String searchKey);

    //更新资料
    @Multipart
    @POST("/mybatiseproject/user/updateUserInfo")
    Call<Map> updateUserInfo(@Part MultipartBody.Part portrait, @Query("id") String id, @Query("nickName") String nickName, @Query("sex") String sex);

    //获取用户资料
    @GET("/mybatiseproject/user/getUserInfo")
    Call<UserInfoVo> getUserInfo(@Query("id") String id);

    //发布圈子
    @Multipart
    @POST("/mybatiseproject/circle/publishCircle")
    Call<Map> publishCircle(@Query("userId") String userId, @Query("dynamicContent") String dynamicContent,
                            @Part List<MultipartBody.Part> imgList);
    //获取圈子列表
    @GET("/mybatiseproject/circle/getCircleList")
    Call<List<CircleBean>> getCircleList(@Query("userId") String userId);
    /**
     * 物业首页数据查询
     *
     * @param accessToken
     */
    @GET("/serving/property")
    Call<CircleBean> propertyQuery(@Query("access_token") String accessToken);

    /**
     * 获取维修列表的接口
     *
     * @param status 状态 0 新发生 1是处理中 2是已完成
     * @param start  起始条目数
     * @param num    每页的数量
     */
    @GET("/serving/property/repair")
    Call<String> selectRepairByState(@Query("access_token") String access_token, @Query("pid") String propertyId, @Query("status") String status, @Query("start") int start, @Query("num") int num);

    //查询通知列表
    @GET("/serving/notice")
    Call<String> queryNoticeList(@Query("access_token") String access_token, @Query("id") String id, @Query("start") int start, @Query("num") int num);

    /**
     * 发布通知
     *
     * @param access_token
     * @param title
     * @param content
     * @param items
     */
    @FormUrlEncoded
    @POST("/serving/notice")
    Call<String> postNotice(@Field("access_token") String access_token, @Field("teamId") String teamId, @Field("title") String title, @Field("content") String content, @Field("items[]") String[] items);

    //更新用户信息
    @POST("/serving/setting")
    @FormUrlEncoded
    Call<String> userSetting(@Query("access_token") String access_Token, @Field("name") String name, @Field("description") String description, @Field("gender") String gender, @Field("portrait") String portrait);

    //获取用户信息
    @GET("/serving/setting")
    Call<String> userGetting(@Query("access_token") String access_token);

    /**
     * 3.状态设置
     *
     * @param access_token
     * @param type
     */
    @PUT("/serving/line")
    Call<String> userCurrentStateChange(@Query("access_token") String access_token, @Query("type") String type);

    /**
     * 物业管理人员接口
     */

    /**
     * 1.添加团队
     *
     * @param access_token
     * @param name
     * @param phone
     * @param address
     * @param type
     */
    @FormUrlEncoded
    @POST("/serving/teams")
    Call<String> createTeam(@Field("access_token") String access_token, @Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("type") String type);

    /**
     * 添加项目
     *
     * @param access_token
     * @param name
     * @param teamId
     * @param communityId
     */
    @FormUrlEncoded
    @POST("/serving/items")
    Call<String> addProject(@Query("access_token") String access_token, @Field("name") String name, @Field("teamId") String teamId, @Field("mouId") String communityId, @Field("description") String description);

    /**
     * 将注册用户加入团队
     *
     * @param access_token
     * @param memberId
     * @param teamId
     */
    @FormUrlEncoded
    @POST("/serving/teams/member")
    Call<String> joinToTeam(@Query("access_token") String access_token, @Field("memberId") String memberId, @Field("teamId") String teamId);

    /**
     * 2.批量添加项目成员
     *
     * @param access_token
     * @param members
     * @param itemId
     */
    @PUT("/serving/items")
    Call<String> joinToProject(@Query("access_token") String access_token, @Query("members[]") List<String> members, @Query("itemId") String itemId);

    /**
     * 3.删离成员从项目组
     *
     * @param access_token
     * @param memberId
     * @param itemId
     */
    @DELETE("/serving/items")
    Call<String> deletePerson(@Query("access_token") String access_token, @Query("memberId") String memberId, @Query("itemId") String itemId);

    /**
     * 查询项目列表
     *
     * @param access_token
     */
    @GET("/serving/items")
    Call<String> queryProjectList(@Query("access_token") String access_token);

    /**
     * 4.查询项目详情
     *
     * @param access_token
     * @param itemId
     */
    @GET("/serving/items/details")
    Call<String> queryProjectDetail(@Query("access_token") String access_token, @Query("itemId") String itemId);

    /**
     * 3获取团队成员
     *
     * @param access_token
     * @param teamId
     */
    @GET("/serving/teams")
    Call<String> queryTeamMember(@Query("access_token") String access_token, @Query("teamId") String teamId);

    /**
     * 团队管理
     * 4.根据手机号获取用户信息
     *
     * @param access_token
     * @param searchString
     * @param
     */
    @GET("/serving/teams/member")
    Call<String> queryOneTeamMember(@Query("access_token") String access_token, @Query("searchString") String searchString);

    /**
     * 2.删离团队
     *
     * @param access_token
     * @param memberId
     * @param teamId
     */
    @DELETE("/serving/teams")
    Call<String> deleteFromTeam(@Query("access_token") String access_token, @Query("memberId") String memberId, @Query("teamId") String teamId);

    /**
     * 6.模糊搜索团队成员 从项目详情页进行跳转查询的
     *
     * @param access_token
     * @param teamId
     * @param searchString
     */
    @GET("/serving/teams/vague")
    Call<String> queryTeamMemberInProjectDetail(@Query("access_token") String access_token, @Query("teamId") String teamId, @Query("searchString") String searchString);


    /**
     * 选择社区
     *
     * @param access_token
     * @param cmyName
     */
    @GET("/serving/items/community")
    Call<String> queryVillage(@Query("access_token") String access_token, @Query("cmyName") String cmyName);

    /**
     * 20.确认接收维修申请订单
     *
     * @param access_token
     * @param id
     */
    @PUT("/serving/property/enSure")
    Call<String> enSureRepair(@Query("access_token") String access_token, @Query("id") String id);

    /**
     * 1.绑定
     *
     * @param access_token
     * @param pushId
     */
    @FormUrlEncoded
    @POST("/serving/push")
    Call<String> binding(@Query("access_token") String access_token, @Field("pushId") String pushId);

    /**
     * 2.解绑
     *
     * @param access_token
     * @param pushId
     */
    @DELETE("/serving/push")
    Call<String> unbundling(@Query("access_token") String access_token, @Query("pushId") String pushId);

    /**
     * 17.根据物业公司id查询不同状态下的报修列表
     *
     * @param access_token
     * @param propertyId
     * @param status
     * @param start
     * @param num
     */
    @GET("/serving/property/repair")
    Call<String> managerRepairByState(@Query("access_token") String access_token, @Query("pid") String propertyId, @Query("status") String status, @Query("start") int start, @Query("num") int num);

    /**
     * 19.根据id查询报修详情(管理人员)
     *
     * @param access_token
     * @param id
     */
    @GET("/serving/property/repair/detail")
    Call<String> managerRepairDetail(@Query("access_token") String access_token, @Query("id") String id);

    /**
     * 查询投诉列表
     *
     * @param access_token
     * @param status
     * @param start
     * @param num
     */
    @GET("/serving/property/suggest")
    Call<String> getSuggestList(@Query("access_token") String access_token, @Query("pid") String pid, @Query("status") String status, @Query("start") int start, @Query("num") int num);

    /**
     * 18.根据id查询投诉建议详情
     *
     * @param access_token
     * @param id
     */
    @GET("/serving/property/suggest/detail")
    Call<String> getSuggestDetail(@Query("access_token") String access_token, @Query("id") String id);

    /**
     * 21.确认授理投诉建议
     *
     * @param access_token
     * @param id
     */
    @PUT("/serving/property/suggest/enSure")
    Call<String> ensureSuggest(@Query("access_token") String access_token, @Query("id") String id);

    /**
     * 11.查询通知详情
     *
     * @param access_token
     * @param id
     */
    @GET("/serving/notice/detail")
    Call<String> getNoticeDetailInfo(@Query("access_token") String access_token, @Query("id") String id);


}
