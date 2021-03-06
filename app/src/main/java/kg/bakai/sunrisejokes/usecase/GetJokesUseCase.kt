package kg.bakai.sunrisejokes.usecase

import kg.bakai.sunrisejokes.data.source.JokesDataSource
import kg.bakai.sunrisejokes.domain.model.Joke

class GetJokesUseCase(private val dataSource: JokesDataSource) {
    suspend fun getJokes(): List<Joke> {
        return dataSource.getJokes()
    }
}