package kg.bakai.sunrisejokes.framework

import kg.bakai.sunrisejokes.domain.model.Joke
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("random_ten")
    suspend fun getJokes(): Response<List<Joke>>
}