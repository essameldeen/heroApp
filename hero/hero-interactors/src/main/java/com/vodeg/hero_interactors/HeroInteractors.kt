package com.vodeg.hero_interactors

import com.squareup.sqldelight.db.SqlDriver
import com.vodeg.hero_datasource.cache.HeroCache
import com.vodeg.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeros: GetHeros,
) {

    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)


            return HeroInteractors(
                getHeros = GetHeros(
                    cache = cache,
                    service = service
                ),

                )
        }
        val schema: SqlDriver.Schema = HeroCache.schema
        val dbName: String = HeroCache.dbName
    }
}
