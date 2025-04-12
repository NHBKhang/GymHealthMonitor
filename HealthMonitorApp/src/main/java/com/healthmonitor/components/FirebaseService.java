package com.healthmonitor.components;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.healthmonitor.pojo.Message;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public void sendMessage(int senderId, Message message) {
        String conversationId = generateConversationId(
                String.valueOf(senderId), 
                String.valueOf(message.getRecieverId())
        );
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("messages/" + conversationId)
                .push();
        ref.setValueAsync(message);
    }

    private String generateConversationId(String userA, String userB) {
        return userA.compareTo(userB) < 0 ? userA + "_" + userB : userB + "_" + userA;
    }

    public void listenForMessages(String userIdA, String userIdB) {
        String conversationId = generateConversationId(userIdA, userIdB);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("messages/" + conversationId);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                Message msg = snapshot.getValue(Message.class);
                System.out.println("New message: " + msg.getContent());
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {

            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {

            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {

            }

            @Override
            public void onCancelled(DatabaseError de) {

            }
        });
    }
}
