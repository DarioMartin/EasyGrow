package com.dariomartin.easygrow.data.sources.firestore

import android.util.Log
import com.dariomartin.easygrow.data.Result
import com.dariomartin.easygrow.data.sources.IAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class FirebaseAuthSource @Inject constructor() : IAuth {

    companion object {
        private val TAG = FirebaseAuthSource::class.java.simpleName
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user?.let {
                Log.d(TAG, "signInWithEmail:success")
                Result.Success(true)
            } ?: Result.Error(IOException("Error logging in"))
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    override suspend fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Boolean> {
        return Result.Error(IOException("Error signing up"))
    }

    override fun logout() {
        auth.signOut()
    }

    override fun getUserId(): String? = auth.currentUser?.uid

}