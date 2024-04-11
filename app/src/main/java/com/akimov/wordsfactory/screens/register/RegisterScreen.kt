package com.akimov.wordsfactory.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.FilledButton
import com.akimov.wordsfactory.common.components.textField.AppTextField
import com.akimov.wordsfactory.common.extensions.checkCondition
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

    val state by viewModel.state.collectAsState()
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
        state = state,
        onButtonClick = remember(viewModel) {
            { email: String,
              password: String
                ->
                viewModel.acceptIntent(RegisterScreenIntent.OnRegisterButtonClick(email, password))
            }
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun RegisterScreenStateless(
    state: RegisterScreenState,
    snackbarHostState: SnackbarHostState,
    onButtonClick: (email: String, password: String) -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
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

                Box(modifier = Modifier.fillMaxHeight()) {
                    SignUpButton(
                        state = state,
                        onButtonClick = onButtonClick,
                        emailValue = emailValue,
                        passwordValue = passwordValue
                    )
                }
            }
        }

    }
}

@Composable
private fun BoxScope.SignUpButton(
    state: RegisterScreenState,
    onButtonClick: (email: String, password: String) -> Unit,
    emailValue: String,
    passwordValue: String,
) {
    var buttonHeight: Int by remember {
        mutableIntStateOf(0)
    }
    val localDensity = LocalDensity.current
    when (state) {
        RegisterScreenState.Content -> {
            FilledButton(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        buttonHeight = it.size.height
                    },
                getText = { stringResource(id = R.string.sign_up) },
                onClick = {
                    onButtonClick(
                        emailValue,
                        passwordValue
                    )
                }
            )
        }

        RegisterScreenState.Loading -> {
            FilledButton(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
                    .checkCondition(
                        condition = { buttonHeight == 0 },
                        ifTrue = { this.wrapContentHeight() },
                        ifFalse = {
                            val height = with(localDensity) {
                                buttonHeight.toDp()
                            }
                            this.height(height)
                        }
                    )
                    .fillMaxWidth(),
                content = {
                    val height = with(localDensity) {
                        buttonHeight.toDp()
                    }
                    Box(modifier = Modifier.size(height)) {
                        CircularProgressIndicator(
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                },
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
            state = RegisterScreenState.Loading,
            snackbarHostState = remember { SnackbarHostState() }) { _, _ ->
        }
    }
}