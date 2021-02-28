package com.example.androiddevchallenge.overview

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.Navigation
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.components.PetInfo
import com.example.androiddevchallenge.model.MockData
import com.example.androiddevchallenge.model.Pet
import dev.chrisbanes.accompanist.coil.CoilImage

private val ITEM_OFFSET = 100.dp

private enum class Tab { DOG, CAT }

@Composable
fun PetsOverview(navController: NavHostController) {
	val tab = mutableStateOf(Tab.DOG)
	Scaffold(
		topBar = { Toolbar() },
		bottomBar = { BottomBar(tab) },
		content = {
			PetsList(navController, tab, it.calculateBottomPadding())
		}
	)
}

@Composable
private fun Toolbar(
	modifier: Modifier = Modifier
) {
	Text(
		text = stringResource(id = R.string.app_name),
		style = MaterialTheme.typography.h3,
		modifier = modifier.padding(16.dp)
	)
}

@Composable
private fun BottomBar(tab: MutableState<Tab>, modifier: Modifier = Modifier) {
	val current = mutableStateOf(tab.value.ordinal)
	WaveComposable(
		itemsCount = 2,
		current = current,
		onClick = {
			tab.value = Tab.values()[it]
		},
		modifier = modifier
	)
}

@Composable
private fun PetsList(
	navController: NavHostController,
	tab: State<Tab>,
	bottomPadding: Dp,
	modifier: Modifier = Modifier
) {
	Surface(modifier.fillMaxSize()) {
		Crossfade(targetState = tab.value) { tab ->
			LazyColumn {
				val source = when (tab) {
					Tab.DOG -> MockData.dogs
					Tab.CAT -> MockData.cats
				}
				items(source) { pet ->
					val displacement = remember(pet) { mutableStateOf(1f) }
					LaunchedEffect(pet) {
						animate(1f, 0f) { value, _ ->
							displacement.value = value
						}
					}
					PetItem(
						pet,
						onClick = {
							navController.navigate(Navigation.detail(pet.id))
						},
						modifier = Modifier
							.absoluteOffset(x = ITEM_OFFSET * displacement.value)
					)
				}
				item {
					Spacer(Modifier.height(bottomPadding))
				}
			}
		}
	}
}

@Composable
private fun PetItem(pet: Pet, onClick: () -> Unit, modifier: Modifier = Modifier) {
	Card(
		modifier = modifier
			.padding(horizontal = 16.dp, vertical = 8.dp)
			.height(140.dp)
			.fillMaxWidth()
	) {
		Row(modifier = Modifier
			.clickable { onClick() }
		) {
			CoilImage(
				data = pet.image,
				contentDescription = stringResource(id = R.string.a11y_pet_image),
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.fillMaxHeight()
					.aspectRatio(1f)
					.clip(RoundedCornerShape(16.dp))
			)
			PetInfo(
				pet, modifier = Modifier
					.padding(
						start = 16.dp,
						top = 8.dp,
						end = 8.dp,
						bottom = 16.dp
					)
			)
		}
	}
}