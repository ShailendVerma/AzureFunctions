package org.shail.azure

import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.HttpTrigger
import java.util.*

class KotlinFunction {

    @FunctionName("HttpTrigger-Kotlin")
    fun run(@HttpTrigger(name = "req",
            methods = [HttpMethod.GET, HttpMethod.POST],
            authLevel = AuthorizationLevel.FUNCTION)
            request: HttpRequestMessage<Optional<String>>,
            context: ExecutionContext): HttpResponseMessage = request.let {
        context.logger.info("Kotlin HTTP trigger processed a request.")
        it.body.orElse(it.queryParameters["name"])
                ?.let { name ->
                    it
                            .createResponseBuilder(HttpStatus.OK)
                            .body("Hello, $name")
                }
                ?: it
                        .createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("Please pass a name on the query string or in the request body")

    }.build()
}