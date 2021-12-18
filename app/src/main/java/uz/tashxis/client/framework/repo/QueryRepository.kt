package uz.tashxis.client.framework.repo

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import timber.log.Timber
import uz.tashxis.client.business.util.Constants

class QueryRepository {
    private val database = Firebase.database
    private val queryRef = database.getReference(Constants.FIRESTORE_TABLE_NAME).orderByChild("client_id")
        .equalTo(30.00)
    init {
        queryRef.addValueEventListener(object :  ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Timber.d("onDataChange:" + snapshot.value + " ")
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d("onDataChangeError: ")
            }

        })
    }

}