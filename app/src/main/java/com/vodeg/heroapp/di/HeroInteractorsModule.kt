package com.vodeg.heroapp.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.vodeg.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HeroInteractorsModule {

    @Provides
    @Singleton
    @Named("heroAndroidSqlDriver") // if had another sql delight db
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = HeroInteractors.schema,
            context = app,
            name = HeroInteractors.dbName,
        )
    }

    @Provides
    @Singleton
    fun provideHeroInteractors(
        @Named("heroAndroidSqlDriver") sqlDriver: SqlDriver
    ): HeroInteractors {
        return HeroInteractors.build(sqlDriver)
    }
}