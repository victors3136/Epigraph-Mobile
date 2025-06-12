package ubb.victors3136.epigraphmobile.ui.animations

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ubb.victors3136.epigraphmobile.ui.components.EpigraphTextBox
import ubb.victors3136.epigraphmobile.ui.theme.ThemeProvider

@Composable
fun rememberRecordingTimer(onMaxReached: () -> Unit) : MutableLongState {
    val time = remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        while (time.longValue < 30) {
            delay(1000L)
            time.longValue += 1
        }
        onMaxReached()
    }

    return time
}

private fun formatTime(absoluteSecondCount: Long): String {
    val minutes = absoluteSecondCount / 60
    val seconds = absoluteSecondCount % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Composable
fun SonogramBars() {
    val barCount = 15
    val randomTargets = remember {
        List(barCount) {
            80f + (30..40).random()
        }
    }
    val randomStarts = remember {
        List(barCount) { 40f }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "bars")

    val animations = randomTargets.mapIndexed { index, target ->
        infiniteTransition.animateFloat(
            initialValue = randomStarts[index],
            targetValue = target,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (500..1_000).random(),
                    easing = CubicBezierEasing(0.0f, 0.32f, 1.0f, 0.74f),
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar$index"
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        animations.forEach { anim ->
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(anim.value.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(ThemeProvider.get().primaryAccent())
            )
        }
    }
}


@Composable
fun RecordingAnimation(duration: Long) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        EpigraphTextBox(
            text = "Recording...",
            style = MaterialTheme.typography.titleMedium,
        )

        EpigraphTextBox(
            text = formatTime(duration),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.Companion.padding(vertical = 8.dp),
        )
        SonogramBars()
    }
}