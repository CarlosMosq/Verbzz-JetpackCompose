package com.company.verbzz_app.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.company.verbzz_app.R
import com.company.verbzz_app.model.Stats
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject

class FireRepository @Inject constructor(private val reference: DatabaseReference) {
    private val userUID = FirebaseAuth.getInstance().currentUser?.uid

    fun saveStatsToDatabase(tense: String, scores: String, date: String, language: String) {
        //generates a random key for each object in the user's score
        val timeStampKey = reference.child("Scores").child(userUID!!).push().key
        //sets an order variable that will be used to organize data chronologically
        val ref = reference.child("Scores").child(userUID).child(timeStampKey!!)

        try {
            ref.child("order").setValue(ServerValue.TIMESTAMP)
            ref.child("order").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.value != null) {
                        if(dataSnapshot.value.toString().toLong() >= 0) {
                            ref.child("order")
                                .setValue(-dataSnapshot.value.toString().toLong())
                        }
                        //saves data passed as arguments into database variables
                        //variables saved with lowercase pattern to match with Stats model class
                        ref.child("tense").setValue(tense)
                        ref.child("score").setValue(scores)
                        ref.child("date").setValue(date)
                        ref.child("language").setValue(language)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w("SAVE_SCORE_ERROR", "Failed to read value.", error.toException())
                }
            })
        } catch (ex: Exception) {
            Log.w("SAVE_SCORE_ERROR", "Failed to read value. $ex")
        }
    }

    fun getListOfScores(stateData: MutableState<List<Stats>>) {
        try {
            reference
                .child("Scores")
                .child(userUID!!)
                .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    stateData.value = dataSnapshot.children.map {
                        it.getValue(Stats::class.java)!!
                    }.reversed()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(
                        "SCORE_CANCELLED_ERROR",
                        "Read scores cancelled.",
                        error.toException())
                }
            })
        } catch (exception: Exception){
            Log.w("SCORE_ERROR", "Failed to read scores. $exception")
        }

    }

    fun getCurrentLanguage(languageState: MutableState<String>, userID: String) {
        try {
            reference
                .child("Current Language")
                .child(userID)
                .child("Current Language")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        languageState.value = dataSnapshot.getValue(String::class.java)!!

                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.w(
                            "LANGUAGE_CANCELLED_ERROR",
                            "Cancelled language read value.",
                            error.toException())
                    }
                })

        } catch (ex: Exception) {
            Log.w("LANGUAGE_ERROR", "Failed to read current language. $ex")
        }
    }

    fun setCurrentLanguage(language: String, context: Context, userID: String) {
        val languageToSet =
            if(language == context.getString(R.string.english)) context.getString(R.string.english)
            else context.getString(R.string.french)
        try {
            reference
                .child("Current Language")
                .child(userID)
                .child("Current Language")
                .setValue(languageToSet)
                .addOnSuccessListener {
                    Toast.makeText(
                        context,
                        context.getString(R.string.languageSet),
                        Toast.LENGTH_SHORT).show()
                }
        } catch (ex: Exception) {
            Log.w("SAVE_ERROR", "Failed to save current language. $ex")
        }
    }



}