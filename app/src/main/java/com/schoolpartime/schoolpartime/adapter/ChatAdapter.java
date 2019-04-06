package com.schoolpartime.schoolpartime.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

public class ChatAdapter extends BaseAdapter {
	private Context context;
	private List<Message> lists;
	private long from;

	public ChatAdapter(Context context, List<Message> lists) {
		super();
		this.context = context;
		this.lists = lists;
		from = SpCommonUtils.getUserId();
	}

	/**
	 * 是否是自己发送的消息
	 *
	 * @author cyf
	 *
	 */
	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int isMeSend(int position) {
		if (lists.get(position).getMsg_from() != from) {// 收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {// 自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HolderView holderView = null;
		Message entity = lists.get(arg0);
		boolean isMeSend = entity.getMsg_from()==from?true:false;
		if (holderView == null) {
			holderView = new HolderView();
			if (isMeSend) {
				arg1 = View.inflate(context, R.layout.chat_dialog_right_item,
						null);
				holderView.tv_chat_me_message = (TextView) arg1
						.findViewById(R.id.tv_chat_me_message);
				holderView.tv_chat_me_message.setText(entity.getMsg_mes());
			} else {
				arg1 = View.inflate(context, R.layout.chat_dialog_left_item,
						null);
				holderView.tv_chat_me_message = (TextView) arg1
						.findViewById(R.id.tvname);
				holderView.tv_chat_me_message.setText(entity.getMsg_mes());
			}
			arg1.setTag(holderView);
		} else {
			holderView = (HolderView) arg1.getTag();
		}

		return arg1;
	}

	class HolderView {
		TextView tv_chat_me_message;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
