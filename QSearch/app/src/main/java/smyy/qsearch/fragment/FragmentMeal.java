package smyy.qsearch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import smyy.qsearch.R;
import smyy.qsearch.adapter.ChatAdapter;
import smyy.qsearch.helper.Database;
import smyy.qsearch.helper.QSearchApplication;
import smyy.qsearch.model.Message;
import smyy.qsearch.servis.CallBack;

public class FragmentMeal extends Fragment {

    EditText messageEdit;
    RecyclerView listMessage;
    Button chatSendButton;
    String personid;
    private Socket mSocket;
    public static Database db;
    public static List<Message> messages;
    public static List<Message> source_messages;
    ChatAdapter chatAdapter;
    CallBack callBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meal_search, container, false);
        messageEdit = (EditText) view.findViewById(R.id.etMessage);
        chatSendButton = (Button) view.findViewById(R.id.btnSend);
        listMessage = (RecyclerView) view.findViewById(R.id.listMessage);
        setHasOptionsMenu(true);
        db = new Database(getContext());
        QSearchApplication app = (QSearchApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on("message_list", message_list);
        mSocket.connect();
        final int type = getArguments().getInt("type");
        source_messages = new ArrayList<>();
        messages = db.getAllMessages();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSourceType() == type) {
                source_messages.add(messages.get(i));
            }
        }
        if (source_messages != null) {
            chatAdapter = new ChatAdapter(getActivity(), source_messages, personid);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            listMessage.setLayoutManager(mLayoutManager);
            listMessage.setItemAnimator(new DefaultItemAnimator());
            listMessage.setAdapter(chatAdapter);
        }
        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = messageEdit.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(getContext(), "Lütfen bir mesaj girin.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        Message message1 = new Message();
                        message1.setMessageID(messages.size() + 1);
                        message1.setisMine(true);
                        message1.setMessageContent(message);
                        message1.setSourceType(type);
                        addMessage(message1);
                        int source_type;
                        if (type == 0)
                            source_type = 1;
                        else if (type == 1)
                            source_type = 0;
                        else
                            source_type = type;

                        JSONObject obj = new JSONObject();
                        obj.put("content", message);
                        obj.put("source", source_type);
                        callBack = new CallBack(getContext());
                        callBack.sendMessage(message);
                        //mSocket.emit("send_msg", obj);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    messageEdit.setText("");
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("message_list ", message_list);
    }


    public void addMessage(Message chatMessage) {
        db.insertMessage(chatMessage);
        if (chatMessage == null)
            source_messages = new ArrayList<>();
        source_messages.add(chatMessage);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter = new ChatAdapter(getActivity(), source_messages, personid);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                listMessage.setLayoutManager(mLayoutManager);
                listMessage.setItemAnimator(new DefaultItemAnimator());
                listMessage.setAdapter(chatAdapter);
            }
        });
    }

    public void removeAllMessage() {
        db.resetTables();
        source_messages = new ArrayList<>();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatAdapter = new ChatAdapter(getActivity(), source_messages, personid);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                listMessage.setLayoutManager(mLayoutManager);
                listMessage.setItemAnimator(new DefaultItemAnimator());
                listMessage.setAdapter(chatAdapter);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                removeAllMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Emitter.Listener message_list = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj;
                    try {
                        obj = (JSONObject) args[0];
                        String tefsir = obj.getString("tefsir");
                        JSONArray tefsirobj = new JSONArray(tefsir);
                        JSONObject jsonObject = tefsirobj.getJSONObject(0);
                        String Meal = jsonObject.getString("Meal");
                        Message message = new Message();
                        message.setisMine(false);
                        message.setMessageID(messages.size() + 1);
                        message.setSourceType(getArguments().getInt("type"));
                        message.setMessageContent(Meal);
                        addMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}