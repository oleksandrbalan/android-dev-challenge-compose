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
package com.example.androiddevchallenge.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.components.PetInfo
import com.example.androiddevchallenge.model.Pet
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PetDetail(navController: NavHostController, pet: Pet) {
    Surface {
        Box(Modifier.fillMaxSize()) {
            PetDetailContent(pet)
            UpButton(navController)
        }
    }
}

@Composable
private fun PetDetailContent(pet: Pet, modifier: Modifier = Modifier) {
    Column(modifier) {
        val state = rememberLazyListState()
        LazyColumn(
            state = state,
            modifier = Modifier.weight(1f)
        ) {
            item {
                CoilImage(
                    data = pet.image,
                    contentDescription = stringResource(id = R.string.a11y_pet_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            item {
                PetInfo(
                    pet,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            item {
                Text(
                    text = pet.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
        BottomButtons()
    }
}

@Composable
private fun BottomButtons(modifier: Modifier = Modifier) {
    Surface(
        elevation = 8.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.adopt_action)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.donate_action)
                )
            }
        }
    }
}

@Composable
private fun UpButton(navController: NavHostController, modifier: Modifier = Modifier) {
    IconButton(
        onClick = {
            navController.popBackStack()
        },
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = stringResource(id = R.string.a11y_back_action),
            tint = MaterialTheme.colors.background,
        )
    }
}
