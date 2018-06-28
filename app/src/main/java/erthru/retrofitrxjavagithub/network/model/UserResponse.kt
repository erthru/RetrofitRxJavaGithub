package erthru.retrofitrxjavagithub.network.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

        @SerializedName("name") var name:String?,
        @SerializedName("description") var description:String?,
        @SerializedName("stargazers_count") var stargazers_count:String?,
        @SerializedName("owner") var owner:Owner

)