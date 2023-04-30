package com.wonder.domain.usecase

abstract class UseCase<in P, R> {

    suspend operator fun invoke(parameters: P): R? = try {
        execute(parameters)
    } catch (e: Exception) {
        null
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}

abstract class NoParamUseCase<R> {

    suspend operator fun invoke(): R? = try {
        execute()
    } catch (e: Exception) {
        null
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}

abstract class ResultUseCase<in P, R> {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            execute(parameters).let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Exception(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}

abstract class ResultNoParamUseCase<R> {

    suspend operator fun invoke(): Result<R> {
        return try {
            execute().let {
                Result.Success(it)
            }
        } catch (e: Exception) {
            Result.Exception(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}
