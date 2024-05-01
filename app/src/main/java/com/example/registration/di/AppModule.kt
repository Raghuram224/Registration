package com.example.registration.di

import android.content.Context
import com.example.registration.modal.LocalDBRepo
import com.example.registration.permissionHandler.PermissionHandler
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
    fun provideApplicationContext(@ApplicationContext context: Context) = LocalDBRepo(mContext = context)

    @Singleton
    @Provides
    fun providePermissionHandler(@ApplicationContext context: Context) =PermissionHandler(mContext = context)



}