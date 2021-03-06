package kg.bakai.sunrisejokes.data.repository

import kg.bakai.sunrisejokes.domain.model.Joke
import kg.bakai.sunrisejokes.framework.Api
import kg.bakai.sunrisejokes.framework.exception.ResponseException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class JokesRepositoryImplementation(private val api: Api) : JokesRepository {
    override suspend fun getJokes(): List<Joke> {
        try {
            val response = api.getJokes()
            val body = response.body()

            return if (response.isSuccessful && body != null) {
                body
            } else {
                throw Exception()
            }
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> throw ResponseException("Нет интернет подключения")
                is SocketTimeoutException -> throw ResponseException("Время соединения истекло")
                else -> throw ResponseException("Неизвестная ошибка")
            }
        }
    }
}