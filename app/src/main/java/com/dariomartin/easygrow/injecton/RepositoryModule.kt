package com.dariomartin.easygrow.injecton

import android.content.Context
import com.dariomartin.easygrow.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule @Inject constructor() {

    @Provides
    fun providePatientRepository(): IPatientRepository {
        return PatientRepositoryImpl()
    }

    @Provides
    fun provideDoctorRepository(): IDoctorRepository {
        return DoctorRepositoryImpl()
    }

    @Provides
    fun provideUserRepository(@ApplicationContext appContext: Context): IUserRepository {
        val pref = EGPreferences(appContext)
        return UserRepositoryImpl(pref)
    }

    @Provides
    fun provideLoginRepository(@ApplicationContext appContext: Context): IAuthRepository {
        val pref = EGPreferences(appContext)
        return AuthRepositoryImpl(pref)
    }

    @Provides
    fun provideDrugRepository(): IDrugRepository {
        return DrugRepositoryImpl()
    }

}