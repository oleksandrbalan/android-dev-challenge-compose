package com.example.androiddevchallenge

object Navigation {

	const val overview = "overview"

	const val detailPetId = "petId"
	const val detail = "detail/{$detailPetId}"
	fun detail(id: String) = detail.replace("{$detailPetId}", id)
}