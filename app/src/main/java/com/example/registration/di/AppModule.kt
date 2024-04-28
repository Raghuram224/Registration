package com.example.registration.di

import android.content.Context
import com.example.registration.modal.LoginRepo
import com.example.registration.modal.SignupRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContextForSignup(@ApplicationContext context: Context) = SignupRepo(mContext = context)

    @Singleton
    @Provides
    fun provideApplicationContextForLogin(@ApplicationContext context: Context) = LoginRepo(mContext = context)

}