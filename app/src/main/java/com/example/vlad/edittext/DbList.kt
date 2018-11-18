package com.example.vlad.edittext

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class DbList : RealmObject() {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("Key")
    @Expose
    var Key: String? = null


}




