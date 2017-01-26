package yss.com.myrongappication.friendcircle.friendcircledemo.bean;

import java.io.Serializable;
import java.util.List;

public class CircleBean implements Serializable {
	private static final long serialVersionUID = -8546131748993549867L;
	public String id;
	public String userId;
	public String portrait;// 头像uri
	public String nickName;// 昵称
	public int goodCount;// 赞数
	public String releaseTime;// 发布时间
	public String dynamicContent;// 动态详情
	public List<String> circleImg;// 图片集合
}
