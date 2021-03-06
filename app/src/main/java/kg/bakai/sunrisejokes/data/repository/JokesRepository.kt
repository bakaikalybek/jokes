package kg.bakai.sunrisejokes.data.repository

import kg.bakai.sunrisejokes.domain.model.Joke

interface JokesRepository {
    suspend fun getJokes(): List<Joke>
}