/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.model

import androidx.annotation.DrawableRes
import com.example.androiddevchallenge.R

interface Pet {
    val id: String
    val name: String
    val image: String
    val tags: List<String>
    val age: String
    val sex: Sex
    val location: String
    val description: String

    enum class Sex(@DrawableRes val iconRes: Int) {
        MALE(R.drawable.ic_pet_male),
        FEMALE(R.drawable.ic_pet_female)
    }
}

data class Dog(
    override val id: String,
    override val name: String,
    override val image: String,
    override val tags: List<String>,
    override val age: String,
    override val sex: Pet.Sex,
    override val location: String,
    override val description: String,
) : Pet

data class Cat(
    override val id: String,
    override val name: String,
    override val image: String,
    override val tags: List<String>,
    override val age: String,
    override val sex: Pet.Sex,
    override val location: String,
    override val description: String,
) : Pet
