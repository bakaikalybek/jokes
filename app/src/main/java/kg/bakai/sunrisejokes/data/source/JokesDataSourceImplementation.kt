package kg.bakai.sunrisejokes.data.source

import kg.bakai.sunrisejokes.data.repository.JokesRepository
import kg.bakai.sunrisejokes.domain.model.Joke

class JokesDataSourceImplementation(private val respository: JokesRepository) : JokesDataSource {
    override suspend fun getJokes(): List<Joke> {
        return respository.getJokes()
    }
}