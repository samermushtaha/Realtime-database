package com.example.realtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.realtimedatabase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.reference
        var i = 0

        binding.btnAddUser.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val birthdate = binding.etBirthdate.text.toString()

            if(name.isNotEmpty() && phoneNumber.isNotEmpty() && birthdate.isNotEmpty()){
                val user = User(name, phoneNumber, birthdate)
                myRef.child("User").child("$i").setValue(user)
                i++
            }
        }

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvData.text = snapshot.value.toString()
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
        })

    }
}