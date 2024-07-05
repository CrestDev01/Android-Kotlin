package com.example.grocerylist.startUp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grocerylist.auth.SignInActivity
import com.example.grocerylist.auth.SignUpActivity
import com.example.grocerylist.base.firebase.FireBaseRepository
import com.example.grocerylist.databinding.ActivityIntroBinding
import com.example.grocerylist.home.MainActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUserId = FireBaseRepository().getCurrentUserId()
        if (currentUserId.isNotBlank() || currentUserId.isNotEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.introBtnSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.introBtnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}