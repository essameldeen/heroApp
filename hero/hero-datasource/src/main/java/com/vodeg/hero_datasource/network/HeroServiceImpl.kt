package com.vodeg.hero_datasource.network

import com.vodeg.hero_datasource.network.HeroDto
import com.vodeg.hero_datasource.network.toHero
import com.vodeg.hero_domain.Hero
import io.ktor.client.*
import io.ktor.client.request.*

class HeroServiceImpl(
    private val httpClient: HttpClient,
): HeroService {

    override suspend fun getHeroStats(): List<Hero> {
        return httpClient.get<List<HeroDto>> {
            url(Endpoints.HERO_STATS)
        }.map { it.toHero() }
    }
}