package com.rutubishi.randomrecipes.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rutubishi.randomrecipes.R
import com.rutubishi.randomrecipes.data.repository.MealRepository
import com.rutubishi.randomrecipes.ui.theme.RandomRecipesTheme
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    vm: LoginVM,
) {
    val context = LocalContext.current
    val loginUiState by vm.loginUiState.collectAsState()

    LaunchedEffect(loginUiState.error) {
        loginUiState.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.diet),
            modifier = Modifier.size(128.dp),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text =
                buildAnnotatedString {
                    append(stringResource(R.string.hello_welcome_to))
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                        ),
                    ) {
                        append(stringResource(R.string.random_recipes))
                    }
                    append(stringResource(R.string.what_s_your_nickname))
                },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.testTag("username_input"),
            value = loginUiState.username ?: "",
            onValueChange = { vm.updateUsername(it) },
            label = { Text(text = stringResource(R.string.nickname)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(0.75f),
            onClick = {
                vm.login(navHostController)
            },
            colors = ButtonDefaults.outlinedButtonColors().copy(contentColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(percent = 20),
        ) {
            Text(stringResource(R.string.let_s_go))
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    RandomRecipesTheme {
        Surface {
            Login(
                vm = LoginVM(mealRepository = MealRepository()),
            )
        }
    }
}
