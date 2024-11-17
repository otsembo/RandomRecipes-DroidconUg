package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rutubishi.randomrecipes.R
import com.rutubishi.randomrecipes.ui.theme.RandomRecipesTheme

@Composable
fun MealDetail(
    imageUrl: String,
    mealName: String,
    mealDescription: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(16.dp)) {
        Image(
            painter = painterResource(R.drawable.diet),
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = mealName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp),
        )

        Text(
            text = mealDescription,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MealDetailPreview(modifier: Modifier = Modifier) {
    RandomRecipesTheme {
        Surface {
            MealDetail(
                "",
                "Hello Meal",
                "This is a long detailed description of the meals I have created and added here.",
            )
        }
    }
}
