package com.pawga.exception

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.exceptions.HttpClientException
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

/**
 * Created by pawga777
 */
@Produces
@Singleton
@Requires(classes = [Throwable::class])
class DictionaryExceptionHandler : ExceptionHandler<Throwable, HttpResponse<ErrorResponse>> {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(
        request: HttpRequest<*>?,
        exception: Throwable?,
    ): HttpResponse<ErrorResponse> {
        if (request != null) {
            val httpMethod = request.method
            val methodName = request.methodName
            val path = request.path

            logger.error("Received error in [$methodName] on [$path] with method [$httpMethod]: \n$exception")
        }
        return when (exception) {
            is NotFoundException -> {
                val errorResponse = ErrorResponse()
                errorResponse.httpStatus = HttpStatus.NOT_FOUND
                errorResponse.exception = exception::class.simpleName
                errorResponse.message = exception.message
                return HttpResponse.notFound(errorResponse)
            }
            is ResponseStatusException -> {
                val errorResponse = ErrorResponse()
                errorResponse.httpStatus = exception.status
                errorResponse.exception = exception::class.simpleName
                errorResponse.message = exception.reason
                return HttpResponse.serverError(errorResponse)
            }
            is HttpClientException -> {
                val errorResponse = ErrorResponse()
                errorResponse.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                errorResponse.exception = exception::class.simpleName
                errorResponse.message = exception.message
                return HttpResponse.serverError(errorResponse)
            }
            else -> HttpResponse.status(HttpStatus.I_AM_A_TEAPOT)
        }
    }
}
