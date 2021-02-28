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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.detail.PetDetail
import com.example.androiddevchallenge.model.MockData
import com.example.androiddevchallenge.overview.PetsOverview
import com.example.androiddevchallenge.ui.theme.JetPawTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPawTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Navigation.overview) {
                    composable(Navigation.overview) { PetsOverview(navController) }
                    composable(Navigation.detail) {
                        val id = it.arguments?.getString(Navigation.detailPetId)
                        val pet = MockData.pets.find { it.id == id }
                            ?: error("No pet found with id $id")
                        PetDetail(navController, pet)
                    }
                }
            }
        }
    }
}
