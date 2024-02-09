package com.example.apilist.model

data class PricesX(
    val `1stEditionHolofoil`: StEditionHolofoil,
    val holofoil: Holofoil,
    val normal: Normal,
    val reverseHolofoil: ReverseHolofoil,
    val unlimitedHolofoil: UnlimitedHolofoil
)