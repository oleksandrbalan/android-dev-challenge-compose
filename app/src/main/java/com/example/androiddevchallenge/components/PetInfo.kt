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
package com.example.androiddevchallenge.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.model.Pet

@Composable
fun PetInfo(pet: Pet, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row {
            Text(
                text = pet.name,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(pet.sex.iconRes),
                contentDescription = stringResource(id = R.string.a11y_gender_icon),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = pet.age,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(4.dp))
        CompositionLocalProvider(LocalContentAlpha provides 0.5f) {
            Text(
                text = pet.tags.joinToString(" • "),
                style = MaterialTheme.typography.body2,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = stringResource(id = R.string.a11y_location_icon),
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = pet.location,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
