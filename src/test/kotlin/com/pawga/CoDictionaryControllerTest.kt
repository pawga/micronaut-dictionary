package com.pawga

import com.pawga.domain.model.Dictionary
import com.pawga.storage.data.DictionaryDb
import com.pawga.storage.repository.CoDictionaryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.annotation.Client
import io.micronaut.reactor.http.client.ReactorHttpClient
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import java.util.*

/**
 * Created by pawga777
 */
@MicronautTest(transactional = false)
class CoDictionaryEasyControllerTest(
    @Client("/api/v1/co-dictionary") private val client: ReactorHttpClient,
    private val coDictionaryRepository: CoDictionaryRepository,
) : BehaviorSpec({

    given("CoDictionaryController") {
        `when`("test find all") {
            val repository = getMock(coDictionaryRepository)
            coEvery { repository.findAll() }
                .returns(
                    flowOf(
                        DictionaryDb(
                            id = 1,
                            name = "test1",
                        ),
                        DictionaryDb(
                            id = 2,
                            name = "test2",
                        ),
                    )
                )
            val response = client.toBlocking().exchange("/list", Array<Dictionary>::class.java)

            then("should return OK") {
                response.status shouldBe HttpStatus.OK
                val list = response.body()!!
                list.size shouldBe 2
                list[0].name shouldBe "test1"
                list[1].name shouldBe "test2"
            }
        }

        `when`("createDictionary") {
            val repository = getMock(coDictionaryRepository)
            coEvery { repository.save(any<DictionaryDb>()) }
                .returns(
                    DictionaryDb(
                        id = 1001,
                        name = "saved_record",
                    )
                )
            val request = HttpRequest.POST<String>("/", "{\"name\": \"string\"}")
            val response: HttpResponse<Dictionary> = client.toBlocking().exchange(request)
            then("should return CREATED") {
                response.status shouldBe HttpStatus.CREATED
                val dictionary = response.getBody(Dictionary::class.java).get()
                dictionary.name shouldBe "saved_record"
                response.header(HttpHeaders.LOCATION) shouldBe "/api/v1/co-dictionary/1001"
            }
        }

        `when`("createDictionary") {

        }
    }
}) {

    @MockBean(CoDictionaryRepository::class)
    fun mockedPostRepository() = mockk<CoDictionaryRepository>()
}