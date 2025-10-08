package com.example.morse_link.di

import com.example.morse_link.presentation.viewmodels.SharedViewmodel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class viewmodelModule {

    /*TODO -
    *  Add viewmodels here
    *  Provide in mainActivity to navHost
    *  Plug viewmodel into composeables
    *  Hardware layer - sound & flashlight
    *  Hardware light - test with something else
    *  Virtual then -->> real device
    *  */

}