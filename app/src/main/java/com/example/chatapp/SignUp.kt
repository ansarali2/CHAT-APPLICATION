package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var btnSignIn: Button
    private lateinit var mauth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    @SuppressLint("MissingInflatedId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mauth= FirebaseAuth.getInstance()
        edtName = findViewById(R.id.edtext_usrname)
        edtEmail=findViewById(R.id.edtext_email)
        edtPassword=findViewById(R.id.edtext_paswrd)
        btnSignIn = findViewById(R.id.btn_signIn)



        btnSignIn.setOnClickListener {
            val name=edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signIn(name,email,password)
        }
    }

    private fun signIn(name:String,email: String,password: String){
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email, mauth.currentUser?.uid!!)
                   val intent=Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(this@SignUp,"Some error occured",Toast.LENGTH_SHORT).show()

                }
            }

    }
    private fun addUserToDatabase(name: String,email: String,uid: String){
        mDbRef=FirebaseDatabase.getInstance().getReference()
         mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}