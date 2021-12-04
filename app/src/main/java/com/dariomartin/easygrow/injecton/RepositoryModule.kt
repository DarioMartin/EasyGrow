package com.dariomartin.easygrow.injecton

import android.content.Context
import com.dariomartin.easygrow.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun providePatientRepository(): IPatientRepository {
        return PatientRepositoryImpl()
    }

    @Provides
    fun provideDoctorRepository(): IDoctorRepository {
        return DoctorRepositoryImpl()
    }

    @Provides
    fun provideUserRepository(): IUserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    fun provideLoginRepository(): IAuthRepository {
        return AuthRepositoryImpl()
    }

}