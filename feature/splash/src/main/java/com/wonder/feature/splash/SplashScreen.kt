package com.wonder.feature.splash

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.WonderTheme
import com.wonder.feature.splash.vm.SplashEffect
import com.wonder.feature.splash.vm.SplashEvent
import com.wonder.feature.splash.vm.SplashViewModel
import com.wonder.resource.R

@Composable
internal fun SplashView(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onMoveOnboarding: () -> Unit,
    onMoveMain: () -> Unit,
) {
    BackHandler(enabled = false) {
    }

    LaunchedEffect(true) {
        splashViewModel.processEvent(SplashEvent.CheckServer)
        splashViewModel.effects.collect { effect ->
            when (effect) {
                is SplashEffect.MoveOnboardingScreen -> onMoveOnboarding()
                is SplashEffect.MoveMainScreen -> onMoveMain()
            }
        }
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Box {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.bg_wonderland),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = null
            )

            Text(
                text = "당신이 찾던 페스티벌, 원더랜드",
                style = Caption2,
                color = Gray700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    WonderTheme {
        SplashScreen()
    }
}
