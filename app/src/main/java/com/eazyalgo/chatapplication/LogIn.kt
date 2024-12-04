package com.eazyalgo.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eazyalgo.chatapplication.ui.theme.ChatApplicationTheme
import com.google.firebase.auth.FirebaseAuth

class LogIn : ComponentActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LogInScreen(innerPadding)
                }
            }
        }
    }

    @Composable
    fun LogInScreen(modifier: Modifier = Modifier) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val mAuth = FirebaseAuth.getInstance()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Log In", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(32.dp))

            // Email TextField
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password TextField
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Log In Button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        login(email, password)
                    } else {
                        Toast.makeText(this@LogIn, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log In")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // SignUp Button
            TextButton(onClick = {
                val intent = Intent(this@LogIn, SignUp::class.java)
                startActivity(intent)
            }) {
                Text("Don't have an account? Sign Up")
            }
        }
    }

    // Function to handle login
    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    ChatApplicationTheme {
        LogInScreen()
    }
}
