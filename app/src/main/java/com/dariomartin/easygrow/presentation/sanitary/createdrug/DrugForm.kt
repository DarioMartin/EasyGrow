package com.dariomartin.easygrow.presentation.sanitary.createdrug

data class DrugForm(
    var name: String? = null,
    var pharmacy: String? = null,
    var url: String? = null,
    var drugMass: Float = 0F,
    var drugVolume: Float = 0F,
    var cartridgeVolume: Float = 0F
) {
    fun isValid() = isValidName()
            && isValidPharmacy()
            && isValidDrugMass()
            && isValidDrugVolume()
            && isValidCartridgeVolume()

    fun isValidName() = !name.isNullOrEmpty()
    fun isValidPharmacy() = !pharmacy.isNullOrEmpty()
    fun isValidDrugMass() = drugMass > 0
    fun isValidDrugVolume() = drugVolume > 0
    fun isValidCartridgeVolume() = cartridgeVolume > 0
}
