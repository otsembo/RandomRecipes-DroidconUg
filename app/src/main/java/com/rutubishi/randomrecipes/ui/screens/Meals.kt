package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rutubishi.randomrecipes.R
import com.rutubishi.randomrecipes.ui.theme.RandomRecipesTheme

@Composable
fun Meals(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(8.dp),
        columns = GridCells.Fixed(2),
    ) {
        item(span = { GridItemSpan(2) }) {
            Text(
                "Hello X, Welcome to Random Recipes",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
            )
        }

        items(10) {
            MealItem()
        }
    }
}

@Composable
fun MealItem(modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.diet),
                contentDescription = null,
                modifier =
                    Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                contentScale = ContentScale.Inside,
            )
            Text(
                text = "Meal Name",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).padding(bottom = 8.dp),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MealsPreview(modifier: Modifier = Modifier) {
    RandomRecipesTheme {
        Surface {
            Meals()
        }
    }
}
