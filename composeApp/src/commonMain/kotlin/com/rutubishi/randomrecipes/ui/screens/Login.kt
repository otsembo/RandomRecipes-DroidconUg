package com.rutubishi.randomrecipes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import randomrecipes.composeapp.generated.resources.Res
import randomrecipes.composeapp.generated.resources.diet
import randomrecipes.composeapp.generated.resources.hello_welcome_to
import randomrecipes.composeapp.generated.resources.let_s_go
import randomrecipes.composeapp.generated.resources.nickname
import randomrecipes.composeapp.generated.resources.random_recipes
import randomrecipes.composeapp.generated.resources.what_s_your_nickname

@Serializable
data object LoginRoute

@Composable
fun Login(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    vm: LoginVM,
) {
    val loginUiState by vm.loginUiState.collectAsState()

//    LaunchedEffect(loginUiState.error) {
//        loginUiState.error?.let {
//            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//        }
//    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.diet),
            modifier = Modifier.size(128.dp),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            modifier = Modifier.fillMaxWidth(0.75f),
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

