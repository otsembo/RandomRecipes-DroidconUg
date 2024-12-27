package com.rutubishi.randomrecipes.presentation.login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rutubishi.randomrecipes.isAppInWideScreen
import com.rutubishi.randomrecipes.presentation.error.AppError
import com.rutubishi.randomrecipes.util.Dimensions
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import randomrecipes.ui.generated.resources.Res
import randomrecipes.ui.generated.resources.diet
import randomrecipes.ui.generated.resources.hello_welcome_to
import randomrecipes.ui.generated.resources.let_s_go
import randomrecipes.ui.generated.resources.nickname
import randomrecipes.ui.generated.resources.random_recipes
import randomrecipes.ui.generated.resources.something_went_wrong
import randomrecipes.ui.generated.resources.what_s_your_nickname

@Serializable
data object LoginRoute

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    vm: LoginVM,
) {
    val isWide = isAppInWideScreen()
    val loginUiState by vm.loginUiState.collectAsState()

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(loginUiState.error) {
        loginUiState.error?.let {
            errorMessage = it
            showError = true
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
        if (showError) {
            AppError(
                onConfirmation = { showError = false },
                dialogTitle = stringResource(Res.string.something_went_wrong),
                dialogText = errorMessage,
            )
        }

        Image(
            painter = painterResource(Res.drawable.diet),
            modifier = Modifier.size(Dimensions.XXLarge(isWide)),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(Dimensions.Medium(isWide)))

        Text(
            text =
                buildAnnotatedString {
                    append(stringResource(Res.string.hello_welcome_to))
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                        ),
                    ) {
                        append(stringResource(Res.string.random_recipes))
                    }
                    append(stringResource(Res.string.what_s_your_nickname))
                },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.testTag("username_input"),
            value = loginUiState.username ?: "",
            onValueChange = { vm.updateUsername(it) },
            label = { Text(text = stringResource(Res.string.nickname)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(if (isWide) .25f else 0.75f),
            onClick = {
                vm.login(navHostController)
            },
            colors = ButtonDefaults.outlinedButtonColors().copy(contentColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(percent = 20),
        ) {
            Text(stringResource(Res.string.let_s_go))
        }
    }
}
