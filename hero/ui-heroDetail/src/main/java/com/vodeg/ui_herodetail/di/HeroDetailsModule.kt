package com.vodeg.ui_herodetail.di

import com.vodeg.hero_interactors.GetHeroFromCache
import com.vodeg.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailsModule {

    @Provides
    @Singleton
    fun provideGetHeroCache(
        heroInteractors: HeroInteractors
    ): GetHeroFromCache {
        return heroInteractors.getHeroFromCache
    }
}