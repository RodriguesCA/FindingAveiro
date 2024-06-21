package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Welcome {
                        navigateToMapActivity()
                    }
                }
            }
        }
    }

    private fun navigateToMapActivity() {
        val intent = Intent(this, MapPage::class.java)
        startActivity(intent)
    }
}

@Composable
fun Welcome(onConfirm: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Finding Aveiro", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Username Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Username",
                modifier = Modifier.weight(1f)
            )
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Password Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Password",
                modifier = Modifier.weight(1f)
            )
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(16.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onConfirm) {
            Text(text = "Confirm")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    TestTheme {
       Welcome {}
    }
}