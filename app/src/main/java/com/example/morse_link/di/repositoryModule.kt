package com.example.morse_link.di

import com.example.morse_link.data.repository.MorseRepoImpl
import com.example.morse_link.domain.morseRepo.MorseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class repositoryModule {

    @Binds
    abstract fun bindMorseRepository (
        impl: MorseRepoImpl
    ): MorseRepository
}