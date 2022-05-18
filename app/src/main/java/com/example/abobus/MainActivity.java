package com.example.abobus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        db = FirebaseDatabase.getInstance().getReference();

        db.child("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot userSnapshot : task.getResult().getChildren()) {
                    User user = userSnapshot.getValue(User.class);


                    Query query = db.child("Messages").orderByChild("UserID").equalTo(user.UserID);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            printLine(user.UserID + ". " + user.Name);
                            for (DataSnapshot messageSnapshot : snapshot.getChildren()) {

                                Message message = messageSnapshot.getValue(Message.class);
                                printLine('\t' + message.Text);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void printLine(String text) {
        String oldText = textView.getText().toString();
        textView.setText(oldText + text + '\n');
    }
}