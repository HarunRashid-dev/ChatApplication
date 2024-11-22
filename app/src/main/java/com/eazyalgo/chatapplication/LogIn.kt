package com.eazyalgo.chatapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eazyalgo.chatapplication.SignUp
import com.eazyalgo.chatapplication.ui.theme.ChatApplicationTheme
import com.google.firebase.Firebase

class LogIn : ComponentActivity() {

    private Lateinit var edtEmail: EditText
    private Lateinit var edtPassword: EditText
    private Lateinit var btnLogIn: Button
    private Lateinit var btnSignUp: Button
    private Lateinit var mAuth : FirebaseAuth





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogIn = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignup.setOnClickListener {
            val intent  = Intent( this, SignUp::class.java)
            startActivity(intent)

        }

        btnLogIn.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email,password);

        }

        private fun login(email: String, password: string){

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@LogIn, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                   Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).shoe()
                    }
                }

        }





        enableEdgeToEdge()
        setContent {
            ChatApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ChatApplicationTheme {
        Greeting2("Android")
    }
}