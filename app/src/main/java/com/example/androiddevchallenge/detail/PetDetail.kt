package com.example.androiddevchallenge.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
					pet, modifier = Modifier
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
