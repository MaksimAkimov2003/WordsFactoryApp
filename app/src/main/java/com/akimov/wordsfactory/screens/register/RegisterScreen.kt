package com.akimov.wordsfactory.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.AppFilledButton
import com.akimov.wordsfactory.common.components.button.AppFilledButtonWithProgressBar
import com.akimov.wordsfactory.common.components.snackbar.SnackbarResult
import com.akimov.wordsfactory.common.components.textField.AppTextField
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.common.theme.paragraphMedium
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navigateUp: () -> Unit
) {
    val viewModel = koinViewModel<RegisterViewModel>()

    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { currentAction ->
            when (currentAction) {
                is RegisterScreenAction.ShowSnackBar -> snackbarHostState.showSnackbar(currentAction.message)
                RegisterScreenAction.NavigateToMainScreen -> navigateUp()
            }
        }

    }

    RegisterScreenStateless(
        isLoading = isLoading,
        onButtonClick = remember(viewModel) {
            { email: String,
              password: String
                ->
                viewModel.onRegisterButtonClick(email = email, password = password)
            }
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun RegisterScreenStateless(
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onButtonClick: (email: String, password: String) -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = {
                    SnackbarResult(data = it, isSuccess = false)
                }
            )
        }
    ) {
        var nameValue by remember {
            mutableStateOf("")
        }
        var emailValue by remember {
            mutableStateOf("")
        }
        var passwordValue by remember {
            mutableStateOf("")
        }
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

        LaunchedEffect(key1 = keyboardHeight) {
            coroutineScope.launch {
                scrollState.scrollBy(keyboardHeight.toFloat())
            }
        }
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CoolStandingImage(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.heading1
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.create_your_account),
                    style = MaterialTheme.typography.paragraphMedium
                )

                TextFieldsColumn(
                    getNameText = { nameValue },
                    getEmailText = { emailValue },
                    getPasswordText = { passwordValue },
                    updateNameText = { nameValue = it },
                    updateEmailText = { emailValue = it },
                    updatePasswordText = { passwordValue = it }
                )

                SignUpButton(
                    modifier = Modifier
                        .padding(bottom = 24.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth(),
                    isLoading = isLoading,
                    onButtonClick = onButtonClick,
                    emailValue = emailValue,
                    passwordValue = passwordValue
                )

            }
        }

    }
}

@Composable
private fun SignUpButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onButtonClick: (email: String, password: String) -> Unit,
    emailValue: String,
    passwordValue: String,
) {
    var buttonHeight: Int by remember {
        mutableIntStateOf(0)
    }
    when (isLoading) {
        false -> {
            AppFilledButton(
                modifier = modifier.then(
                    Modifier.onGloballyPositioned {
                        buttonHeight = it.size.height
                    }
                ),
                getText = { stringResource(id = R.string.sign_up) },
                onClick = {
                    onButtonClick(
                        emailValue,
                        passwordValue
                    )
                }
            )
        }

        true -> {
            AppFilledButtonWithProgressBar(
                modifier = modifier,
                buttonHeight = buttonHeight,
                onClick = {
                    onButtonClick(
                        emailValue,
                        passwordValue
                    )
                }
            )
        }
    }
}

@Composable
private fun TextFieldsColumn(
    getNameText: () -> String,
    getEmailText: () -> String,
    getPasswordText: () -> String,
    updateNameText: (String) -> Unit,
    updateEmailText: (String) -> Unit,
    updatePasswordText: (String) -> Unit
) {
    // Name
    AppTextField(
        getText = getNameText,
        updateText = updateNameText,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        getLabelText = { stringResource(R.string.name) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                defaultKeyboardAction(ImeAction.Next)
            }
        )
    )

    // Email
    AppTextField(
        getText = getEmailText,
        updateText = updateEmailText,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        getLabelText = { stringResource(R.string.e_mail) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                defaultKeyboardAction(ImeAction.Next)
            }
        )
    )

    // Password
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    AppTextField(
        getText = getPasswordText,
        updateText = updatePasswordText,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        getLabelText = { stringResource(R.string.password) },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    isPasswordVisible = !isPasswordVisible
                },
                imageVector = if (isPasswordVisible) {
                    ImageVector.vectorResource(id = R.drawable.ic_visibility_off)
                } else {
                    ImageVector.vectorResource(id = R.drawable.ic_visibility_on)
                },
                contentDescription = null
            )
        },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
    )
}

@Composable
fun CoolStandingImage(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.cool_kids_standing),
        contentDescription = null
    )
}

@Preview
@Composable
fun RegisterScreenPreview() {
    WordsFactoryTheme {
        RegisterScreenStateless(
            isLoading = false,
            snackbarHostState = remember { SnackbarHostState() }) { _, _ ->
        }
    }
}