package com.dariomartin.easygrow.injecton

import com.dariomartin.easygrow.model.repository.IPatientRepository
import com.dariomartin.easygrow.model.repository.PatientRepositoryMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun providePatientRepository(): IPatientRepository {
        return PatientRepositoryMock()
    }
}