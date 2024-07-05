package com.example.grocerylist.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.grocerylist.R
import com.example.grocerylist.base.baseActivity.BaseActivity
import com.example.grocerylist.databinding.ActivitySignUpBinding
import com.example.grocerylist.home.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionbar()

        binding.signUpBtnSignIn.setOnClickListener { registerUser() }


        binding.signUpEtName.doOnTextChanged { _, _, _, _ -> removeError() }
        binding.signUpEtEmail.doOnTextChanged { _, _, _, _ -> removeError() }
        binding.signUpEtPassword.doOnTextChanged { _, _, _, _ -> removeError() }
    }

    private fun removeError() {
        binding.signUpTilName.setErrorMessage("")
        binding.signUpTilEmail.setErrorMessage("")
        binding.signUpTilPassword.setErrorMessage("")
    }

    private fun setupActionbar() {
        setSupportActionBar(binding.signUpToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.signUpToolBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun registerUser() {
        val name: String = binding.signUpEtName.text.toString().trim { it <= ' ' }
        val email: String = binding.signUpEtEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.signUpEtPassword.text.toString().trim { it <= ' ' }
        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.progress_please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("TAG", "registerUser: ", )
                        signUpSuccess()
                    } else {
                        hideProgressDialog()
                        Toast.makeText(
                            this,
                            "Registration failed",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            name.isBlank() -> {
                binding.signUpTilName.setErrorMessage("Please enter a name.")
                false
            }

            email.isBlank() -> {
                binding.signUpTilEmail.setErrorMessage("Please enter an email.")
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.signUpTilEmail.setErrorMessage("Enter valid email.")
                false
            }

            password.isBlank() -> {
                binding.signUpTilPassword.setErrorMessage("Please enter a password.")
                false
            }

            password.length < 4 -> {
                binding.signUpTilPassword.setErrorMessage("Password must be 5 or more character long.")
                false
            }

            else -> true
        }
    }

    fun signUpSuccess() {
        hideProgressDialog()
        Toast.makeText(this, "Registration successful, Welcome!", Toast.LENGTH_SHORT)
            .show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}