package com.eazyalgo.chatapplication

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)  // Ensure this uses the layout resource ID

        // Retrieve intent extras
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        // Initialize FirebaseAuth and FirebaseDatabase
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().reference

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        // Set the action bar title
        supportActionBar?.title = name

        // Initialize views
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        // Set up RecyclerView
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        // Listen for messages in the database
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        if (message != null) {
                            messageList.add(message)
                        }
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })

        // Send button click listener
        sendButton.setOnClickListener {
            val messageText = messageBox.text.toString()
            if (messageText.isNotEmpty()) {
                val messageObject = Message(messageText, senderUid)
                mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(messageObject)
                    }
                messageBox.setText("")
            }
        }
    }
}
