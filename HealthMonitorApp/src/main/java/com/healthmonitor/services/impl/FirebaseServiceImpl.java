package com.healthmonitor.services.impl;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.healthmonitor.pojo.Message;
import com.healthmonitor.services.FirebaseService;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    public void sendMessage(String chatRoomId, Message message) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("messages/" + chatRoomId)
                .push();

        ref.setValueAsync(message);
    }

    public void listenForMessages(String chatRoomId) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("messages/" + chatRoomId);

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
