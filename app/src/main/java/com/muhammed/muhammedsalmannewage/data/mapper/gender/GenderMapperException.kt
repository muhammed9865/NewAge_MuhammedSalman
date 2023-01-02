package com.muhammed.muhammedsalmannewage.data.mapper.gender

sealed class GenderMapperException : Exception() {
    class GenderMappingNotFound : GenderMapperException()
}