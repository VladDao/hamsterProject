package com.example.vlad.edittext


import retrofit2.http.GET

interface ApiService {

    @get:GET("/v2/59c92a123f0000780183f72d")
    val friendList: retrofit2.Call<List<String>>
    // Call<ContactList> getResponce(@Path("id") String userId, @Path("friends") String fiendList);
}