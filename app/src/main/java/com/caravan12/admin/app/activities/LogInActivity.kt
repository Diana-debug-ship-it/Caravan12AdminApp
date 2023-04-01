package com.caravan12.admin.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.caravan12.admin.app.R
import com.caravan12.admin.app.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun signIn(){
        val email = binding.edTextEmail.text.toString().trim()
        val password = binding.edTextPassword.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .addOnFailureListener {
                    StyleableToast.makeText(this, getString(R.string.strWrongEmailOrPassword), Toast.LENGTH_SHORT, R.style.errorToast).show()
                }
        } else {
            StyleableToast.makeText(this, getString(R.string.warningEmptyFields), Toast.LENGTH_SHORT, R.style.errorToast).show()
        }

    }

    fun onClickSignIn(view: View) {
        signIn()
    }
}