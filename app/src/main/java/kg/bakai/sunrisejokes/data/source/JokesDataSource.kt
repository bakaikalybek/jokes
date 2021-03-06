package kg.bakai.sunrisejokes.data.source

import kg.bakai.sunrisejokes.domain.model.Joke

interface JokesDataSource {
    suspend fun getJokes(): List<Joke>
}