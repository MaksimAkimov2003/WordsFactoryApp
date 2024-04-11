package com.akimov.wordsfactory.screens.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akimov.wordsfactory.R
import com.akimov.wordsfactory.common.components.button.AppOutlinedButton
import com.akimov.wordsfactory.common.components.button.FilledButton
import com.akimov.wordsfactory.common.components.textField.AppTextField
import com.akimov.wordsfactory.common.extensions.checkCondition
import com.akimov.wordsfactory.common.theme.WordsFactoryTheme
import com.akimov.wordsfactory.common.theme.heading1
import com.akimov.wordsfactory.common.theme.paragraphMedium
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navigateUp: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val viewModel = koinViewModel<LoginViewModel>()

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.actions.collect { currentAction ->
            when (currentAction) {
                is LoginScreenAction.ShowSnackBar -> snackbarHostState.showSnackbar(currentAction.message)
                LoginScreenAction.NavigateToMainScreen -> navigateUp()
            }
        }

    }

    RegisterScreenStateless(
        state = state,
        onButtonClick = remember(viewModel) {
            { email: String,
              password: String
                ->
                viewModel.acceptIntent(LoginScreenIntent.OnLoginButtonClick(email, password))
            }
        },
        snackbarHostState = snackbarHostState,
        navigateToSignUp = navigateToSignUp
    )
}

@Composable
private fun RegisterScreenStateless(
    state: LoginScreenState,
    snackbarHostState: SnackbarHostState,
    onButtonClick: (email: String, password: String) -> Unit,
    navigateToSignUp: () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        }
    ) {
        var emailValue by remember {
            mutableStateOf("")
        }
        var passwordValue by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .background(MaterialTheme.colorScheme.background)
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
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.heading1
            )

            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.please_enter_your_email_and_password),
                style = MaterialTheme.typography.paragraphMedium
            )

            TextFieldsColumn(
                getEmailText = { emailValue },
                getPasswordText = { passwordValue },
                updateEmailText = { email -> emailValue = email },
                updatePasswordText = { password -> passwordValue = password }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    SignInButton(
                        state = state,
                        onButtonClick = onButtonClick,
                        emailValue = emailValue,
                        passwordValue = passwordValue,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    )

                    AppOutlinedButton(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        getText = { stringResource(id = R.string.sign_up) }
                    ) {
                        navigateToSignUp()
                    }
                }
            }
        }
    }
}

@Composable
private fun SignInButton(
    state: LoginScreenState,
    onButtonClick: (email: String, password: String) -> Unit,
    emailValue: String,
    passwordValue: String,
    modifier: Modifier
) {
    var buttonHeight: Int by remember {
        mutableIntStateOf(0)
    }
    val localDensity = LocalDensity.current

    when (state) {
        LoginScreenState.Content -> {
            FilledButton(
                modifier = modifier.then(
                    Modifier.onGloballyPositioned {
                        buttonHeight = it.size.height
                    }
                ),
                getText = { stringResource(id = R.string.sign_in) },
                onClick = {
                    onButtonClick(
                        emailValue,
                        passwordValue
                    )
                }
            )
        }

        LoginScreenState.Loading -> {
            FilledButton(
                modifier = modifier.then(Modifier
                    .checkCondition(
                        condition = { buttonHeight == 0 },
                        ifTrue = { this.wrapContentHeight() },
                        ifFalse = {
                            val height = with(localDensity) {
                                buttonHeight.toDp()
                            }
                            this.height(height)
                        }
                    )),
                content = {
                    val height = with(localDensity) {
                        buttonHeight.toDp()
                    }
                    Box(modifier = Modifier.size(height)) {
                        CircularProgressIndicator(
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
    getEmailText: () -> String,
    getPasswordText: () -> String,
    updateEmailText: (String) -> Unit,
    updatePasswordText: (String) -> Unit
) {
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
            state = LoginScreenState.Content,
            snackbarHostState = remember { SnackbarHostState() },
            onButtonClick = { _, _ ->
            },
            navigateToSignUp = {}
        )
    }
}