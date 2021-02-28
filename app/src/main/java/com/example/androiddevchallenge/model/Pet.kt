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