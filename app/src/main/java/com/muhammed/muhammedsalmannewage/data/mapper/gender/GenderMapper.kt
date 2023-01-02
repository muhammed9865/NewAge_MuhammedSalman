package com.muhammed.muhammedsalmannewage.data.mapper.gender

import com.muhammed.muhammedsalmannewage.domain.model.State
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender

class GenderMapper {
    companion object {
        fun mapGenderToDomain(genderStr: String) : State<Gender> {
            return try {
                val gender = Gender.valueOf(genderStr.uppercase())
                State.Success(gender)
            }catch (e: IllegalArgumentException) {
                State.Failure(
                    data = Gender.UNDEFINED,
                    message = "Couldn't map string to existing gender",
                    throwable = GenderMapperException.GenderMappingNotFound()
                )
            }catch (e: Exception) {
                State.Failure(
                    data = Gender.UNDEFINED,
                    message = "Unknown error",
                    throwable = e
                )
            }
        }
    }
}